package com.rareboom.member.dao.event.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.rareboom.member.dao.IMemberSummaryDao;
import com.rareboom.member.dao.entity.MemberSummary;
import com.rareboom.member.dao.event.MemberSummaryEvent;

@Component
public class MemberSummaryEventListener implements ApplicationListener<MemberSummaryEvent> {
	private static Logger log = LoggerFactory.getLogger(MemberSummaryEventListener.class);
	
	@Autowired
	private IMemberSummaryDao memberSummaryDao;


	public void onApplicationEvent(MemberSummaryEvent event) {
		MemberSummaryEvent.Action action = event.getAction();
		switch (action) {
		case ADD:
			MemberSummary addSummary = (MemberSummary) event.getSource();
			add(addSummary);
			break;
		case UPDATE:
			MemberSummary updateSummary = (MemberSummary) event.getSource();
			update(updateSummary);
			break;
		default:
			break;

		}
	}


	private void add(MemberSummary Summary) {
		try {
			memberSummaryDao.insert(Summary);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}


	private void update(MemberSummary Summary) {
		try {
			memberSummaryDao.update(Summary);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
