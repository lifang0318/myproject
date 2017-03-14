package com.rareboom.member.api.lang;

import java.io.Serializable;

public class GeneralResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String code = "0000";

	private String message;

	private Object body;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public void setActionStatus(ActionStatus status){
		this.code = status.getCode();
		this.message = status.getMsg();
	}
}
