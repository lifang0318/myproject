package com.renlink.push.msg.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.renlink.push.msg.logic.PushControllerLogic;
import com.renlink.push.msg.scheme.MessageScheme;
import com.renlink.push.msg.scheme.MessageSchemeCollection;
import com.renlink.push.msg.scheme.NotificationScheme;
import com.renlink.push.msg.scheme.NotificationSchemeCollection;

@Component
public class PushControllerListener implements MessageListener {

	private static Logger log = LoggerFactory
			.getLogger(PushControllerListener.class);

	@Autowired
	private PushControllerLogic pushControllerLogic;

	@Override
	public void onMessage(Message message) {
		try {
			if (message instanceof ObjectMessage) {

				log.trace("Push Controller Listener on messagae");

				ObjectMessage msg = (ObjectMessage) message;
				Object obj = msg.getObject();

				if (obj instanceof MessageScheme) {
					MessageScheme scheme = (MessageScheme) obj;
					pushControllerLogic.pushMsg(scheme);
				}

				if (obj instanceof MessageSchemeCollection) {
					MessageSchemeCollection collection = (MessageSchemeCollection) obj;
					pushControllerLogic.batchPushMsg(collection);
				}

				if (obj instanceof NotificationScheme) {
					NotificationScheme scheme = (NotificationScheme) obj;
					pushControllerLogic.pushNotification(scheme);
				}

				if (obj instanceof NotificationSchemeCollection) {
					NotificationSchemeCollection collection = (NotificationSchemeCollection) obj;
					pushControllerLogic.batchPushNotificaiton(collection);
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
