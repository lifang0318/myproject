package cn.com.izj.base.entity.car;

import cn.com.izj.base.entity.BaseEntity;
import cn.com.izj.mybatis.annotation.Table;

import java.util.Date;

/**
 * 车辆信息
 *
 * @author: 朱鸿平
 * @date: 2018/6/6 20:15
 */
@Table("car_common_info")
public class CarCommonInfo extends BaseEntity {

    public static final Integer STATE_AUDIT = 0;//审核中
    public static final Integer STATE_AUDIT_FAILURE = 1;//审核不通过
    public static final Integer STATE_FREE = 2;//空闲 上线/使用完成/取消预约
    public static final Integer STATE_AUDIT_PASS = 3;//审核通过
    public static final Integer STATE_IN_USE = 4;//使用中
    public static final Integer STATE_DOWNLINE = 5;//已下线
    public static final Integer STATE_RESERVATION = 6;//已预约
    public static final Integer STATE_DELETE = 7;//已删除

    private String plateNumber;//车牌号
    private String brand;//品牌
    private Integer seatNumber;//座位数
    private String carColor;//车辆颜色
    private Integer carGrade;//车辆等级
    private Integer carType;//车辆类型 0小轿车 1越野车
    private Integer carState;//车辆状态 0审核中 1审核失败 2空闲 3审核通过 4使用中 5已下线 6已预约 7已删除
    private String vehicleLicensePhotoMaster;//行驶证正面照片url
    private String vehicleLicensePhotoSlave;//行驶证背面照片url
    private Date onlineTime;//最近上线时间
    private Date downlineTime;//最近下线时间
    private String remarks;//备注
    private Long ownerId;//车主id
    private Long auditorId;//审核者id

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public Integer getCarGrade() {
        return carGrade;
    }

    public void setCarGrade(Integer carGrade) {
        this.carGrade = carGrade;
    }

    public Integer getCarType() {
        return carType;
    }

    public void setCarType(Integer carType) {
        this.carType = carType;
    }

    public Integer getCarState() {
        return carState;
    }

    public void setCarState(Integer carState) {
        this.carState = carState;
    }

    public String getVehicleLicensePhotoMaster() {
        return vehicleLicensePhotoMaster;
    }

    public void setVehicleLicensePhotoMaster(String vehicleLicensePhotoMaster) {
        this.vehicleLicensePhotoMaster = vehicleLicensePhotoMaster;
    }

    public String getVehicleLicensePhotoSlave() {
        return vehicleLicensePhotoSlave;
    }

    public void setVehicleLicensePhotoSlave(String vehicleLicensePhotoSlave) {
        this.vehicleLicensePhotoSlave = vehicleLicensePhotoSlave;
    }

    public Date getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Date onlineTime) {
        this.onlineTime = onlineTime;
    }

    public Date getDownlineTime() {
        return downlineTime;
    }

    public void setDownlineTime(Date downlineTime) {
        this.downlineTime = downlineTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Long auditorId) {
        this.auditorId = auditorId;
    }
}
