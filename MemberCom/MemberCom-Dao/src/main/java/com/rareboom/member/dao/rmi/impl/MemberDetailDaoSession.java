package com.rareboom.member.dao.rmi.impl;

import java.util.List;

import javax.jms.Destination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.rareboom.member.dao.IMemberDetailDao;
import com.rareboom.member.dao.entity.MemberDetail;
import com.rareboom.member.dao.lang.ActionCommand;
import com.rareboom.member.dao.lang.ActionCommand.Action;
import com.rareboom.member.dao.lang.MemberDetailFindCondition;
import com.rareboom.member.dao.lang.MemberRegisterCommand;
import com.rareboom.member.dao.lang.ModifyMemberCommand;
import com.rareboom.member.dao.rmi.IMemberDetailDaoSession;

@Component
public class MemberDetailDaoSession extends BaseDaoSession implements IMemberDetailDaoSession {
	
	private static Logger log = LoggerFactory.getLogger(MemberDetailDaoSession.class);
	
	@Autowired
	private IMemberDetailDao memberDetailDao;
	
	@Autowired
	@Qualifier("memberDetailDaoQueue")
	private Destination destination;

	public void register(MemberRegisterCommand command) throws Exception {
		log.trace("RMI REGISTER Request");

		try {
			ActionCommand action = new ActionCommand();
			action.setAction(Action.ADD);
			action.setBody(command);
			sendMessage(destination, action);
		} catch (Exception e) {
			throw e;
		}
	}

	public void modify(ModifyMemberCommand command) throws Exception {
		log.trace("RMI MODIFY MEMBER Request");

		try {
			ActionCommand action = new ActionCommand();
			action.setAction(Action.MODIFY);
			action.setBody(command);
			sendMessage(destination, action);
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
