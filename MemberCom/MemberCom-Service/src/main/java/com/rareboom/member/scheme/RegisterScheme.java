package com.rareboom.member.scheme;

import javax.validation.constraints.NotNull;


public class RegisterScheme extends BaseScheme {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "register.account.null")
	private String memberAccount;
	@NotNull(message = "register.password.null")
	private String loginPassword;

	private String registerLocation;

	private String memberPhone;

	private String memberStatus;
	
	private String statusName;
	
	private String MemberType;
	
	private String typeName;

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

	public String getRegisterLocation() {
		return registerLocation;
	}

	public void setRegisterLocation(String registerLocation) {
		this.registerLocation = registerLocation;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
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
		return MemberType;
	}

	public void setMemberType(String memberType) {
		MemberType = memberType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
   
}
