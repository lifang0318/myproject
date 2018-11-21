package cn.com.izj.dto;

/**
 * @author: 朱鸿平
 * @date: 2018/6/25 21:36
 */
public class PayOrder {

    private Long userId;//用户id
    private String orderNum;//订单号

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
}
