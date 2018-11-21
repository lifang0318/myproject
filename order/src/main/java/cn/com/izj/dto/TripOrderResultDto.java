package cn.com.izj.dto;

import java.util.Date;

/**
 * @author: lifang
 * @description: 订单查询返回结果-dto
 * @date: Created in 2018/8/14 1:32
 * @version:
 */
public class TripOrderResultDto {

    private Long id;
    private String number;//订单号
    private Long carId;//车辆id
    private String plateNumber;
    private Long userId;//用户id
    private Long startParkId;//起点停车场id
    private String startParkName;//起点停车场名称
    private Long destinationParkId;//终点停车场id
    private String destinationParkName;//终点停车场名称
    private Date startTime;//行程开始时间
    private Date endTime;//行程结束时间
    private Integer shouldPayAmount;//应付金额
    private Integer realPayAmount;//实付金额
    private Integer ownerEarnAmount;//车主收益
    private Date payTime;//支付时间
    private Integer payType;//支付类型 0：余额支付，1 微信支付 2：支付宝支付
    private Integer mileage;//行程里程
    private Long durationTime;//行程时间
    private Integer deductibleStatus;//不计免赔 0：未购买 1：已购买
    private Integer status;//状态 0：行程中 1：未付款 2：已完成
    private Long discountId;//折扣id
    private Long preferentialId;//优惠券id
    private Integer parkFee;//停车费
    private Integer deductible;//不计免赔费用
    private String url;//轨迹url


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

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
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

    public Long getStartParkId() {
        return startParkId;
    }

    public void setStartParkId(Long startParkId) {
        this.startParkId = startParkId;
    }

    public String getStartParkName() {
        return startParkName;
    }

    public void setStartParkName(String startParkName) {
        this.startParkName = startParkName;
    }

    public Long getDestinationParkId() {
        return destinationParkId;
    }

    public void setDestinationParkId(Long destinationParkId) {
        this.destinationParkId = destinationParkId;
    }

    public String getDestinationParkName() {
        return destinationParkName;
    }

    public void setDestinationParkName(String destinationParkName) {
        this.destinationParkName = destinationParkName;
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

    public Long getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(Long durationTime) {
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

    public Integer getParkFee() {
        return parkFee;
    }

    public void setParkFee(Integer parkFee) {
        this.parkFee = parkFee;
    }

    public Integer getDeductible() {
        return deductible;
    }

    public void setDeductible(Integer deductible) {
        this.deductible = deductible;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
