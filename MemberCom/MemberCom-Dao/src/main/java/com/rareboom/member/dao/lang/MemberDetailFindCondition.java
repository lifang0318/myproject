package com.rareboom.member.dao.lang;

import com.rd.jbasis.datalink.persistence.BaseCondition;

public class MemberDetailFindCondition extends BaseCondition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String memberAccount;
	private String memberPhone;
	private String memberMail;
	private String memberStatus;
	private String memberType;
	private String registerLocation;
	private String vestEnterpriseId;
   
	public String getMemberAccount() {
		return memberAccount;
	}

	public void setMemberAccount(String memberAccount) {
		this.memberAccount = memberAccount;
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

	public String getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getRegisterLocation() {
		return registerLocation;
	}

	public void setRegisterLocation(String registerLocation) {
		this.registerLocation = registerLocation;
	}

	public String getVestEnterpriseId() {
		return vestEnterpriseId;
	}

	public void setVestEnterpriseId(String vestEnterpriseId) {
		this.vestEnterpriseId = vestEnterpriseId;
	}

}
