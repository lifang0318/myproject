package com.renlink.push.msg.event.listener;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.renlink.push.msg.adapter.IPushAdapter;
import com.renlink.push.msg.adapter.PushAdapterFactoryBean;
import com.renlink.push.msg.event.PushNotificationEvent;
import com.renlink.push.msg.scheme.NotificationScheme;
import com.renlink.push.msg.scheme.NotificationSchemeCollection;

@Component
public class PushNotificationEventListener implements
		ApplicationListener<PushNotificationEvent> {

	private static Logger log = LoggerFactory
			.getLogger(PushNotificationEventListener.class);

	@Autowired
	private PushAdapterFactoryBean pushAdapterFactory;

	@Override
	public void onApplicationEvent(PushNotificationEvent event) {
		try {
			PushNotificationEvent.Action action = event.getAction();

			switch (action) {
			case ONE:
				NotificationScheme scheme = (NotificationScheme) event
						.getSource();
				oneProcess(scheme);
				break;
			case BATCH:
				NotificationSchemeCollection schemes = (NotificationSchemeCollection) event
						.getSource();
				batchProcess(schemes);
				break;
			default:
				break;
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void oneProcess(NotificationScheme scheme) {
		push(scheme);
	}

	private void batchProcess(NotificationSchemeCollection schemes) {
		if (schemes != null && schemes.size() > 0) {
			Iterator<NotificationScheme> itor = schemes.iterator();
			while (itor.hasNext()) {
				push(itor.next());
			}
		}
	}

	private void push(NotificationScheme scheme) {
		try {
			IPushAdapter adapter = pushAdapterFactory.getPushAdapter();
			adapter.pushNotification(scheme);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
