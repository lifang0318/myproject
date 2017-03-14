package com.rareboom.member.dao.logic;

import org.springframework.stereotype.Component;
import com.rareboom.member.dao.entity.MemberLicense;
import com.rareboom.member.dao.event.MemberLicenseEvent;
import com.rareboom.member.dao.lang.ModifyMemberLicenseCommand;

@Component
public class MemberLicenseSessionLogic extends BaseSessionLogic {
	public void add(MemberLicense License) throws Exception {
		try {
			MemberLicenseEvent event = new MemberLicenseEvent(License);
			event.setAction(MemberLicenseEvent.Action.ADD);
			sendEvent(event);
		} catch (Exception e) {
			throw e;
		}
	}

	public void update(ModifyMemberLicenseCommand command) throws Exception {
		try {
			MemberLicenseEvent event = new MemberLicenseEvent(command);
			event.setAction(MemberLicenseEvent.Action.UPDATE);
			sendEvent(event);
		} catch (Exception e) {
			throw e;
		}
	}
}
