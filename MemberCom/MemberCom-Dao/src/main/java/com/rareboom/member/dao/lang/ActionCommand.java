package com.rareboom.member.dao.lang;

import java.io.Serializable;
import com.alibaba.druid.support.json.JSONUtils;

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

	public String toJson() {
		return JSONUtils.toJSONString(this);
	}

	public enum Action {
		ADD,MODIFY;
	}

}
