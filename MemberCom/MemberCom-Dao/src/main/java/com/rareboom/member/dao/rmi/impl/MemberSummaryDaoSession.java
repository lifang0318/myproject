package com.rareboom.member.dao.rmi.impl;

import java.util.List;

import javax.jms.Destination;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.rareboom.member.dao.IMemberSummaryDao;
import com.rareboom.member.dao.entity.MemberSummary;
import com.rareboom.member.dao.lang.ActionCommand;
import com.rareboom.member.dao.lang.ActionCommand.Action;
import com.rareboom.member.dao.lang.MemberSummaryFindCondition;
import com.rareboom.member.dao.rmi.IMemberSummaryDaoSession;

@Service
public class MemberSummaryDaoSession extends BaseDaoSession implements IMemberSummaryDaoSession {
	
	private static Logger log = LoggerFactory.getLogger(MemberSummaryDaoSession.class);
	@Autowired
	private IMemberSummaryDao memberSummaryDao;
	
	@Autowired
	@Qualifier("memberSummaryDaoQueue")
	private Destination destination;


	public void add(@Valid MemberSummary memberSummary) throws Exception {
		log.trace("RMI ADD Request");
		try {
			ActionCommand action = new ActionCommand();
			action.setAction(Action.ADD);
			action.setBody(memberSummary);
			sendMessage(destination, action);
		} catch (Exception e) {
			throw e;
		}
	}


	public void update(@Valid MemberSummary memberSummary) throws Exception {
		log.trace("RMI Update MemberSummary Request");

		try {
			ActionCommand action = new ActionCommand();
			action.setAction(Action.MODIFY);
			action.setBody(memberSummary);
			sendMessage(destination, action);
		} catch (Exception e) {
			throw e;
		}
	}


	public MemberSummary find(String memberId) throws Exception {
		try {
			return memberSummaryDao.get(memberId);
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
