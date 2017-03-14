package com.renlink.push.msg.event;

import org.springframework.context.ApplicationEvent;

public class PushNotificationEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	
	private Action action;

	public PushNotificationEvent(Object source) {
		super(source);
	}
	
	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public enum Action {
		ONE, BATCH;
	}

}
