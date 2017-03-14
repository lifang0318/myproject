package com.rareboom.member.dao.rmi;

import java.util.List;

import com.rareboom.member.dao.entity.MemberDetail;
import com.rareboom.member.dao.lang.MemberDetailFindCondition;
import com.rareboom.member.dao.lang.MemberRegisterCommand;
import com.rareboom.member.dao.lang.ModifyMemberCommand;

public interface IMemberDetailDaoSession {
	public void register(MemberRegisterCommand command) throws Exception;

	public void modify(ModifyMemberCommand command) throws Exception;

	public MemberDetail find(String id) throws Exception;

	public List<MemberDetail> findList(MemberDetailFindCondition condition) throws Exception;

	public Integer findCount(MemberDetailFindCondition condition) throws Exception;
}
