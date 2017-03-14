package com.rareboom.member.service.lang;

import java.io.Serializable;

public class ActionCommand implements Serializable {

	private static final long serialVersionUID = 1L;

	private Action action;
	private Object body;

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public enum Action {
		REGISTER,LOGIN,MODIFY_MEMBER,MODIFY_MEMBERLICENSE,ADD_MEMBERLICENSE;
	}

}
