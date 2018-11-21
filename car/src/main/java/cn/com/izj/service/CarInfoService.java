package cn.com.izj.service;


import cn.com.izj.base.CONSTANT;
import cn.com.izj.base.entity.ConvertPageInfo;
import cn.com.izj.base.entity.LocationPoint;
import cn.com.izj.base.entity.PageResult;
import cn.com.izj.base.entity.car.CarCommonInfo;
import cn.com.izj.base.entity.car.CarDynamicInfo;
import cn.com.izj.base.entity.car.ReservationCar;
import cn.com.izj.base.enums.ResponseEnum;
import cn.com.izj.base.response.ApiResult;
import cn.com.izj.base.service.BaseService;
import cn.com.izj.dao.*;
import cn.com.izj.dto.*;
import cn.com.izj.event.dto.GPSInfo;
import cn.com.izj.utils.JsonUtil;
import cn.com.izj.utils.RedisUtil;
import cn.com.izj.utils.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 移动端车辆 API
 *
 * @author: 朱鸿平
 * @date: 2018/6/6 20:13
 */
@Service
public class CarInfoService extends BaseService {

    @Autowired
    private CarCommonDao carCommonDao;

    @Autowired
    private CarDynamicDao carDynamicDao;

    @Autowired
    private ReservationCarDao reservationCarDao;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private DeviceInfoDao deviceInfoDao;

    @Autowired
    private OriginContrailDao originContrailDao;
    @Autowired
    private DeviceService deviceService;

    /**
     * 添加车辆
     *
     * @param info 车辆信息
     */
    @Transactional
    public ApiResult insertCar(AddCarInfo info) {
        //添加基本信息
        CarCommonInfo carInfo = carCommonDao.queryByPlateNumber(info.getPlateNumber());
        if (carInfo != null) {
            log.error("车牌号已存在,carInfo: " + JsonUtil.beanToJson(info));
            return new ApiResult(ResponseEnum.CAR_IS_EXIST.getValue(), ResponseEnum.CAR_IS_EXIST.getDesc());
        }
        CarCommonInfo commonInfo = new CarCommonInfo();
        BeanUtils.copyProperties(info, commonInfo);
        Date date = new Date();
        commonInfo.setCreateTime(date);
        commonInfo.setCarState(CarCommonInfo.STATE_FREE);
        carCommonDao.insert(commonInfo);

        carInfo = carCommonDao.queryByPlateNumber(info.getPlateNumber());
        CarDynamicInfo dynamicInfo = new CarDynamicInfo();
        BeanUtils.copyProperties(info, dynamicInfo);
        dynamicInfo.setId(carInfo.getId());
        dynamicInfo.setCreateTime(date);
        int result = carDynamicDao.insert(dynamicInfo);
        return ApiResult.validateSingleData(result);
    }

    /**
     * 根据id获取车辆信息
     *
     * @param id 车辆id
     */
    public ApiResult getCarInfoById(Long id) {
        ShowCarInfo carInfo = carCommonDao.getCarInfoById(id);
        if (null == carInfo) {
            return ApiResult.errorWithData(new ShowCarInfo());
        }
        Device device = getDeviceInfo(carInfo.getId());
        if (device != null) {
            carInfo.setMaxDistance(device.getRemainderRange());
        }
        return ApiResult.successWithData(carInfo);
    }

    /**
     * 删除/下线车辆
     *
     * @param id 车辆id
     */
    @Transactional
    public ApiResult UpdateCarStateById(Long id, Integer state) {
        //查询车辆状态
        CarCommonInfo carInfo = carCommonDao.findById(id);
        if (carIsUsed(carInfo.getCarState())) {
            log.error("the car" + JsonUtil.beanToJson(carInfo) + " is used");
            return new ApiResult(ResponseEnum.CAR_IS_USED.getValue(), ResponseEnum.CAR_IS_USED.getDesc());
        }
        carInfo.setCarState(state);
        carInfo.setUpdateTime(new Date());
        if (StringUtil.getInt(state) == CarCommonInfo.STATE_DOWNLINE) {
            carInfo.setDownlineTime(new Date());
        }
        return ApiResult.validateSingleData(carCommonDao.updateById(carInfo));
    }

    /**
     * 车辆上线
     *
     * @param id 车辆id
     */
    @Transactional
    public ApiResult updateCarOnline(Long id) {
        //查询车车辆是否在停车场内
        LocationPoint locationPoint = originContrailDao.getLocationByCarId(id);
        locationPoint.setDistance(CONSTANT.PARK_LIMIT_DISTANCE);
        List<LocationPoint> parkList = redisUtil.getParkByPoint(locationPoint);
        if (CollectionUtils.isEmpty(parkList)) {
            log.error("车辆不在停车场内，location: " + JsonUtil.beanToJson(locationPoint)
                    + " carId: " + id);
            return new ApiResult(ResponseEnum.CAR_NOT_IN_PARK.getValue(), ResponseEnum.CAR_NOT_IN_PARK.getDesc());
        }
        // 查询车辆续航里程 >= 10km
        Device device = getDeviceInfo(id);
        if (StringUtil.getInt(device.getRemainderRange()) < CONSTANT.ONLINE_MINIMUM_DISTANCE) {
            log.error("车辆动力不足，dynamicInfo: " + JsonUtil.beanToJson(id));
            return new ApiResult(ResponseEnum.CAR_FUEL_LACK.getValue(), ResponseEnum.CAR_FUEL_LACK.getDesc());
        }

        //更新车辆上线状态
        CarCommonInfo carCommonInfo = new CarCommonInfo();
        carCommonInfo.setId(id);
        carCommonInfo.setCarState(CarCommonInfo.STATE_FREE);
        carCommonInfo.setOnlineTime(new Date());
        carCommonInfo.setUpdateTime(new Date());
        int result = carCommonDao.updateById(carCommonInfo);

        //更新车辆所在停车场
        CarDynamicInfo dynamicInfo = new CarDynamicInfo();
        dynamicInfo.setId(id);
        // 获取最近的停车场
        Long parkId = StringUtil.getLong(parkList.get(0).getParkId());
        LocationPoint point = carCommonDao.getParkInfo(parkId);
        dynamicInfo.setParkId(parkId);
        dynamicInfo.setParkName(point.getParkName());
        dynamicInfo.setParkAddress(point.getParkAddress());
        carDynamicDao.updateById(dynamicInfo);
        //更新停车场车位数
        Integer parkCountBalance = carCommonDao.findParkCountBalance(parkId);
        //控制最小值不能小于0
        if (parkCountBalance <= 0) {
            return new ApiResult(ResponseEnum.PARK_COUNT_NOT_ENOUGH.getValue(), ResponseEnum.PARK_COUNT_NOT_ENOUGH.getDesc());
        }
        carCommonDao.updateParkCount(parkId);
        return ApiResult.validateSingleData(result);
    }

    /**
     * 判断车辆是否在使用状态
     *
     * @param state 状态值
     */
    private boolean carIsUsed(Integer state) {
        return CarCommonInfo.STATE_IN_USE == StringUtil.getInt(state) ||
                CarCommonInfo.STATE_RESERVATION == StringUtil.getInt(state);
    }

    /**
     * 修改车辆等级
     *
     * @param gradeInfo 修改字段(carId + carGrade)
     */
    @Transactional
    public ApiResult updateGradeById(UpdateGrade gradeInfo) {
        CarCommonInfo carInfo = carCommonDao.findById(gradeInfo.getCarId());
        if (carIsUsed(carInfo.getCarState())) {
            log.error("the car" + JsonUtil.beanToJson(carInfo) + " is used");
            return new ApiResult(ResponseEnum.CAR_IS_USED.getValue(), ResponseEnum.CAR_IS_USED.getDesc());
        }
        carInfo.setCarState(gradeInfo.getCarGrade());
        carInfo.setUpdateTime(new Date());
        int result = carCommonDao.updateById(carInfo);
        return ApiResult.validateSingleData(result);
    }

    /**
     * 获取可预约车辆list
     *
     * @param parkId 停车场id
     */
    public ApiResult getReservationList(Long parkId) {
        List<ShowCarInfo> commonInfoList = carCommonDao.getReservationList(parkId);
        if (CollectionUtils.isEmpty(commonInfoList)) {
            commonInfoList = new ArrayList<>();
        }
        for (ShowCarInfo carInfo : commonInfoList) {
            Device device = getDeviceInfo(carInfo.getId());
            if (device != null) {
                carInfo.setMaxDistance(device.getRemainderRange());
            }
        }
        return ApiResult.successWithData(commonInfoList);
    }

    /**
     * 我的车辆
     */
    public ApiResult getMyCarList(Long userId) {
        List<MyCarInfo> carList = carCommonDao.getMyCarList(userId);
        if (CollectionUtils.isEmpty(carList)) {
            carList = new ArrayList<>();
        }
        return ApiResult.successWithData(carList);
    }

    /**
     * 取消预约
     *
     * @param userId 用户id
     */
    @Transactional
    public ApiResult cancelReservation(Long userId, Long reservationId) {
        ReservationCar reservationCar = new ReservationCar();
        String key = CONSTANT.RESERVATION_CAR + userId;
        String reservationInfo = (String) redisTemplate.opsForValue().get(key);
        ReservationCar car = JsonUtil.jsonToBean(reservationInfo, ReservationCar.class);
        int result;
        if (car != null && car.getId().equals(reservationId)) {
            reservationCar.setId(reservationId);
            reservationCar.setReservationState(ReservationCar.RESERVATION_CANCEL);
            reservationCar.setCancelReservationTime(new Date());
            reservationCar.setUpdateTime(new Date());
            CarCommonInfo carCommonInfo = new CarCommonInfo();
            carCommonInfo.setCarState(CarCommonInfo.STATE_FREE);
            //修改被预约车辆状态
            carCommonDao.updateReservationCarState(reservationId, CarCommonInfo.STATE_FREE);
            //修改预约信息状态
            result = reservationCarDao.updateById(reservationCar);
            redisTemplate.delete(key);
            return ApiResult.validateSingleData(result);
        } else {
            return new ApiResult(ResponseEnum.RESEARVATION_OVER_TIME.getValue(),
                    ResponseEnum.RESEARVATION_OVER_TIME.getDesc());
        }
    }

    /**
     * 审核车辆
     *
     * @param carInfo 车辆信息
     */
    @Transactional
    public Integer auditCar(AuditCar carInfo) {
        CarCommonInfo commonInfo = new CarCommonInfo();
        CarDynamicInfo dynamicInfo = new CarDynamicInfo();
        if (carInfo.getState().equals(AuditCar.STATE_AUDIT_PASS)) {
            commonInfo.setCarState(CarCommonInfo.STATE_AUDIT_PASS);
            commonInfo.setRemarks("");
        } else if (carInfo.getState().equals(AuditCar.STATE_AUDIT_FAILURE)) {
            commonInfo.setCarState(CarCommonInfo.STATE_AUDIT_FAILURE);
            if (StringUtil.isNotEmpty(carInfo.getRemarks())) {//审核不通过原因
                commonInfo.setRemarks(carInfo.getRemarks());
            }
        }
        //修改车辆状态
        commonInfo.setId(carInfo.getCarId());
        commonInfo.setUpdateTime(new Date());
        commonInfo.setAuditorId(carInfo.getAuditorId());
        commonInfo.setCarGrade(carInfo.getCarGrade());
        dynamicInfo.setId(carInfo.getCarId());
        dynamicInfo.setDeviceId(carInfo.getDeviceId());
        dynamicInfo.setUpdateTime(new Date());
        carCommonDao.updateById(commonInfo);
        return carDynamicDao.updateById(dynamicInfo);
    }

    /**
     * 车辆列表
     */
    public ApiResult getCarList(QueryCarCondition condition) {
        List<CarInfo> list = new ArrayList<>();
        PageHelper.startPage(condition.getPageNumber(), condition.getPageSize());
        List<ShowCarInfo> carList = carCommonDao.getCarList(condition);
        if (CollectionUtils.isEmpty(carList)) {
            log.warn("无车辆信息");
            carList = new ArrayList<>();
        }
        carList.forEach(car -> {
            CarInfo carInfo = new CarInfo();
            carInfo.setCarInfo(car);
            Device device = null;
            try {
                device = getDeviceInfo(car.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (device != null) {
                log.info("设备信息，device:" + JsonUtil.beanToJson(device));
                carInfo.setDevice(device);
            }
            log.info("车辆信息，car:" + JsonUtil.beanToJson(car));
            list.add(carInfo);
        });
        PageInfo<ShowCarInfo> pageInfo = new PageInfo<>(carList);
        ConvertPageInfo convert = new ConvertPageInfo();
        PageResult carInfo = convert.convertPageInfo(pageInfo);
        carInfo.setList(list);
        return ApiResult.successWithData(carInfo);
    }

    /**
     * 根据监听事件修改设备状态
     *
     * @param deviceId
     * @param state
     */
    public void updateDeviceState(String deviceId, Integer state) {
        deviceInfoDao.updateDeviceState(deviceId, state);
    }

    /**
     * 获取设备信息(经纬度，油量)
     *
     * @param carId
     */
    public Device getDeviceInfo(Long carId) {
        Device device = new Device();
        CarDynamicInfo carInfo = carDynamicDao.findById(carId);
        Object object;
        if (carInfo != null) {
            object = redisTemplate.opsForValue().get(CONSTANT.GPS_INFO + carInfo.getDeviceId());
            if (object == null) {
                object = deviceService.getDeviceInfo(carInfo.getDeviceId());
            }
            if (object instanceof GPSInfo) {
                GPSInfo gPSInfo = (GPSInfo) object;
                device.setLatitude(StringUtil.getDouble(gPSInfo.getLatitude()));
                device.setLongitude(StringUtil.getDouble(gPSInfo.getLongitude()));
                device.setBatteryRemaining(gPSInfo.getBatteryRemaining());
                device.setGpsSignalIntensity(gPSInfo.getGpsSignalIntensity());
                device.setSpeed(gPSInfo.getSpeed());
                device.setTotalMileage(gPSInfo.getTotalMileage());
                device.setRemainderRange(gPSInfo.getRemainderRange());
                log.info("设备信息，device:" + JsonUtil.beanToJson(device));
            } else {
                log.error("从设备上获取信息失败，car:" + JsonUtil.beanToJson(carInfo));
                LocationPoint point = originContrailDao.getLocationByCarId(carId);
                device.setLatitude(point.getLatitude());
                device.setLongitude(point.getLongitude());
            }
        } else {
            log.error("获取车辆信息失败，car:" + JsonUtil.beanToJson(carId));
        }
        return device;
    }
}
