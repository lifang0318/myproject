package com.rareboom.member.dao.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.rareboom.member.dao.lang.ActionCommand;
import com.rareboom.member.dao.lang.ActionCommand.Action;
import com.rareboom.member.dao.lang.MemberRegisterCommand;
import com.rareboom.member.dao.lang.ModifyMemberCommand;
import com.rareboom.member.dao.logic.MemberDetailSessionLogic;

@Component("memberDetailSessionListener")
public class MemberDetailSessionListener implements MessageListener {
	private static Logger log = LoggerFactory.getLogger(MemberDetailSessionListener.class);
	@Autowired
	private MemberDetailSessionLogic memberDetailSessionLogic;


	public void onMessage(Message message) {
		log.trace("MemberDetail Session Listener Receive Message");
		try {

			if (message instanceof ObjectMessage) {
				ObjectMessage objmsg = (ObjectMessage) message;
				ActionCommand command = (ActionCommand) objmsg.getObject();
				Action action = command.getAction();
				switch (action) {
				case ADD:
					MemberRegisterCommand registerCommand = (MemberRegisterCommand) command.getBody();
					memberDetailSessionLogic.add(registerCommand);
					break;
				case MODIFY:
					ModifyMemberCommand modifyMemberCommand=(ModifyMemberCommand) command.getBody();
					memberDetailSessionLogic.update(modifyMemberCommand);
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
