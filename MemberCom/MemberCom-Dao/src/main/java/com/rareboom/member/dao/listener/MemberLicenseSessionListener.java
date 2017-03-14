package com.rareboom.member.dao.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.rareboom.member.dao.entity.MemberLicense;
import com.rareboom.member.dao.lang.ActionCommand;
import com.rareboom.member.dao.lang.ActionCommand.Action;
import com.rareboom.member.dao.lang.ModifyMemberLicenseCommand;
import com.rareboom.member.dao.logic.MemberLicenseSessionLogic;

@Component("memberLicenseSessionListener")
public class MemberLicenseSessionListener implements MessageListener {
	private static Logger log = LoggerFactory.getLogger(MemberLicenseSessionListener.class);
	@Autowired
	private MemberLicenseSessionLogic memberLicenseSessionLogic;


	public void onMessage(Message message) {
		log.trace("MemberLicense Session Listener Receive Message");
		try {

			if (message instanceof ObjectMessage) {
				ObjectMessage objmsg = (ObjectMessage) message;
				ActionCommand command = (ActionCommand) objmsg.getObject();
				Action action = command.getAction();
				switch (action) {
				case ADD:
					MemberLicense addLicense = (MemberLicense) command.getBody();
					memberLicenseSessionLogic.add(addLicense);
					break;
				case MODIFY:
					ModifyMemberLicenseCommand modifyLicense = (ModifyMemberLicenseCommand) command.getBody();
					memberLicenseSessionLogic.update(modifyLicense);
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
