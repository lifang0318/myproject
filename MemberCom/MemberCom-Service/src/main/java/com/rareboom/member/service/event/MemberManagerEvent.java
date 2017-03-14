package com.rareboom.member.service.event;

import org.springframework.context.ApplicationEvent;

public class MemberManagerEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	private Action action;

	public MemberManagerEvent(Object source) {
		super(source);
	}

	public Action getAction() {
		return this.action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public enum Action {
		REGISTER,MODIFY_MEMBER,MODIFY_MEMBERLICENSE,ADD_MEMBERLICENSE;
	}
}
