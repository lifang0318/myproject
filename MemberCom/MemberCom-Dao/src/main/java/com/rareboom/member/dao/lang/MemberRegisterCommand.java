package com.rareboom.member.dao.lang;

import java.io.Serializable;
import com.rareboom.member.dao.entity.MemberDetail;
import com.rareboom.member.dao.entity.MemberSummary;

public class MemberRegisterCommand implements Serializable {
	private static final long serialVersionUID = 1L;

	private MemberDetail detail;

	private MemberSummary summary;

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
