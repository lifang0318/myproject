package com.renlink.push.msg.event.listener;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.renlink.push.msg.adapter.IPushAdapter;
import com.renlink.push.msg.adapter.PushAdapterFactoryBean;
import com.renlink.push.msg.event.PushMessageEvent;
import com.renlink.push.msg.scheme.MessageScheme;
import com.renlink.push.msg.scheme.MessageSchemeCollection;

@Component
public class PushMessageEventListener implements
		ApplicationListener<PushMessageEvent> {

	private static Logger log = LoggerFactory
			.getLogger(PushMessageEventListener.class);

	@Autowired
	private PushAdapterFactoryBean pushAdapterFactory;


	@Override
	public void onApplicationEvent(PushMessageEvent event) {
		try {

			PushMessageEvent.Action action = event.getAction();
			switch (action) {
			case ONE:
				MessageScheme scheme = (MessageScheme) event.getSource();
				oneProcees(scheme);
				break;
			case BATCH:
				MessageSchemeCollection schemes = (MessageSchemeCollection) event.getSource();
				batchProcess(schemes);
				break;
			default:
				break;
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void oneProcees(MessageScheme scheme) {
		push(scheme);
	}
	
	private void batchProcess(MessageSchemeCollection schemes) {

		if (schemes != null && schemes.size() > 0) {
			Iterator<MessageScheme> itor = schemes.iterator();
			while (itor.hasNext()) {
				push(itor.next());
			}
		}
	}

	private void push(MessageScheme scheme) {
		try {
			IPushAdapter adapter = pushAdapterFactory.getPushAdapter();
			adapter.pushMessage(scheme);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
