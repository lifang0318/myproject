package com.rareboom.member.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.rareboom.member.dao.IMemberDetailDao;
import com.rareboom.member.dao.entity.MemberDetail;
import com.rareboom.member.dao.lang.MemberDetailFindCondition;

public class MemberDetailDao {
	@Autowired
	private IMemberDetailDao memberDetailDao;

	public void add(MemberDetail detail) throws Exception {
		try {
			memberDetailDao.insert(detail);
		} catch (Exception e) {
			throw e;
		}
	}

	public void update(MemberDetail detail) throws Exception {
		try {
			memberDetailDao.update(detail);
		} catch (Exception e) {
			throw e;
		}
	}

	public MemberDetail find(String id) throws Exception {
		try {
			return memberDetailDao.get(id);
		} catch (Exception e) {
			throw e;
		}
	}

	public List<MemberDetail> findList(MemberDetailFindCondition condition) throws Exception {
		try {
			return memberDetailDao.findList(condition);
		} catch (Exception e) {
			throw e;
		}
	}
	public Integer findCount(MemberDetailFindCondition condition) throws Exception {
		try {
			return memberDetailDao.findCount(condition);
		} catch (Exception e) {
			throw e;
		}
	}
   
}
