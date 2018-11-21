package cn.com.izj.dto;

/**
 * @author: 朱鸿平
 * @date: 2018/8/12 17:56
 */
public class IdCardInfo {

    private Long userId;//用户id
    private String realName;//真实姓名
    private String identityCardNumber;//身份证号码
    private String handCardPhoto;//手持身份证照片
    private String identityCardPhotoFront;//身份证正面url
    private String identityCardPhotoBehind;//身份证背面url
    private Integer realNameAuthStatus;//身份证认证状态 0未认证，1审核中 2已审核 3认证失败
    private String cardRemark;//身份证审核失败原因

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
}
