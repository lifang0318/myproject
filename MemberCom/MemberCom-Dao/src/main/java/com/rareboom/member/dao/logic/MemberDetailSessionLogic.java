package com.rareboom.member.dao.logic;

import org.springframework.stereotype.Component;
import com.rareboom.member.dao.event.MemberDetailEvent;
import com.rareboom.member.dao.lang.MemberRegisterCommand;
import com.rareboom.member.dao.lang.ModifyMemberCommand;

@Component
public class MemberDetailSessionLogic extends BaseSessionLogic {
	public void add(MemberRegisterCommand command) throws Exception {
		try {
			MemberDetailEvent event = new MemberDetailEvent(command);
			event.setAction(MemberDetailEvent.Action.ADD);
			sendEvent(event);
		} catch (Exception e) {
			throw e;
		}
	}

	public void update(ModifyMemberCommand command) throws Exception {
		try {
			MemberDetailEvent event = new MemberDetailEvent(command);
			event.setAction(MemberDetailEvent.Action.UPDATE);
			sendEvent(event);
		} catch (Exception e) {
			throw e;
		}
	}
}
