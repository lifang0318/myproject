package com.rareboom.member.scheme;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class ModifyMemberLicenseScheme extends BaseScheme {

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "modifyMemberLicense.account.null")
	private String memberAccount;// 会员账号
	private String memberName;// 会员名称
	private String enterpriseId;// 企业Id
	private String enterpriseName;// 企业名称
	private String licenseStatus;// 状态
	private String StatusName;// 状态名称
	private Date validTime;// 有效期
	private String token;// token

	public String getMemberAccount() {
		return memberAccount;
	}

	public void setMemberAccount(String memberAccount) {
		this.memberAccount = memberAccount;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getLicenseStatus() {
		return licenseStatus;
	}

	public void setLicenseStatus(String licenseStatus) {
		this.licenseStatus = licenseStatus;
	}

	public String getStatusName() {
		return StatusName;
	}

	public void setStatusName(String statusName) {
		StatusName = statusName;
	}

	public Date getValidTime() {
		return validTime;
	}

	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
