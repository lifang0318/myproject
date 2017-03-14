package com.rareboom.member.scheme;

import java.util.Date;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import com.rareboom.member.scheme.valid.IsExistEmail;
import com.rareboom.member.scheme.valid.IsExistNickName;

public class ModifyMemberScheme extends BaseScheme {

	private static final long serialVersionUID = 1L;
	
	private String memberId;
	@NotEmpty(message = "{modifyMember.account.null}")
	private String memberAccount;
	@Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$", message = "{modifyMember.passwd.invalid}")
	private String loginPassword;
	private String memberStatus;
	private String statusName;
	private String memberType;
	private String typeName;
	private String headImage;
	@IsExistNickName(message = "{modifyMember.nickName.invalid}")
	private String nickName;
	private String memberName;
	private String memberPhone;
	@IsExistEmail(message = "{modifyMember.eamil.exist}")
	@Email(message = "{modifyMember.email.invalid}")
	private String memberMail;
	private Date registerTime;
	private Date registerDate;
	private String registerLocation;
	@Past(message = "modifyMember.birthday.invalid")
	private Date memberBirthday;
	private String extendProperty;
	private String memberImage;
	private String sex;
	private Integer age;
	private String memberChannel;
	private String channelName;
	private String vestEnterpriseId;
	private String vestEnterpriseName;
	private Date validTime;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberAccount() {
		return memberAccount;
	}

	public void setMemberAccount(String memberAccount) {
		this.memberAccount = memberAccount;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public String getMemberMail() {
		return memberMail;
	}

	public void setMemberMail(String memberMail) {
		this.memberMail = memberMail;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getRegisterLocation() {
		return registerLocation;
	}

	public void setRegisterLocation(String registerLocation) {
		this.registerLocation = registerLocation;
	}

	public Date getMemberBirthday() {
		return memberBirthday;
	}

	public void setMemberBirthday(Date memberBirthday) {
		this.memberBirthday = memberBirthday;
	}

	public String getExtendProperty() {
		return extendProperty;
	}

	public void setExtendProperty(String extendProperty) {
		this.extendProperty = extendProperty;
	}

	public String getMemberImage() {
		return memberImage;
	}

	public void setMemberImage(String memberImage) {
		this.memberImage = memberImage;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getMemberChannel() {
		return memberChannel;
	}

	public void setMemberChannel(String memberChannel) {
		this.memberChannel = memberChannel;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getVestEnterpriseId() {
		return vestEnterpriseId;
	}

	public void setVestEnterpriseId(String vestEnterpriseId) {
		this.vestEnterpriseId = vestEnterpriseId;
	}

	public String getVestEnterpriseName() {
		return vestEnterpriseName;
	}

	public void setVestEnterpriseName(String vestEnterpriseName) {
		this.vestEnterpriseName = vestEnterpriseName;
	}

	public Date getValidTime() {
		return validTime;
	}

	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}

}
