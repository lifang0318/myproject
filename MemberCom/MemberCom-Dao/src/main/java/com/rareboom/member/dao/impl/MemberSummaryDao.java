package com.rareboom.member.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.rareboom.member.dao.IMemberSummaryDao;
import com.rareboom.member.dao.entity.MemberSummary;
import com.rareboom.member.dao.lang.MemberSummaryFindCondition;

public class MemberSummaryDao {
	@Autowired
	private IMemberSummaryDao memberSummaryDao;

	public void add(MemberSummary summary) throws Exception {
		try {
			memberSummaryDao.insert(summary);
		} catch (Exception e) {
			throw e;
		}
	}

	public void update(MemberSummary summary) throws Exception {
		try {
			memberSummaryDao.update(summary);
		} catch (Exception e) {
			throw e;
		}
	}

	public MemberSummary find(String account) throws Exception {
		try {
			return memberSummaryDao.get(account);
		} catch (Exception e) {
			throw e;
		}
	}

	public List<MemberSummary> findList(MemberSummaryFindCondition condition) throws Exception {
		try {
			return memberSummaryDao.findList(condition);
		} catch (Exception e) {
			throw e;
		}
	}
	public Integer findCount(MemberSummaryFindCondition condition) throws Exception {
		try {
			return memberSummaryDao.findCount(condition);
		} catch (Exception e) {
			throw e;
		}
	}
}
