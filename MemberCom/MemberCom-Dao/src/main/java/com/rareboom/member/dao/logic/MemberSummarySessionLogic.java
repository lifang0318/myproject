package com.rareboom.member.dao.logic;

import org.springframework.stereotype.Component;
import com.rareboom.member.dao.entity.MemberSummary;
import com.rareboom.member.dao.event.MemberSummaryEvent;

@Component
public class MemberSummarySessionLogic extends BaseSessionLogic {
	public void add(MemberSummary Summary) throws Exception {
		try {
			MemberSummaryEvent event = new MemberSummaryEvent(Summary);
			event.setAction(MemberSummaryEvent.Action.ADD);
			sendEvent(event);
		} catch (Exception e) {
			throw e;
		}
	}

	public void update(MemberSummary Summary) throws Exception {
		try {
			MemberSummaryEvent event = new MemberSummaryEvent(Summary);
			event.setAction(MemberSummaryEvent.Action.UPDATE);
			sendEvent(event);
		} catch (Exception e) {
			throw e;
		}
	}
}
