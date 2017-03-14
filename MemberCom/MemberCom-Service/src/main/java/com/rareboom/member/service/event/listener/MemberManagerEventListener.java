package com.rareboom.member.service.event.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import com.rareboom.member.dao.entity.MemberLicense;
import com.rareboom.member.dao.lang.MemberRegisterCommand;
import com.rareboom.member.dao.lang.ModifyMemberCommand;
import com.rareboom.member.dao.lang.ModifyMemberLicenseCommand;
import com.rareboom.member.service.adapter.MemberDetailDaoAdapter;
import com.rareboom.member.service.adapter.MemberLicenseDaoAdapter;
import com.rareboom.member.service.event.MemberManagerEvent;

@Component("memeberManagerListener")
public class MemberManagerEventListener implements ApplicationListener<MemberManagerEvent> {
	private static Logger log = LoggerFactory.getLogger(MemberManagerEventListener.class);
	@Autowired
	private MemberDetailDaoAdapter memberDetailDaoAdapter;
	@Autowired
	private MemberLicenseDaoAdapter memberLicenseDaoAdapter;

	@Override
	public void onApplicationEvent(MemberManagerEvent event) {
		MemberManagerEvent.Action action = event.getAction();
		switch (action) {
		case REGISTER:
			MemberRegisterCommand registerCommand = (MemberRegisterCommand) event.getSource();
			register(registerCommand);
			break;

		case MODIFY_MEMBER:
			ModifyMemberCommand modifyMemberCommand = (ModifyMemberCommand) event.getSource();
			updateMember(modifyMemberCommand);
			break;

		case MODIFY_MEMBERLICENSE:
			ModifyMemberLicenseCommand modifyLicenseCommand = (ModifyMemberLicenseCommand) event.getSource();
			updateMemberLicense(modifyLicenseCommand);
			break;
		case ADD_MEMBERLICENSE:
			MemberLicense License = (MemberLicense) event.getSource();
			addMemberLicense(License);
			break;
		default:
			break;
		}

	}

	private void register(MemberRegisterCommand command) {
		try {
			memberDetailDaoAdapter.register(command);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void updateMember(ModifyMemberCommand command) {
		try {
			memberDetailDaoAdapter.modify(command);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void addMemberLicense(MemberLicense License) {
		try {
			memberLicenseDaoAdapter.add(License);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void updateMemberLicense(ModifyMemberLicenseCommand command) {
		try {
			memberLicenseDaoAdapter.update(command);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
