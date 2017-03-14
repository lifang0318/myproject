package com.rareboom.member.service.adapter;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.rareboom.member.dao.entity.MemberSummary;
import com.rareboom.member.dao.lang.MemberSummaryFindCondition;
import com.rareboom.member.dao.rmi.IMemberSummaryDaoSession;

@Component
public class MemberSummaryDaoAdapter {
	@Autowired
	private IMemberSummaryDaoSession memberSummaryDaoSession;

	public void add(MemberSummary Summary) throws Exception {
		memberSummaryDaoSession.add(Summary);
	}

	public void update(MemberSummary Summary) throws Exception {
		memberSummaryDaoSession.update(Summary);
	}

	public MemberSummary find(String memberId) throws Exception {
		return memberSummaryDaoSession.find(memberId);
	}

	public List<MemberSummary> findList(MemberSummaryFindCondition condition) throws Exception {
		return memberSummaryDaoSession.findList(condition);
	}

	public Integer findCount(MemberSummaryFindCondition condition) throws Exception {
		return memberSummaryDaoSession.findCount(condition);
	}
}
