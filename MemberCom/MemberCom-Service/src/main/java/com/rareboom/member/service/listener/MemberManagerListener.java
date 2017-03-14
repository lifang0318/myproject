package com.rareboom.member.service.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.rareboom.member.scheme.MemberLicenseScheme;
import com.rareboom.member.scheme.ModifyMemberLicenseScheme;
import com.rareboom.member.scheme.ModifyMemberScheme;
import com.rareboom.member.scheme.RegisterScheme;
import com.rareboom.member.service.lang.ActionCommand;
import com.rareboom.member.service.lang.ActionCommand.Action;
import com.rareboom.member.service.logic.MemberManagerLogic;

@Component("memberManagerListener")
public class MemberManagerListener implements MessageListener {
	private static Logger loger = LogManager.getLogger(MemberManagerListener.class);

	@Autowired
	private MemberManagerLogic memberManagerLogic;

	@Override
	public void onMessage(Message message) {
		loger.trace("MemberManagerListener Receive Message");
		try {

			if (message instanceof ObjectMessage) {
				ObjectMessage objmsg = (ObjectMessage) message;
				ActionCommand command = (ActionCommand) objmsg.getObject();
				Action action = command.getAction();
				switch (action) {
				case REGISTER:
					RegisterScheme registerShceme=(RegisterScheme)command.getBody();
					memberManagerLogic.register(registerShceme);
					break;
				case MODIFY_MEMBER:
					ModifyMemberScheme modifyMemberScheme = (ModifyMemberScheme)command.getBody();
					memberManagerLogic.modifyMember(modifyMemberScheme);
					break;
				case MODIFY_MEMBERLICENSE:
					ModifyMemberLicenseScheme modifyMemberLicenseScheme = (ModifyMemberLicenseScheme)command.getBody();
					memberManagerLogic.modifyMemberLicense(modifyMemberLicenseScheme);
					break;
				case ADD_MEMBERLICENSE:
					MemberLicenseScheme memberLicenseScheme = (MemberLicenseScheme)command.getBody();
					memberManagerLogic.addMemberLicense(memberLicenseScheme);
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			loger.error(e.getMessage(), e);
		}
		
	}
}