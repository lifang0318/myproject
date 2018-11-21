package cn.com.izj.condition;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author: lifang
 * @description:行程订单查询条件
 * @date: Created in 2018/6/12 0:08
 * @version:
 */
public class TripOrderFindCondition {
    private Long id;
    private String number;//订单号
    private Long carId;//车辆id
    private Long userId;//用户id
    private String username;//手机号
    private Long startParkId;//起点停车场id
    private Long destinationParkId;//终点停车场id
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;//创建日期最小值
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;//创建时间最大值
    private Integer shouldPayAmount;//应付金额
    private Integer realPayAmount;//实付金额
    private Integer ownerEarnAmount;//车主收益
    private Date payTime;//支付时间
    private Integer payType;//支付类型 0：微信支付 1：支付宝支付
    private Integer mileage;//行程里程
    private Integer durationTime;//行程时间
    private Integer deductibleStatus;//不计免赔 0：未购买 1：已购买
    private Integer status;//状态 0：行程中 1：未付款 2：已完成
    private Long discountId;//折扣id
    private Long preferentialId;//优惠券id
    private Integer pageSize;//每页数量
    private Integer pageNumber;//第几页

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

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

    public Long getDestinationParkId() {
        return destinationParkId;
    }

    public void setDestinationParkId(Long destinationParkId) {
        this.destinationParkId = destinationParkId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getShouldPayAmount() {
        return shouldPayAmount;
    }

    public void setShouldPayAmount(Integer shouldPayAmount) {
        this.shouldPayAmount = shouldPayAmount;
    }

    public Integer getRealPayAmount() {
        return realPayAmount;
    }

    public void setRealPayAmount(Integer realPayAmount) {
        this.realPayAmount = realPayAmount;
    }

    public Integer getOwnerEarnAmount() {
        return ownerEarnAmount;
    }

    public void setOwnerEarnAmount(Integer ownerEarnAmount) {
        this.ownerEarnAmount = ownerEarnAmount;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public Integer getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(Integer durationTime) {
        this.durationTime = durationTime;
    }

    public Integer getDeductibleStatus() {
        return deductibleStatus;
    }

    public void setDeductibleStatus(Integer deductibleStatus) {
        this.deductibleStatus = deductibleStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

    public Long getPreferentialId() {
        return preferentialId;
    }

    public void setPreferentialId(Long preferentialId) {
        this.preferentialId = preferentialId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPageSize() {
        if(this.pageSize == null){
           return -1;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        if(this.pageNumber == null ){
            return 1;
        }
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
}
