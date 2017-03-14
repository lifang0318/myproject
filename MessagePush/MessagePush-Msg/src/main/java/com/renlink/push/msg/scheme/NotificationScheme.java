package com.renlink.push.msg.scheme;


public class NotificationScheme extends PushScheme {

	private static final long serialVersionUID = 1L;

	private String alert;

	public String getAlert() {
		return alert;
	}
	
	public void setAlert(String alert) {
		this.alert = alert;
	}
}
