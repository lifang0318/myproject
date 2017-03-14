package com.rareboom.member.api.lang;

import java.io.Serializable;

public class GeneralRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Object body;

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}
	
	
	
}
