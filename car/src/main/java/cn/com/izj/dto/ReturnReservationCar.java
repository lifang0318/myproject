package cn.com.izj.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 返回预约信息
 *
 * @author: 朱鸿平
 * @date: 2018/6/9 9:46
 */
public class ReturnReservationCar extends BaseCarInfo {

    public static final Integer ORDER_NOT_EXIST = 0;
    public static final Integer ORDER_EXIST = 1;

    private String orderNum;//用户未完成订单号
    private Integer orderType;//用户是否有未完成订单 0没有 1 有
    private Long reservationId;//预约id
    private Long userId;//用户id
    private String name;//开始停车场名称
    private String address;//开始停车场地址
    private BigDecimal longitude;//所在位置经度
    private BigDecimal latitude;//所在位置纬度
    private Date createTime;//预约创建时间
    private Integer remainderRange;//最大续航里程 km

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getRemainderRange() {
        return remainderRange;
    }

    public void setRemainderRange(Integer remainderRange) {
        this.remainderRange = remainderRange;
    }
}
