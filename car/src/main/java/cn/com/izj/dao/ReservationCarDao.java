package cn.com.izj.dao;

import cn.com.izj.base.entity.car.ReservationCar;
import cn.com.izj.dto.ReturnReservationCar;
import cn.com.izj.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author: 朱鸿平
 * @date: 2018/6/11 20:46
 */
public interface ReservationCarDao extends BaseMapper<ReservationCar, Long> {

    /**
     * 查询用户预约车辆信息
     *
     * @param userId 用户id
     */
    ReturnReservationCar queryReservationByUserId(@Param("userId") Long userId);


    /**
     * 更新旧预约数据到超时状态
     *
     * @param userId        用户Id
     * @param overtimeState 超时状态
     */
    void updateUserReservation(@Param("userId") Long userId, @Param("overtimeState") Integer overtimeState);
}
