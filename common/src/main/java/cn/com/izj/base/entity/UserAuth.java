package cn.com.izj.base.entity;

import cn.com.izj.mybatis.annotation.Table;

/**
 * @author: 朱鸿平
 * @date: 2018/8/12 17:58
 */
@Table("user_auth")
public class UserAuth extends BaseEntity {

    public static final Integer AUDIT_NOT = 0;
    public static final Integer AUDIT_IN = 1;
    public static final Integer AUDIT_PASS = 2;
    public static final Integer AUDIT_FAIL = 3;

    private Long userId;//用户id
    private String realName;//真实姓名
    private String identityCardNumber;//身份证号码
    private String handCardPhoto;//手持身份证照片
    private String identityCardPhotoFront;//身份证正面url
    private String identityCardPhotoBehind;//身份证背面url
    private Integer realNameAuthStatus;//身份证认证状态 0未认证，1审核中 2已审核 3认证失败
    private String cardRemark;//身份证审核失败原因
    private String driverLicencePhotoMaster;//驾驶证正本url
    private String driverLicencePhotoSlave;//驾驶证副本url
    private Integer driverLicenceAuthStatus;//驾驶证认证状态 0未认证，1审核中 2已审核 3认证失败
    private String driverRemark;//驾驶证审核失败原因
    private Long auditorId;//审核者id

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdentityCardNumber() {
        return identityCardNumber;
    }

    public void setIdentityCardNumber(String identityCardNumber) {
        this.identityCardNumber = identityCardNumber;
    }

    public String getHandCardPhoto() {
        return handCardPhoto;
    }

    public void setHandCardPhoto(String handCardPhoto) {
        this.handCardPhoto = handCardPhoto;
    }

    public String getIdentityCardPhotoFront() {
        return identityCardPhotoFront;
    }

    public void setIdentityCardPhotoFront(String identityCardPhotoFront) {
        this.identityCardPhotoFront = identityCardPhotoFront;
    }

    public String getIdentityCardPhotoBehind() {
        return identityCardPhotoBehind;
    }

    public void setIdentityCardPhotoBehind(String identityCardPhotoBehind) {
        this.identityCardPhotoBehind = identityCardPhotoBehind;
    }

    public Integer getRealNameAuthStatus() {
        return realNameAuthStatus;
    }

    public void setRealNameAuthStatus(Integer realNameAuthStatus) {
        this.realNameAuthStatus = realNameAuthStatus;
    }

    public String getCardRemark() {
        return cardRemark;
    }

    public void setCardRemark(String cardRemark) {
        this.cardRemark = cardRemark;
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

    public Long getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Long auditorId) {
        this.auditorId = auditorId;
    }
}
