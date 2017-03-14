package com.rareboom.member.dao.event;

import org.springframework.context.ApplicationEvent;

public class MemberLicenseEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	private Action action;

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public MemberLicenseEvent(Object source) {
		super(source);
	}

	public enum Action {
		ADD, UPDATE;
	}
}
