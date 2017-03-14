package com.renlink.push.msg.event;

import org.springframework.context.ApplicationEvent;

public class PushMessageEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	
	private Action action;

	public PushMessageEvent(Object source) {
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
