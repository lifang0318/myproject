package cn.com.izj.dto;

/**
 * @author: 朱鸿平
 * @date: 2018/6/22 21:46
 */
public class DestinationPark {

    private String orderNum;//订单号
    private Long destinationParkId;//终点停车场id

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Long getDestinationParkId() {
        return destinationParkId;
    }

    public void setDestinationParkId(Long destinationParkId) {
        this.destinationParkId = destinationParkId;
    }
}
