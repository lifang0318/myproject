package com.rareboom.member.service.adapter;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.rareboom.member.dao.entity.MemberLicense;
import com.rareboom.member.dao.lang.MemberLicenseFindCondition;
import com.rareboom.member.dao.lang.ModifyMemberLicenseCommand;
import com.rareboom.member.dao.rmi.IMemberLicenseDaoSession;

@Component
public class MemberLicenseDaoAdapter {
	@Autowired
	private IMemberLicenseDaoSession memberLicenseDaoSession;

	public void add(MemberLicense License) throws Exception {
		memberLicenseDaoSession.add(License);
	}

	public void update(ModifyMemberLicenseCommand command) throws Exception {
		memberLicenseDaoSession.update(command);
	}

	public MemberLicense find(String account) throws Exception {
		return memberLicenseDaoSession.find(account);
	}

	public List<MemberLicense> findList(MemberLicenseFindCondition condition) throws Exception {
		return memberLicenseDaoSession.findList(condition);
	}

	public Integer findCount(MemberLicenseFindCondition condition) throws Exception {
		return memberLicenseDaoSession.findCount(condition);
	}
}
