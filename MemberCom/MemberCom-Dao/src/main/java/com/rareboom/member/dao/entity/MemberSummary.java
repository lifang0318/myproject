package com.rareboom.member.dao.entity;

import java.util.Date;

import com.rd.jbasis.datalink.persistence.GeneralEntity;

public class MemberSummary extends GeneralEntity {

	private static final long serialVersionUID = 1L;
	
	private String memberId; // 会员ID
	private String memberAccount; // 会员账号
	private String loginPassword; // 登录密码
	private String memberStatus; // 会员状态A、N A-激活N-未激活
	private String statusName; // 状态名称
	private String memberType; // 会员类型
	private String typeName; // 类型名称
	private String headImage; // 会员头像
	private String nickName; // 会员昵称
	private String memberName; // 会员名称
	private String memberPhone; // 会员手机
	private String memberMail; // 会员邮箱
	private Date registerTime; // 注册时间
	private Date registerDate; // 注册日期
	private String registerLocation; // 注册地
	private Date validTime;//有效期
	private Date lastLoginDate; // 最后一次登录日期
	private Date lastLoginTime; // 最后一次登录时间
	private String token;

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

	public Date getValidTime() {
		return validTime;
	}

	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "MemberSummary [memberId=" + memberId + ", memberAccount=" + memberAccount + ", loginPassword="
				+ loginPassword + ", memberStatus=" + memberStatus + ", statusName=" + statusName + ", memberType="
				+ memberType + ", typeName=" + typeName + ", headImage=" + headImage + ", nickName=" + nickName
				+ ", memberName=" + memberName + ", memberPhone=" + memberPhone + ", memberMail=" + memberMail
				+ ", registerTime=" + registerTime + ", registerDate=" + registerDate + ", registerLocation="
				+ registerLocation + ", validTime=" + validTime + ", lastLoginDate=" + lastLoginDate
				+ ", lastLoginTime=" + lastLoginTime + ", token=" + token + "]";
	}
}
