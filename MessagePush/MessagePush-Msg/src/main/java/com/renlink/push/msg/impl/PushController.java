package com.renlink.push.msg.impl;

import java.io.Serializable;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.renlink.push.msg.IController;
import com.renlink.push.msg.scheme.MessageScheme;
import com.renlink.push.msg.scheme.MessageSchemeCollection;
import com.renlink.push.msg.scheme.NotificationScheme;
import com.renlink.push.msg.scheme.NotificationSchemeCollection;
import com.renlink.push.msg.scheme.SynthetizeScheme;

@Service
public class PushController implements IController {

	private static Logger log = LoggerFactory.getLogger(PushController.class);

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	@Qualifier("pushQueue")
	private Destination destination;

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	private void sendMessage(Destination destination, Serializable object)
			throws JMSException {

		jmsTemplate.send(destination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				ObjectMessage msg = session.createObjectMessage(object);
				return msg;
			}
		});

	}

	@Override
	public void pushMsg(MessageScheme scheme) throws Exception {
		log.trace("Push Controller executing push message opertion");
		try {
			sendMessage(destination, scheme);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public void pushMsgAndroid(MessageScheme scheme) throws Exception {
		log.trace("Push Controller executing push message opertion");
		
		try {
			sendMessage(destination, scheme);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void pushNotification(NotificationScheme scheme) throws Exception {
		log.trace("Push Controller executing push notification opertion");
		try {
			sendMessage(destination, scheme);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void push(SynthetizeScheme scheme) throws Exception {
		try {
			sendMessage(destination, scheme);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void batchPushMsg(MessageSchemeCollection conllection)
			throws Exception {
		try{
			sendMessage(destination, conllection);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public void batchPushNotification(NotificationSchemeCollection conllection)
			throws Exception {
		try{
			sendMessage(destination, conllection);
		}catch(Exception e){
			throw e;
		}
	}

}
