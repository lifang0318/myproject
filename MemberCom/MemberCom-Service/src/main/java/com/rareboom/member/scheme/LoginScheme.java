package com.rareboom.member.scheme;

import org.hibernate.validator.constraints.NotEmpty;

public class LoginScheme extends BaseScheme {

	private static final long serialVersionUID = 1L;
	@NotEmpty(message = "login.account.null")
	private String memberAccount;
	@NotEmpty(message = "login.password.null")
	private String loginPassword;
	private String memberType;
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

}
