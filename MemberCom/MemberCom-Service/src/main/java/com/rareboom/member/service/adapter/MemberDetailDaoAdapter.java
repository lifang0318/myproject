package com.rareboom.member.service.adapter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rareboom.member.dao.entity.MemberDetail;
import com.rareboom.member.dao.lang.MemberDetailFindCondition;
import com.rareboom.member.dao.lang.MemberRegisterCommand;
import com.rareboom.member.dao.lang.ModifyMemberCommand;
import com.rareboom.member.dao.rmi.IMemberDetailDaoSession;

@Component
public class MemberDetailDaoAdapter {
	@Autowired
	private IMemberDetailDaoSession memberDetailDaoSession;

	public void register(MemberRegisterCommand memberRegisterCommand) throws Exception {
		memberDetailDaoSession.register(memberRegisterCommand);
	}

	public void modify(ModifyMemberCommand modifyMemberCommand) throws Exception {
		memberDetailDaoSession.modify(modifyMemberCommand);
	}

	public MemberDetail find(String id) throws Exception {
		return memberDetailDaoSession.find(id);
	}

	public List<MemberDetail> findList(MemberDetailFindCondition condition) throws Exception {
		return memberDetailDaoSession.findList(condition);
	}

	public Integer findCount(MemberDetailFindCondition condition) throws Exception {
		return memberDetailDaoSession.findCount(condition);
	}
}
