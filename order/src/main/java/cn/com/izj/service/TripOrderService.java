package cn.com.izj.service;

import cn.com.izj.amap.StaticMapParams;
import cn.com.izj.base.CONSTANT;
import cn.com.izj.base.entity.GlobalSetting;
import cn.com.izj.base.entity.LocationPoint;
import cn.com.izj.base.entity.UserAuth;
import cn.com.izj.base.entity.UserAuthInfo;
import cn.com.izj.base.entity.car.*;
import cn.com.izj.base.entity.order.TripContrail;
import cn.com.izj.base.entity.order.TripOrder;
import cn.com.izj.base.entity.park.Park;
import cn.com.izj.base.entity.pay.Deposit;
import cn.com.izj.base.enums.ResponseEnum;
import cn.com.izj.base.enums.SettingTypeEnum;
import cn.com.izj.base.exception.BussinessException;
import cn.com.izj.base.response.ApiResult;
import cn.com.izj.base.service.BaseService;
import cn.com.izj.condition.TripOrderFindCondition;
import cn.com.izj.dao.*;
import cn.com.izj.dto.*;
import cn.com.izj.entity.DevicePassword;
import cn.com.izj.entity.User;
import cn.com.izj.event.UpdateCarEvent;
import cn.com.izj.utils.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author: lifang
 * @description:行程订单-service
 * @date: Created in 2018/6/10 16:32
 * @version:
 */
@Service
public class TripOrderService extends BaseService {

    @Autowired
    private ReservationCarDao reservationCarDao;

    @Autowired
    private TripOrderDao tripOrderDao;

    @Autowired
    private CarGradeRuleDao carGradeRuleDao;

    @Autowired
    private CarCommonDao carCommonDao;

    @Autowired
    private CarInfoService carInfoService;

    @Autowired
    private OriginContrailDao originContrailDao;

    @Autowired
    private TripContrailDao tripContrailDao;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private HttpClientService httpClient;

    @Autowired
    private StaticMapParams staticMapParams;

    @Autowired
    private AMapUtil aMapUtil;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private ParkDao parkDao;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private CarDynamicDao carDynamicDao;

    @Autowired
    private UserPreferentialDao userPreferentialDao;

    @Autowired
    private DevicePasswordDao devicePasswordDao;

    @Autowired
    private SmsService smsService;

    @Autowired
    private AliSmsService aliSmsService;

    /**
     * 生成行程订单记录
     *
     * @param tripOrder
     * @param user
     */
    @Transactional(rollbackFor = Exception.class)
    public ApiResult createTripOrder(TripOrder tripOrder, User user) {
        //从预约单中查询预约信息(起点停车场id、终点停车场id)
        String key = CONSTANT.RESERVATION_CAR + user.getId();
        String reservationInfo = (String) redisTemplate.opsForValue().get(key);
        ReservationCar reservationCar = JsonUtil.jsonToBean(reservationInfo, ReservationCar.class);
        if (null != reservationCar) {
            //修改被预约车辆状态为使用中
            carCommonDao.updateReservationCarState(reservationCar.getId(), CarCommonInfo.STATE_IN_USE);
            tripOrder.setStartParkId(reservationCar.getStartParkId());
            tripOrder.setDestinationParkId(tripOrder.getDestinationParkId());
            //生成订单编号,当前时间戳
            tripOrder.setNumber(OrderUtil.makeOrderNum());
            tripOrder.setUserId(user.getId());
            tripOrder.setCarId(reservationCar.getCarId());
            tripOrder.setStatus(TripOrder.STATUS_RUNNING);
            tripOrder.setStartTime(new Date());
            this.tripOrderDao.insert(tripOrder);
            //修改对应预约信息的状态为已完成
            reservationCar.setReservationState(ReservationCar.RESERVATION_SUCCESS);
            reservationCar.setUpdateTime(new Date());
            this.reservationCarDao.updateById(reservationCar);
            //修改起点停车场可用停车位数量
            parkDao.updateById(updateParkCount(reservationCar.getStartParkId(), 1));
            //删除Redis中对应车辆的预约信息
            redisTemplate.delete(key);
            //将订单号缓存至Redis中
            //String orderKey = CONSTANT.START_ORDER + user.getId();
            //redisTemplate.opsForValue().set(orderKey, tripOrder.getNumber());
            //发送键盘开锁密码
            CarDynamicInfo dynamicInfo = this.carDynamicDao.findById(reservationCar.getCarId());
            DevicePassword devicePassword = this.devicePasswordDao.getDriverPassword(dynamicInfo.getDeviceId());
            String regExp = "^[1][3-9][0-9]{9}$";
            Pattern p = Pattern.compile(regExp);
            Matcher m = p.matcher(user.getUsername());
            if (m.matches()) {
                String keyboardPassword;
                if (null != devicePassword) {
                    keyboardPassword = devicePassword.getKeyboardPassword();
                } else {
                    //查询为空，重置密码
                    keyboardPassword = RandomStringUtil.getRandomString(8);
                    for (int i = 0; i < 3; i++) {
                        TcpResponse response = this.deviceService.setKeyboardPassword(dynamicInfo.getDeviceId(), keyboardPassword);
                        if (response.isStatus()) {
                            log.info("重置密码键盘开锁码，设备id：" + dynamicInfo.getDeviceId() + "开锁码：" + keyboardPassword);
                            break;
                        }
                    }
                }
                if (StringUtils.isNotEmpty(keyboardPassword)) {
                    log.info("向用户 " + user.getUsername() + " 发送密码键盘开锁码 " + keyboardPassword);
                    //短信通知用户开锁密码
                    aliSmsService.sendKeyboardPassword(user.getUsername(), keyboardPassword);
                }
            }
            //返回生成的订单信息
            return new ApiResult(ResponseEnum.SUCCESS.getValue(), ResponseEnum.SUCCESS.getDesc(), tripOrder);
        } else {
            //未找到预约信息,拒绝生成订单
            log.error("reservation info belong to " + tripOrder.getUserId() + "is not found");
            return new ApiResult(ResponseEnum.RESEARVATION_NOT_FOUND.getValue(), ResponseEnum.RESEARVATION_NOT_FOUND.getDesc());
        }
    }

    /**
     * 查询订单集合
     *
     * @param condition 查询条件
     * @return
     * @throws Exception
     */
    public List<TripOrderResultDto> findTripOrdersByCondition(TripOrderFindCondition condition) {
        return this.tripOrderDao.findTripOrdersByCondition(condition);
    }

    /**
     * 订单集合分页查询
     *
     * @param condition
     * @return
     */
    public PageInfo findOrdersPageByCondition(TripOrderFindCondition condition) {
        PageHelper.startPage(condition.getPageNumber(), condition.getPageSize());
        List<TripOrderResultDto> list = this.tripOrderDao.findTripOrdersByCondition(condition);
        PageInfo<TripOrderResultDto> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 根据订单号查询订单详情
     *
     * @param orderNum 订单号
     */
    public TripOrderResultDto getTripOrderByNum(String orderNum) {
        TripOrderResultDto order = tripOrderDao.getTripOrderByNum(orderNum);
        return order;
    }

    /**
     * 结束行程
     *
     * @param orderNum
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ApiResult completeTrip(String orderNum, User user) throws Exception {
        ApiResult apiResult = null;
        try {
            TripOrderResultDto tripOrderResultDto = this.tripOrderDao.getTripOrderByNum(orderNum);
            TripOrder tripOrder = new TripOrder();
            BeanUtils.copyProperties(tripOrderResultDto, tripOrder);
            //检查车辆是否在订单对应的终点停车场位置范围（超过范围认为位置不正确，拒绝结束，暂定200m）
            Device device = this.carInfoService.getDeviceInfo(tripOrder.getCarId());
            LocationPoint locationPoint = new LocationPoint();
            locationPoint.setLongitude(device.getLongitude());
            locationPoint.setLatitude(device.getLatitude());
            locationPoint.setDistance(CONSTANT.PARK_LIMIT_DISTANCE);
            boolean isInpark = redisUtil.isInPark(locationPoint, tripOrder.getDestinationParkId());
            if (!isInpark) {
                return new ApiResult(ResponseEnum.CAR_NOT_IN_PARK.getValue(), ResponseEnum.CAR_NOT_IN_PARK.getDesc());
            }

            //还车
            Long carId = tripOrder.getCarId();
            CarDynamicInfo carDynamicInfo = carDynamicDao.findById(carId);
            TcpResponse tcpResponse = this.deviceService.returnCar(carDynamicInfo.getDeviceId());
            if (null != tcpResponse) {
                boolean returnRes = tcpResponse.isStatus();
                if (!returnRes) {//还车失败
                    return new ApiResult(ResponseEnum.RETURN_CAR_FAILED.getValue(), tcpResponse.getMsg());
                }
            } else {//还车失败
                return new ApiResult(ResponseEnum.RETURN_CAR_FAILED.getValue(), tcpResponse.getMsg());
            }

            //修改车辆状态
            CarCommonInfo carCommonInfo = new CarCommonInfo();
            carCommonInfo.setId(tripOrder.getCarId());
            carCommonInfo.setCarState(CarCommonInfo.STATE_FREE);
            carCommonInfo.setUpdateTime(new Date());
            carCommonDao.updateById(carCommonInfo);
            //修改车辆终点停车场
            carDynamicInfo.setParkId(tripOrder.getDestinationParkId());
            this.carDynamicDao.updateById(carDynamicInfo);

            //更新终点停车场中可用停车位数量 -1
            parkDao.updateById(updateParkCount(tripOrder.getDestinationParkId(), -1));

            tripOrder.setEndTime(new Date());
            List<OriginContrail> originContrails = this.originContrailDao.findByTimeRange(tripOrder.getCarId(), tripOrder.getStartTime(), tripOrder.getEndTime());
            int totalMilgle = 0;
            if (null != originContrails && originContrails.size() > 0) {
                // 异步处理保存订单轨迹数据
                CompletableFuture.runAsync(() -> saveTripContrial(tripOrder.getNumber(), originContrails));
                //计算行程总里程
                int milgleStart = originContrails.get(0).getTotalMileage();
                int milgleEnd = originContrails.get(originContrails.size() - 1).getTotalMileage();
                totalMilgle = milgleEnd - milgleStart;
            }
            tripOrder.setMileage(totalMilgle);
            tripOrder.setStatus(TripOrder.STATUS_WAITTING_PAY);
            //计算行程持续时间(分钟)
            Double durationTime = Math.ceil(Double.parseDouble(String.valueOf((tripOrder.getEndTime().getTime() - tripOrder.getStartTime().getTime()))) / (1000 * 60));
            tripOrder.setDurationTime(durationTime.longValue());
//            //计算行程总里程
//            tripOrder.setMileage(this.calculateMileage(tripOrder, originContrails));
            //计算行程应付总金额
            Integer tripAmount = calculateOrderAmount(tripOrder);

            //不计免赔
            Integer deductible = 0;
            if (tripOrder.getDeductibleStatus() == 1) {
                GlobalSetting globalSetting = this.getSettingInfo(SettingTypeEnum.DEDUCTIBLES.getValue());
                if (null != globalSetting) {
                    deductible = StringUtil.getInt(globalSetting.getFieldValue());
                }
            }
            tripOrder.setDeductible(deductible);
            //停车费 暂使用全局变量
            GlobalSetting setting = tripOrderDao.getSettingInfo(SettingTypeEnum.PARK_FEE.getValue());
            if (setting != null) {
                tripOrder.setParkFee(StringUtil.getInt(setting.getFieldValue()));
            }
            tripOrder.setShouldPayAmount(tripAmount + deductible + tripOrder.getParkFee());
            tripOrder.setRealPayAmount(tripAmount + deductible + tripOrder.getParkFee());
            if (user.isHalfUser()) {
                tripOrder.setRealPayAmount(Integer.parseInt(new DecimalFormat("0").
                        format((tripAmount * CONSTANT.HALF_OFF))) + deductible + tripOrder.getParkFee());
            }
            this.tripOrderDao.updateById(tripOrder);

            //删除订单缓存
            //String orderKey = CONSTANT.START_ORDER + tripOrder.getUserId();
            //redisTemplate.delete(orderKey);

            //重置键盘开锁密码
            String regExp = "^[1][3-9][0-9]{9}$";
            Pattern p = Pattern.compile(regExp);
            Matcher m = p.matcher(user.getUsername());
            if (m.matches()) {
                CarDynamicInfo dynamicInfo = this.carDynamicDao.findById(tripOrder.getCarId());
                String pswd = RandomStringUtil.getRandomString(8);
                for (int i = 0; i < 3; i++) {
                    TcpResponse response = this.deviceService.setKeyboardPassword(dynamicInfo.getDeviceId(), pswd);
                    if (response.isStatus()) {
                        log.info("重置密码键盘开锁码，设备id：" + dynamicInfo.getDeviceId() + "开锁码：" + pswd);
                        break;
                    }
                }
            }
        } catch (BussinessException e) {
            apiResult = new ApiResult(ResponseEnum.ERROR.getValue(), ResponseEnum.ERROR.getDesc());
            log.error("结束行程出现异常：" + e.getMessage());
        }
        return apiResult != null ? apiResult : new ApiResult(ResponseEnum.SUCCESS.getValue(), ResponseEnum.SUCCESS.getDesc());
    }

    /**
     * 保存订单轨迹数据
     *
     * @param originContrails
     */
    @Transactional
    public String saveTripContrial(String orderNumber, List<OriginContrail> originContrails) {
        if (!CollectionUtils.isEmpty(originContrails)) {
            List<OriginContrail> list = originContrails.parallelStream().
                    filter(e -> StringUtil.getInt(e.getSpeed()) != 0).collect(Collectors.toList());
            list = ContrailUtil.getContrailList(list);
            TripContrail tripContrail = new TripContrail();
            tripContrail.setOrderId(orderNumber);
            List<JSONObject> jsonObjects = list.stream().map(e -> {
                JSONObject jo = new JSONObject();
                jo.put("lon", e.getLongitude());
                jo.put("lat", e.getLatitude());
                return jo;
            }).collect(Collectors.toList());
            tripContrail.setContrailPoint(JSON.toJSONString(jsonObjects));
            //生成轨迹图片
            String url = aMapUtil.tripContrailImageGen(orderNumber, staticMapParams, jsonObjects);
            //存放路径
            tripContrail.setContrailUrl(url);
            tripContrail.setCreateTime(new Date());
            this.tripContrailDao.insert(tripContrail);
            log.info("轨迹图地址：" + url);
            //向订单记录中添加轨迹图
            TripOrder tripOrder = new TripOrder();
            tripOrder.setUrl(StringUtil.getStr(url));
            tripOrder.setNumber(orderNumber);
            tripOrderDao.updateByOrderNum(tripOrder);
            return url;
        }
        return null;
    }

    /**
     * 计算行程总里程
     *
     * @param tripOrder       行程订单
     * @param originContrails 行程期间，对应车辆的全部位置信息
     * @return
     * @throws Exception
     */
    private Integer calculateMileage(TripOrder tripOrder, List<OriginContrail> originContrails) throws Exception {
        BigDecimal distanceTotal = new BigDecimal("0");
        Assert.notEmpty(originContrails, "[Assertion failed] - not found any originContrail data of tripOrder which orderNum is " + tripOrder.getNumber());
        if (originContrails.size() < 2) {
            throw new BussinessException(ResponseEnum.POINT_AT_LEAST_TWO.getValue(), ResponseEnum.POINT_AT_LEAST_TWO.getDesc());
        }
        //每次计算最多包含500个点位，这里将其类比分页
        int currentPageNum = 1;
        int elementSize = 500;
        int totalCalcuCount = originContrails.size() / elementSize;
        if (originContrails.size() % elementSize != 0) {
            totalCalcuCount++;
        }
        for (long i = 1; i <= totalCalcuCount; i++) {
            List<OriginContrail> tempList = originContrails.stream().skip((currentPageNum - 1) * elementSize).limit(elementSize).collect(Collectors.toList());
            //组装参数
            JSONArray points = new JSONArray();
            for (int j = 0; j <= originContrails.size() - 1; j++) {
                JSONObject point = new JSONObject();
                OriginContrail originContrail = originContrails.get(j);
                point.put("x", originContrail.getLongitude());
                point.put("y", originContrail.getLatitude());
                point.put("ag", 0);
                point.put("sp", 0);
                if (i == 0) {
                    point.put("tm", originContrail.getCreateTime().getTime());
                } else {
                    point.put("tm", originContrail.getCreateTime().getTime() - originContrails.get(0).getCreateTime().getTime());
                }
                points.add(point);
            }
            JSONObject result = JSON.parseObject(this.httpClient.doPostJson(CONSTANT.AMAP_MILEAGE_CALCULATE_URL + "?key=" + CONSTANT.AMAP_KEY, JSON.toJSONString(points)));
            if (result.getInteger("errcode") != 0) {
                throw new BussinessException(result.getInteger("errcode"), result.getString("errdetail"));
            }
            distanceTotal = distanceTotal.add(result.getJSONObject("data").getBigDecimal("distance"));
            currentPageNum++;
        }
        return distanceTotal.intValue();
    }


    /**
     * 计算行程订单总金额
     *
     * @param tripOrder 行程订单
     * @return
     * @throws Exception
     */
    private Integer calculateOrderAmount(TripOrder tripOrder) throws Exception {
        //获取车辆等级
        CarCommonInfo carCommonInfo = this.carCommonDao.findById(tripOrder.getCarId());
        Integer grade = carCommonInfo.getCarGrade();
        //获取等级计价规则(里程计价，计时计价)
        CarGradeRule carGradeRule = this.carGradeRuleDao.getCarGradeRuleByGrade(grade);
        if (carGradeRule == null) {
            log.error("not found carGradeRule which carId is " + tripOrder.getCarId());
            throw new BussinessException(ResponseEnum.CARGRADERULE_NOT_FOUND.getValue(), ResponseEnum.CARGRADERULE_NOT_FOUND.getDesc());
        }
        //行程持续时间
        Integer durationTime = tripOrder.getDurationTime().intValue();
        Integer mileageAmount = 0;
        Integer timeAmount = 0;
        CarDynamicInfo dynamicInfo = this.carDynamicDao.findById(tripOrder.getCarId());
        if (dynamicInfo.getPower() == CarDynamicInfo.POWER_CHARGE) {//电动6小时后不计时长费
            //判断时长是否超过6小时
            if (durationTime >= 6 * 60) {
                durationTime = 6 * 60;
            }
            //计时计价总金额
            timeAmount = carGradeRule.getTimeFee() * durationTime;
        } else {//燃油8小时后不计时长费
            //判断时长是否超过8小时
            if (durationTime >= 8 * 60) {
                durationTime = 8 * 60;
            }
            //里程计价总金额
            mileageAmount = carGradeRule.getMileagePrice() * tripOrder.getMileage();
            //计时计价总金额
            timeAmount = carGradeRule.getTimeFee() * durationTime;
        }
        return mileageAmount + timeAmount;
    }

    /**
     * 查询用户未完成订单
     *
     * @param userId 用户id
     */
    public ApiResult queryUnfinishedOrder(Long userId) {
        String orderNum = tripOrderDao.queryUnfinishedOrder(userId);
        TripOrderResultDto order = null;
        if (StringUtil.isNotEmpty(orderNum)) {
            order = getTripOrderByNum(orderNum);
        }
        JSONObject jo = new JSONObject();
        if (order != null) {
            //fromPark
            Park parkStart = this.parkDao.findById(order.getStartParkId());
            JSONObject fromPark = new JSONObject();
            fromPark.put("parkID", (parkStart == null ? "" : parkStart.getId()) == null ? "" : parkStart.getId());
            fromPark.put("name", (parkStart == null ? "" : parkStart.getName()) == null ? "" : parkStart.getName());
            fromPark.put("longtitude", (parkStart == null ? "" : parkStart.getLongitude()) == null ? "" : parkStart.getLongitude());
            fromPark.put("latitude", (parkStart == null ? "" : parkStart.getLatitude()) == null ? "" : parkStart.getLatitude());
            //toPark
            Park parkEnd = this.parkDao.findById(order.getDestinationParkId());
            JSONObject toPark = new JSONObject();
            toPark.put("parkID", (parkEnd == null ? "" : parkEnd.getId()) == null ? "" : parkEnd.getId());
            toPark.put("name", (parkEnd == null ? "" : parkEnd.getName()) == null ? "" : parkEnd.getName());
            toPark.put("longtitude", (parkEnd == null ? "" : parkEnd.getLongitude()) == null ? "" : parkEnd.getLongitude());
            toPark.put("latitude", (parkEnd == null ? "" : parkEnd.getLatitude()) == null ? "" : parkEnd.getLatitude());
            //carModel
            CarCommonInfo carCommonInfo = this.carCommonDao.findById(order.getCarId());
            Device device = this.carInfoService.getDeviceInfo(order.getCarId());
            JSONObject car = new JSONObject();
            car.put("carID", (carCommonInfo == null ? "" : carCommonInfo.getId()) == null ? "" : carCommonInfo.getId());
            car.put("carType", (carCommonInfo == null ? "" : carCommonInfo.getCarType()) == null ? "" : carCommonInfo.getCarType());
            car.put("brand", (carCommonInfo == null ? "" : carCommonInfo.getBrand()) == null ? "" : carCommonInfo.getBrand());
            car.put("carColor", (carCommonInfo == null ? "" : carCommonInfo.getCarColor()) == null ? "" : carCommonInfo.getCarColor());
            car.put("plateNumber", (carCommonInfo == null ? "" : carCommonInfo.getPlateNumber()) == null ? "" : carCommonInfo.getPlateNumber());
            car.put("maxDistance", (device == null ? "" : device.getRemainderRange()) == null ? "" : device.getRemainderRange());
            //orderModel
            JSONObject orderjo = new JSONObject();
            orderjo.put("orderId", order.getId());
            orderjo.put("number", order.getNumber());
            orderjo.put("reservationId", CONSTANT.RESERVATION_CAR + order.getUserId());
            jo = (JSONObject) JSONObject.toJSON(order);
            jo.put("fromPark", fromPark);
            jo.put("toPark", toPark);
            jo.put("car", car);
            jo.put("order", orderjo);
        }
        return ApiResult.successWithData(jo);
    }

    /**
     * 更新终点停车场
     *
     * @param park 更新信息
     */
    @Transactional
    public ApiResult updateDestinationPark(DestinationPark park) {
        TripOrderResultDto order = getTripOrderByNum(park.getOrderNum());
        RelativePark relativePark = new RelativePark();
        if (0 == order.getStatus()) {//行程中订单可修改
            Park parkInfo = parkDao.findById(park.getDestinationParkId());
            if (parkInfo == null) {
                log.error("停车场不存在 ，" + JsonUtil.beanToJson(park));
                return new ApiResult(ResponseEnum.PARK_NOT_EXIST.getValue(), ResponseEnum.PARK_NOT_EXIST.getDesc(), relativePark);
            }
            if (parkInfo.getParkCountBalance() <= 0) {
                log.error("停车场车位不足，" + JsonUtil.beanToJson(parkInfo));
                return new ApiResult(ResponseEnum.PARK_COUNT_NOT_ENOUGH.getValue(), ResponseEnum.PARK_COUNT_NOT_ENOUGH.getDesc(), relativePark);
            }
            tripOrderDao.updateDestinationPark(park);
            //原终点停车场车位数 +1
            parkDao.updateById(updateParkCount(order.getDestinationParkId(), 1));
            //更换停车场车位数 -1
            parkDao.updateById(updateParkCount(park.getDestinationParkId(), -1));
            relativePark.setParkCountBalance(parkInfo.getParkCountBalance() - 1);
            relativePark.setAddress(parkInfo.getAddress());
            relativePark.setId(parkInfo.getId());
            relativePark.setLatitude(parkInfo.getLatitude());
            relativePark.setLongitude(parkInfo.getLongitude());
            relativePark.setName(parkInfo.getName());
            return ApiResult.successWithData(relativePark);
        }
        log.error("更新重点停车场信息失败：" + JsonUtil.beanToJson(park));
        return ApiResult.errorWithData(relativePark);
    }

    /**
     * 更新停车场剩余车位数
     *
     * @param parkId 停车场id
     * @param amount 增减车位数
     */
    private Park updateParkCount(Long parkId, Integer amount) {
        Park park = parkDao.findById(parkId);
        Integer parkCountBalance = park.getParkCountBalance() + amount;
        //控制最小值不能小于0
        if (parkCountBalance <= 0) {
            parkCountBalance = 0;
        }
        //控制最大值不能大于停车位总数量
        if (parkCountBalance > park.getParkCount()) {
            parkCountBalance = park.getParkCount();
        }
        park.setParkCountBalance(parkCountBalance);
        park.setUpdateTime(new Date());
        return park;
    }

    /**
     * 声音寻车
     *
     * @param operateDevice 设备操作类
     */
    public ApiResult searchCarBySound(OperateDevice operateDevice) {
        ApiResult result = new ApiResult();
        //redis缓存锁
        String key = CONSTANT.SEARCH_CAR_REDIS + operateDevice.getOrderId();
        String value = StringUtil.getStr(redisTemplate.opsForValue().get(key));
        if (StringUtil.isEmpty(value)) {//无缓存则操作
            redisTemplate.opsForValue().set(key, operateDevice.getCarId());
            redisTemplate.expire(key, 10, TimeUnit.SECONDS);
            TripOrder order = tripOrderDao.findById(operateDevice.getOrderId());
            if (order != null && order.getUserId().equals(operateDevice.getUserId())) {
                result = searchCar(operateDevice.getCarId());
                redisTemplate.delete(key);
                return result;
            }
        }
        log.error("车辆寻车失败，operateDevice:" + JsonUtil.beanToJson(operateDevice));
        result.setCode(ResponseEnum.ERROR.getValue());
        result.setMessage(ResponseEnum.ERROR.getDesc());
        redisTemplate.delete(key);
        return result;
    }

    /**
     * 开门/锁门
     *
     * @param operateDevice 设备操作类
     */
    public ApiResult openOrCloseCar(OperateDevice operateDevice) {
        ApiResult result = new ApiResult();
        //redis缓存锁
        String key = CONSTANT.OPEN_CLOSE_CAR_REDIS + operateDevice.getOrderId();
        String value = StringUtil.getStr(redisTemplate.opsForValue().get(key));
        if (StringUtil.isEmpty(value)) {//无缓存则操作
            redisTemplate.opsForValue().set(key, operateDevice.getCarId());
            redisTemplate.expire(key, 10, TimeUnit.SECONDS);
            TripOrder order = tripOrderDao.findById(operateDevice.getOrderId());
            if (order != null && order.getUserId().equals(operateDevice.getUserId())) {
                result = openOrLockCar(operateDevice.getCarId(), operateDevice.getCode());
                redisTemplate.delete(key);
                return result;
            }
        }
        log.error("车辆开-关门失败，operateDevice:" + JsonUtil.beanToJson(operateDevice));
        result.setCode(ResponseEnum.ERROR.getValue());
        result.setMessage(ResponseEnum.ERROR.getDesc());
        redisTemplate.delete(key);
        return result;
    }

    /**
     * 声音寻车
     *
     * @param carId 车辆id
     */
    public ApiResult searchCar(Long carId) {
        ShowCarInfo carInfo = carCommonDao.getCarInfoById(carId);
        TcpResponse response = deviceService.findCar(carInfo.getDeviceId(), CONSTANT.FIND_CAR);
        Integer result = response.isStatus() ? 1 : 0;
        return ApiResult.validateSingleData(result);
    }

    /**
     * 开 关门
     *
     * @param carId 车辆id
     * @param code  操作码
     */
    public ApiResult openOrLockCar(Long carId, Integer code) {
        ShowCarInfo carInfo = carCommonDao.getCarInfoById(carId);
        TcpResponse response = deviceService.OpenOrCloseDoor(carInfo.getDeviceId(), code);
        Integer result = response.isStatus() ? 1 : 0;
        return ApiResult.validateSingleData(result);
    }

    /**
     * 计算用户实付金额
     */
    @Transactional
    public ApiResult calculatePayAmount(PayAmount payAmount) {
        ApiResult result = new ApiResult();
        //1.获取订单
        int realPayAmount = 0;
        TripOrderResultDto orderDto = this.tripOrderDao.getTripOrderByNum(payAmount.getOrderId());
        TripOrder order = new TripOrder();
        if (orderDto != null) {
            BeanUtils.copyProperties(orderDto, order);
            int shouldPayAmount = StringUtil.getInt(order.getShouldPayAmount());
            //1.判断是否存在用户优惠券关联id
            Integer preferentialAmount;
            Long preferentialId = StringUtil.getLong(payAmount.getPreferentialId());
            int parkFee = StringUtil.getInt(order.getParkFee());
            int deductible = StringUtil.getInt(order.getDeductible());
            //优惠券
            if (preferentialId != 0) {
                UserPreferentialCondition condition = new UserPreferentialCondition();
                condition.setId(preferentialId);
                condition.setUserId(payAmount.getUserId());
                preferentialAmount = userPreferentialDao.getUserPreferential(condition);
                int validAmount = shouldPayAmount - parkFee - deductible;//参与打折的金额
                if (StringUtil.getInt(preferentialAmount) != 0 && validAmount >= preferentialAmount) {
                    realPayAmount = validAmount - preferentialAmount;
                } else {
                    realPayAmount = 0;
                }
            }

            //2.判断是否为5折优惠卡用户
            if (payAmount.isHalfUser()) {
                realPayAmount = Integer.parseInt(new DecimalFormat("0").
                        format((realPayAmount * CONSTANT.HALF_OFF)));
            }

            //3.添加停车费&不计免赔费用
            realPayAmount = realPayAmount + parkFee + deductible;
            order.setRealPayAmount(realPayAmount == 0 ? 1 : realPayAmount);
            //写入数据库
            order.setPreferentialId(preferentialId);
            tripOrderDao.updateById(order);
            result.setData(order);
            return result;

        }
        log.error("订单信息为空，order:" + JsonUtil.beanToJson(payAmount));
        result.setCode(ResponseEnum.ERROR.getValue());
        result.setData(ResponseEnum.ERROR.getDesc());
        return result;
    }

    /**
     * 根据时间区间查询订单
     *
     * @param userId
     * @param start
     * @param end
     * @return
     */
    public List<TripOrderResultDto> queryOrdersByTimeRange(Long userId, Date start, Date end) {
        return this.tripOrderDao.queryOrdersByTimeRange(userId, start, end);
    }

    /**
     * 获取配置信息(充值赠送额度/押金额度/不计免赔额度等)见SettingTypeEnum
     */
    private GlobalSetting getSettingInfo(Integer type) {
        return tripOrderDao.getSettingInfo(type);
    }

    /**
     * 根据id获取折扣信息
     */
    private GlobalSetting getDiscountInfoById(Long discountId) {
        return tripOrderDao.getDiscountInfoById(discountId);
    }

    /**
     * 根据车牌号获取订单（模糊匹配）
     *
     * @param plateNumber
     * @return
     */
    public List<TripOrderResultDto> queryOrdersByPlateNum(String plateNumber) {
        return tripOrderDao.queryOrdersByPlateNum(plateNumber);
    }

    /**
     * 预约车辆
     *
     * @param reservation 预约车辆信息
     */
    @Transactional
    public ApiResult createReservate(ReservationInfo reservation) {
        ReturnReservationCar car = new ReturnReservationCar();
        UserAuthInfo userAuthInfo = tripOrderDao.getUserInfo(reservation.getUserId());
        //判断用户实名
        if (StringUtil.getInt(userAuthInfo.getIdCardState()) != UserAuth.AUDIT_PASS) {
            return new ApiResult(ResponseEnum.NO_IDCARD.getValue(), ResponseEnum.NO_IDCARD.getDesc(), car);
        }
        //驾驶认证
        if (StringUtil.getInt(userAuthInfo.getDriverState()) != UserAuth.AUDIT_PASS) {
            return new ApiResult(ResponseEnum.NO_DRIVER_CARD.getValue(), ResponseEnum.NO_DRIVER_CARD.getDesc(), car);
        }
        //押金是否交纳
        if (StringUtil.getInt(userAuthInfo.getDepositState()) != Deposit.STATE_PAYED) {
            return new ApiResult(ResponseEnum.NO_DEPOSIT.getValue(), ResponseEnum.NO_DEPOSIT.getDesc(), car);
        }
        //查询用户是否有未完成的订单
        String orderNum = tripOrderDao.queryUnfinishedOrder(reservation.getUserId());
        if (StringUtil.isNotEmpty(orderNum)) {
            car.setOrderNum(orderNum);
            car.setOrderType(ReturnReservationCar.ORDER_EXIST);
            return ApiResult.successWithData(car);
        }
        car.setOrderType(ReturnReservationCar.ORDER_NOT_EXIST);
        //查询用户是否已有预约
        ReservationCar reservationCar = new ReservationCar();
        String key = CONSTANT.RESERVATION_CAR + reservation.getUserId();
        String reservationInfo = (String) redisTemplate.opsForValue().get(key);
        if (StringUtil.isEmpty(reservationInfo)) {
            CarCommonInfo info = carCommonDao.findById(reservation.getCarId());
            if (info != null && info.getCarState() == 2) {
                //更新预约表用户预约中状态数据为超时状态
                reservationCarDao.updateUserReservation(reservation.getUserId(), ReservationCar.RESERVATION_OVER_TIME);
                //用户预约车辆
                BeanUtils.copyProperties(reservation, reservationCar);
                reservationCar.setReservationState(ReservationCar.RESERVATION_USED);
                reservationCar.setCreateTime(new Date());
                reservationCarDao.insert(reservationCar);
                CarCommonInfo carInfo = new CarCommonInfo();
                carInfo.setId(reservation.getCarId());
                carInfo.setCarState(CarCommonInfo.STATE_RESERVATION);
                carInfo.setUpdateTime(new Date());
                carCommonDao.updateCarState(carInfo);
                redisTemplate.opsForValue().set(key, JsonUtil.beanToJson(reservationCar));
                redisTemplate.expire(key, 15, TimeUnit.MINUTES);
            } else {
                log.error("车辆不存在或已被预约，reservation:" + JsonUtil.beanToJson(reservation));
                return ApiResult.errorWithData(car);
            }
        }
        car = reservationCarDao.queryReservationByUserId(reservation.getUserId());
        if (StringUtil.isEmpty(reservationInfo)) {
            updateCarState(car.getReservationId(), car.getUserId());
        }
        return ApiResult.successWithData(car);

    }

    /**
     * 延时事件修改预约超时车辆状态
     *
     * @param reservationId
     */
    private void updateCarState(Long reservationId, Long userId) {
        Timer timer = new Timer();
        log.info("-------启动延时修改车辆预约状态事件-----预约号：" + reservationId + ",userId: " + userId);
        UpdateCarEvent carEvent = new UpdateCarEvent(reservationId, userId);
        timer.schedule(carEvent, CONSTANT.DELAY_TIME);
    }

    /**
     * 查询用户预约信息
     *
     * @param userId 用户id
     */
    public ApiResult getReservation(Long userId) {
        ReturnReservationCar car = reservationCarDao.queryReservationByUserId(userId);
        if (car == null) {
            car = new ReturnReservationCar();
        }
        Device device = carInfoService.getDeviceInfo(car.getId());
        car.setRemainderRange(StringUtil.getInt(device.getRemainderRange()));
        return ApiResult.successWithData(car);
    }

    /**
     * 获取赠送比例&最大赠送额度
     */
    public ApiResult getPresent() {
        GlobalSetting setting = tripOrderDao.getSettingInfo(SettingTypeEnum.RECHARGE.getValue());
        GlobalSetting setting1 = tripOrderDao.getSettingInfo(SettingTypeEnum.RECHARGE_MAX.getValue());
        RechargeInfo rechargeInfo = new RechargeInfo();
        rechargeInfo.setMaxPresent(StringUtil.getInt(setting1));
        rechargeInfo.setRechargeRate(StringUtil.getDouble(setting));
        return ApiResult.successWithData(rechargeInfo);
    }


//    public static void main(String[] args) {
//        System.out.println(RandomStringUtil.random(100000000));
//    }
}
