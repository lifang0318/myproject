package com.rareboom.member.dao.rmi;

import java.util.List;

import com.rareboom.member.dao.entity.MemberSummary;
import com.rareboom.member.dao.lang.MemberSummaryFindCondition;

public interface IMemberSummaryDaoSession {
	public void add(MemberSummary memberSummary) throws Exception;

	public void update(MemberSummary memberSummary) throws Exception;

	public MemberSummary find(String memberId) throws Exception;

	public List<MemberSummary> findList(MemberSummaryFindCondition condition) throws Exception;

	public Integer findCount(MemberSummaryFindCondition condition) throws Exception;
}
