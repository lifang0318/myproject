package cn.com.izj.base.entity.car;

import cn.com.izj.base.entity.BaseEntity;
import cn.com.izj.mybatis.annotation.Table;

import java.util.Date;

/**
 * 车辆预约信息
 *
 * @author: 朱鸿平
 * @date: 2018/6/6 21:28
 */
@Table("reservation_car")
public class ReservationCar extends BaseEntity {

    public static final Integer RESERVATION_USED = 0;
    public static final Integer RESERVATION_OVER_TIME = 1;
    public static final Integer RESERVATION_CANCEL = 2;
    public static final Integer RESERVATION_SUCCESS = 3;

    private Long userId;//用户id
    private Long carId;//车辆id
    private Integer reservationState;//预约状态 0预约中 1 超时 2 取消 3 完成
    private Date cancelReservationTime;//取消预约时间
    private Long startParkId;//开始停车场id

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Integer getReservationState() {
        return reservationState;
    }

    public void setReservationState(Integer reservationState) {
        this.reservationState = reservationState;
    }

    public Date getCancelReservationTime() {
        return cancelReservationTime;
    }

    public void setCancelReservationTime(Date cancelReservationTime) {
        this.cancelReservationTime = cancelReservationTime;
    }

    public Long getStartParkId() {
        return startParkId;
    }

    public void setStartParkId(Long startParkId) {
        this.startParkId = startParkId;
    }

}
