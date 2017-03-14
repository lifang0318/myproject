package com.rareboom.member.dao.entity;

import java.util.Date;

import com.rd.jbasis.datalink.persistence.GeneralEntity;

public class MemberLicense extends GeneralEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String memberAccount;//会员账号
	private String memberName;//会员名称
	private String enterpriseId;//企业Id
	private String enterpriseName;//企业名称
	private Date createDate;//创建日期
	private Date createTime;//创建时间
	private String licenseStatus;//状态
	private String StatusName;//状态名称
	private Date validTime;//有效期
    private String token;//token
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	@Override
	public String toString() {
		return "MemberLicense [memberAccount=" + memberAccount + ", memberName=" + memberName + ", enterpriseId="
				+ enterpriseId + ", enterpriseName=" + enterpriseName + ", createDate=" + createDate + ", createTime="
				+ createTime + ", licenseStatus=" + licenseStatus + ", StatusName=" + StatusName + ", validTime="
				+ validTime + ", token=" + token + "]";
	}
}
