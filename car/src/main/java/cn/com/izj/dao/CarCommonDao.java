package cn.com.izj.dao;

import cn.com.izj.base.entity.LocationPoint;
import cn.com.izj.base.entity.car.CarCommonInfo;
import cn.com.izj.dto.MyCarInfo;
import cn.com.izj.dto.QueryCarCondition;
import cn.com.izj.dto.ShowCarInfo;
import cn.com.izj.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: 朱鸿平
 * @date: 2018/6/8 22:18
 */
public interface CarCommonDao extends BaseMapper<CarCommonInfo, Long> {

    /**
     * 获取车辆展示信息
     *
     * @param id 车辆id
     */
    ShowCarInfo getCarInfoById(Long id);

    /**
     * 获取可预约车辆
     *
     * @param parkId 停车场id
     */
    List<ShowCarInfo> getReservationList(@Param("parkId") Long parkId);

    /**
     * 根据车牌号查询车辆信息
     *
     * @param plateNumber 车牌号
     */
    CarCommonInfo queryByPlateNumber(String plateNumber);

    /**
     * 获取我的车辆
     *
     * @param userId 用户id
     */
    List<MyCarInfo> getMyCarList(@Param("userId") Long userId);

    /**
     * 根据设备id获取对应车辆Id
     *
     * @param deviceId
     * @return
     */
    CarCommonInfo getCarIdByDeviceId(@Param("deviceId") String deviceId);

    /**
     * 根据停车场id获取位置信息
     *
     * @param parkId
     */
    LocationPoint getParkInfo(@Param("parkId") Long parkId);

    /**
     * 获取车辆列表
     */
    List<ShowCarInfo> getCarList(QueryCarCondition condition);

    /**
     * 修改预约车辆状态
     *
     * @param reservationId 预约订单id
     * @param state         车辆状态
     */
    int updateReservationCarState(@Param("id") Long reservationId, @Param("state") Integer state);

    /**
     * 修改空闲车辆--预约状态
     *
     * @param carInfo
     */
    void updateCarState(CarCommonInfo carInfo);

    /**
     * 根据停车场id获取停车场剩余车位数
     *
     * @param parkId
     */
    Integer findParkCountBalance(Long parkId);

    /**
     * 停车场可用车位数量减少 1
     */
    void updateParkCount(Long parkId);
}
