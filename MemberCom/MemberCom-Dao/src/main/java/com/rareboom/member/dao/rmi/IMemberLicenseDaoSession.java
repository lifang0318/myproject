package com.rareboom.member.dao.rmi;

import java.util.List;

import com.rareboom.member.dao.entity.MemberLicense;
import com.rareboom.member.dao.lang.MemberLicenseFindCondition;
import com.rareboom.member.dao.lang.ModifyMemberLicenseCommand;

public interface IMemberLicenseDaoSession {
	public void add(MemberLicense memberLicense) throws Exception;

	public void update(ModifyMemberLicenseCommand command) throws Exception;

	public MemberLicense find(String id) throws Exception;

	public List<MemberLicense> findList(MemberLicenseFindCondition condition) throws Exception;

	public Integer findCount(MemberLicenseFindCondition condition) throws Exception;
}
