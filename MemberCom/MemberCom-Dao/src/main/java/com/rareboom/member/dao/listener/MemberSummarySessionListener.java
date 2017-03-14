package com.rareboom.member.dao.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.rareboom.member.dao.entity.MemberSummary;
import com.rareboom.member.dao.lang.ActionCommand;
import com.rareboom.member.dao.lang.ActionCommand.Action;
import com.rareboom.member.dao.logic.MemberSummarySessionLogic;

@Component("memberSummarySessionListener")
public class MemberSummarySessionListener implements MessageListener {
	private static Logger log = LoggerFactory.getLogger(MemberSummarySessionListener.class);
	@Autowired
	private MemberSummarySessionLogic memberSummarySessionLogic;


	public void onMessage(Message message) {
		log.trace("MemberSummary Session Listener Receive Message");
		try {

			if (message instanceof ObjectMessage) {
				ObjectMessage objmsg = (ObjectMessage) message;
				ActionCommand command = (ActionCommand) objmsg.getObject();
				Action action = command.getAction();
				switch (action) {
				case ADD:
					MemberSummary addSummary = (MemberSummary) command.getBody();
					memberSummarySessionLogic.add(addSummary);
					break;
				case MODIFY:
					MemberSummary updateSummary = (MemberSummary) command.getBody();
					memberSummarySessionLogic.update(updateSummary);
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
