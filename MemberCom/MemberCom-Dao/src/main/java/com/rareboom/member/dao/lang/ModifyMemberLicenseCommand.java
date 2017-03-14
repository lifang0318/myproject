package com.rareboom.member.dao.lang;

import java.io.Serializable;
import com.rareboom.member.dao.entity.MemberDetail;
import com.rareboom.member.dao.entity.MemberLicense;
import com.rareboom.member.dao.entity.MemberSummary;

public class ModifyMemberLicenseCommand implements Serializable {
	private static final long serialVersionUID = 1L;

	private MemberLicense license;

	private MemberDetail detail;

	private MemberSummary summary;

	public MemberLicense getLicense() {
		return license;
	}

	public void setLicense(MemberLicense license) {
		this.license = license;
	}

	public MemberDetail getDetail() {
		return detail;
	}

	public void setDetail(MemberDetail detail) {
		this.detail = detail;
	}

	public MemberSummary getSummary() {
		return summary;
	}

	public void setSummary(MemberSummary summary) {
		this.summary = summary;
	}

}
