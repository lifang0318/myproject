package com.rareboom.member.dao.event.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.rareboom.member.dao.IMemberDetailDao;
import com.rareboom.member.dao.IMemberSummaryDao;
import com.rareboom.member.dao.entity.MemberDetail;
import com.rareboom.member.dao.entity.MemberSummary;
import com.rareboom.member.dao.event.MemberDetailEvent;
import com.rareboom.member.dao.lang.MemberRegisterCommand;
import com.rareboom.member.dao.lang.ModifyMemberCommand;

@Component
public class MemberDetailEventListener implements ApplicationListener<MemberDetailEvent> {
	private static Logger log = LoggerFactory.getLogger(MemberDetailEventListener.class);

	@Autowired
	private IMemberDetailDao memberDetailDao;

	@Autowired
	private IMemberSummaryDao memberSummaryDao;

	public void onApplicationEvent(MemberDetailEvent event) {
		MemberDetailEvent.Action action = event.getAction();
		switch (action) {
		case ADD:
			MemberRegisterCommand registerCommand = (MemberRegisterCommand) event.getSource();
			add(registerCommand);
			break;
		case UPDATE:
			ModifyMemberCommand modifyCommand = (ModifyMemberCommand) event.getSource();
			update(modifyCommand);
			break;
		default:
			break;

		}

	}

	@Transactional(rollbackFor = Exception.class)
	private void add(MemberRegisterCommand command) {
		try {
			MemberDetail detail = command.getDetail();
			MemberSummary summary = command.getSummary();
			memberDetailDao.insert(detail);
			memberSummaryDao.insert(summary);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	private void update(ModifyMemberCommand command) {
		try {
			MemberDetail detail = command.getDetail();
			MemberSummary summary = command.getSummary();
			memberDetailDao.update(detail);
			memberSummaryDao.update(summary);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
