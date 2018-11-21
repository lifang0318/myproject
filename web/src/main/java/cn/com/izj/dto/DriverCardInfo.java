package cn.com.izj.dto;

/**
 * @author: 朱鸿平
 * @date: 2018/8/12 21:22
 */
public class DriverCardInfo {

    private Long userId;//用户id
    private String driverLicencePhotoMaster;//驾驶证正本url
    private String driverLicencePhotoSlave;//驾驶证副本url
    private Integer driverLicenceAuthStatus;//驾驶证认证状态 0未认证，1审核中 2已审核 3认证失败
    private String driverRemark;//驾驶证审核失败原因

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDriverLicencePhotoMaster() {
        return driverLicencePhotoMaster;
    }

    public void setDriverLicencePhotoMaster(String driverLicencePhotoMaster) {
        this.driverLicencePhotoMaster = driverLicencePhotoMaster;
    }

    public String getDriverLicencePhotoSlave() {
        return driverLicencePhotoSlave;
    }

    public void setDriverLicencePhotoSlave(String driverLicencePhotoSlave) {
        this.driverLicencePhotoSlave = driverLicencePhotoSlave;
    }

    public Integer getDriverLicenceAuthStatus() {
        return driverLicenceAuthStatus;
    }

    public void setDriverLicenceAuthStatus(Integer driverLicenceAuthStatus) {
        this.driverLicenceAuthStatus = driverLicenceAuthStatus;
    }

    public String getDriverRemark() {
        return driverRemark;
    }

    public void setDriverRemark(String driverRemark) {
        this.driverRemark = driverRemark;
    }
}
