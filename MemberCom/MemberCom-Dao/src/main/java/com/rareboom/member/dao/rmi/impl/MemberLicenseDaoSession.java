package com.rareboom.member.dao.rmi.impl;

import java.util.List;

import javax.jms.Destination;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.rareboom.member.dao.IMemberLicenseDao;
import com.rareboom.member.dao.entity.MemberLicense;
import com.rareboom.member.dao.lang.ActionCommand;
import com.rareboom.member.dao.lang.ActionCommand.Action;
import com.rareboom.member.dao.lang.MemberLicenseFindCondition;
import com.rareboom.member.dao.lang.ModifyMemberLicenseCommand;
import com.rareboom.member.dao.rmi.IMemberLicenseDaoSession;

@Service
public class MemberLicenseDaoSession extends BaseDaoSession implements IMemberLicenseDaoSession {
	
	private static Logger log = LoggerFactory.getLogger(MemberLicenseDaoSession.class);
	
	@Autowired
	private IMemberLicenseDao memberLicenseDao;
	
	@Autowired
	@Qualifier("memberLicenseDaoQueue")
	private Destination destination;


	public void add(@Valid MemberLicense memberLicense) throws Exception {
		log.trace("RMI ADD Request");

		try {
			ActionCommand action = new ActionCommand();
			action.setAction(Action.ADD);
			action.setBody(memberLicense);
			sendMessage(destination, action);
		} catch (Exception e) {
			throw e;
		}
	}


	public void update(@Valid ModifyMemberLicenseCommand command) throws Exception {
		log.trace("RMI Update License Request");

		try {
			ActionCommand action = new ActionCommand();
			action.setAction(Action.MODIFY);
			action.setBody(command);
			sendMessage(destination, action);
		} catch (Exception e) {
			throw e;
		}
	}


	public MemberLicense find(String account) throws Exception {
		try {
			return memberLicenseDao.get(account);
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
