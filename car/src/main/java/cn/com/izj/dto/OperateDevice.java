package cn.com.izj.dto;

/**
 * @author: 朱鸿平
 * @date: 2018/7/19 20:36
 */
public class OperateDevice {

    private Long orderId;//订单id
    private Long carId;//车辆id
    private Integer code;//操作码
    private Long userId;//操作人

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
