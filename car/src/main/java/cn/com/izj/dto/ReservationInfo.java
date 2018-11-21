package cn.com.izj.dto;

/**
 * 车辆预约类
 *
 * @author: 朱鸿平
 * @date: 2018/6/9 2:50
 */
public class ReservationInfo {

    private Long carId;//车辆id
    private Long userId;//用户id
    private Long startParkId;//开始停车场id

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStartParkId() {
        return startParkId;
    }

    public void setStartParkId(Long startParkId) {
        this.startParkId = startParkId;
    }

}
