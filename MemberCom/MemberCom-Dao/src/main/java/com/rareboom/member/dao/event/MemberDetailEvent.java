package com.rareboom.member.dao.event;

import org.springframework.context.ApplicationEvent;

public class MemberDetailEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	private Action action;

	public MemberDetailEvent(Object source) {
		super(source);
	}

	public Action getAction() {
		return this.action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public enum Action {
		ADD, UPDATE;
	}
}
