package cn.com.izj.dto;

/**
 * @author: 朱鸿平
 * @date: 2018/7/20 20:16
 */
public class PayAmount {

    private String orderId;//订单id
    private Long preferentialId;//优惠券id
    private Long userId;//用户id
    private boolean halfUser;//是否五折优惠

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getPreferentialId() {
        return preferentialId;
    }

    public void setPreferentialId(Long preferentialId) {
        this.preferentialId = preferentialId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isHalfUser() {
        return halfUser;
    }

    public void setHalfUser(boolean halfUser) {
        this.halfUser = halfUser;
    }
}
