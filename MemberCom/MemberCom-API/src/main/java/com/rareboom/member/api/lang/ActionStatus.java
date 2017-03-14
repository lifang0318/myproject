package com.rareboom.member.api.lang;

public enum ActionStatus {
	ACTION_FAIL("0001", "Action to fail"), REQUEST_FAIL("0002", "Request to fail");

	private String code;
	private String msg;

	ActionStatus(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
