package com.rareboom.member.dao.entity;

import java.util.Date;

import com.rd.jbasis.datalink.persistence.GeneralEntity;



public class MemberDetail extends GeneralEntity {

	private static final long serialVersionUID = 1L;
	private String memberId;// 会员id
	private String memberAccount;// 会员账号
	private String loginPassword; // 登录密码
	private String memberType;// 会员类型
	private String typeName;// 类型名称
	private String memberStatus;// 会员状态A、N A-激活N-未激活
	private String statusName;// 状态名称
	private String headImage;// 会员头像
	private String nickName;// 会员昵称
	private String memberName;// 会员名称
	private String memberPhone;// 会员手机
	private String memberMail;// 会员邮箱
	private Date registerTime;// 注册时间
	private Date registerDate;// 注册日期
	private String registerLocation;// 注册地
	private Date memberBirthday;// 会员生日
	private String extendProperty;// 扩展属性
	private String memberImage;// 会员图片信息
	private String sex;// 会员性别
	private Integer age; // 会员年龄
	private String memberChannel;// 会员渠道
	private String channelName;// 渠道名称
	private String vestEnterpriseId;// 归属企业id
	private String vestEnterpriseName;// 归属企业名称

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

	@Override
	public String toString() {
		return "MemberDetail [memberId=" + memberId + ", memberAccount=" + memberAccount + ", memberType=" + memberType
				+ ", typeName=" + typeName + ", memberStatus=" + memberStatus + ", statusName=" + statusName
				+ ", headImage=" + headImage + ", nickName=" + nickName + ", memberName=" + memberName
				+ ", memberPhone=" + memberPhone + ", memberMail=" + memberMail + ", registerTime=" + registerTime
				+ ", registerDate=" + registerDate + ", registerLocation=" + registerLocation + ", memberBirthday="
				+ memberBirthday + ", extendProperty=" + extendProperty + ", memberImage=" + memberImage + ", sex="
				+ sex + ", age=" + age + ", memberChannel=" + memberChannel + ", channelName=" + channelName
				+ ", vestEnterpriseId=" + vestEnterpriseId + ", vestEnterpriseName=" + vestEnterpriseName + "]";
	}

}
