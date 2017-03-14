package com.renlink.push.msg.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import com.renlink.push.msg.event.PushMessageEvent;
import com.renlink.push.msg.event.PushNotificationEvent;
import com.renlink.push.msg.lang.ServiceProperty;
import com.renlink.push.msg.scheme.MessageScheme;
import com.renlink.push.msg.scheme.MessageSchemeCollection;
import com.renlink.push.msg.scheme.NotificationScheme;
import com.renlink.push.msg.scheme.NotificationSchemeCollection;

@Component
public class PushControllerLogic {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private ServiceProperty property;

	public <T extends ApplicationEvent> void sendEvent(T event) {
		applicationContext.publishEvent(event);
	}

	public void pushMsg(MessageScheme scheme) throws Exception {

		PushMessageEvent event = new PushMessageEvent(scheme);
		event.setAction(PushMessageEvent.Action.ONE);
		try {
			sendEvent(event);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void pushNotification(NotificationScheme scheme) throws Exception {
		PushNotificationEvent event = new PushNotificationEvent(scheme);
		event.setAction(PushNotificationEvent.Action.ONE);
		try {
			sendEvent(event);
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	public void batchPushMsg(MessageSchemeCollection collection)
			throws Exception {

		if (collection != null && collection.size() > 0) {
			int batchSize = property.getBatchSize();
			int size = collection.size();
			
			int arrySize = size%batchSize==0?size/batchSize:size/batchSize+1;			
			for(int i = 0 ; i <arrySize ; i++ ){

								
			}
		}

		PushMessageEvent event = new PushMessageEvent(collection);
		event.setAction(PushMessageEvent.Action.BATCH);
		try {
			sendEvent(event);
		} catch (Exception e) {
			throw e;
		}
	}


	public void batchPushNotificaiton(NotificationSchemeCollection collection)
			throws Exception {

		if (collection != null && collection.size() > 0) {

		}
		
		
		PushNotificationEvent event = new PushNotificationEvent(collection);
		event.setAction(PushNotificationEvent.Action.BATCH);
		try {
			sendEvent(event);
		} catch (Exception e) {
			throw e;
		}
	}

}
