package cn.com.izj.base.entity;

import java.util.Date;

/**
 * Created by GouBo on 2018/8/19 13:07.
 */
public class UserAuthInfo {

    private Long userId;//用户id
    private String username;//用户姓名
    private Integer state;//用户状态 0禁用 1正常
    private Integer depositState;//押金信息 0未交押金 1 已交
    private Integer balance;//用户余额
    private Integer giveBalance;//赠送余额
    private Integer idCardState;//实名认证状态 0未认证，1审核中 2已审核 3认证失败
    private Integer driverState;//驾驶证认证状态 0未认证，1审核中 2已审核 3认证失败
    private Date createTime;//创建时间
    private String realName;//真实姓名
    private String cardNumber;//身份证号码
    private Integer halfUser;//是否5折用户
    private String handCardPhoto;//手持身份证照片
    private String identityCardPhotoFront;//身份证正面url
    private String identityCardPhotoBehind;//身份证背面url
    private String driverLicencePhotoMaster;//驾驶证正本url
    private String driverLicencePhotoSlave;//驾驶证副本url

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getDepositState() {
        return depositState;
    }

    public void setDepositState(Integer depositState) {
        this.depositState = depositState;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getGiveBalance() {
        return giveBalance;
    }

    public void setGiveBalance(Integer giveBalance) {
        this.giveBalance = giveBalance;
    }

    public Integer getIdCardState() {
        return idCardState;
    }

    public void setIdCardState(Integer idCardState) {
        this.idCardState = idCardState;
    }

    public Integer getDriverState() {
        return driverState;
    }

    public void setDriverState(Integer driverState) {
        this.driverState = driverState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getHalfUser() {
        return halfUser;
    }

    public void setHalfUser(Integer halfUser) {
        this.halfUser = halfUser;
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
}
