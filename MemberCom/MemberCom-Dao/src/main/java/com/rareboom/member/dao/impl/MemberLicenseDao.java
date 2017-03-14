package com.rareboom.member.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.rareboom.member.dao.IMemberLicenseDao;
import com.rareboom.member.dao.entity.MemberLicense;
import com.rareboom.member.dao.lang.MemberLicenseFindCondition;

public class MemberLicenseDao {
	@Autowired
	private IMemberLicenseDao memberLicenseDao;

	public void add(MemberLicense license) throws Exception {
		try {
			memberLicenseDao.insert(license);
		} catch (Exception e) {
			throw e;
		}
	}

	public void update(MemberLicense license) throws Exception {
		try {
			memberLicenseDao.update(license);
		} catch (Exception e) {
			throw e;
		}
	}

	public MemberLicense find(String id) throws Exception {
		try {
			return memberLicenseDao.get(id);
		} catch (Exception e) {
			throw e;
		}
	}

	public List<MemberLicense> findList(MemberLicenseFindCondition condition) throws Exception {
		try {
			return memberLicenseDao.findList(condition);
		} catch (Exception e) {
			throw e;
		}
	}
	public Integer findCount(MemberLicenseFindCondition condition) throws Exception {
		try {
			return memberLicenseDao.findCount(condition);
		} catch (Exception e) {
			throw e;
		}
	}
}
