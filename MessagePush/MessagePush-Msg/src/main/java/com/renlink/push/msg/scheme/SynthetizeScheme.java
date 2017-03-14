package com.renlink.push.msg.scheme;

public class SynthetizeScheme extends PushScheme {

	private static final long serialVersionUID = 1L;

	private NotificationScheme notification;

	private MessageScheme message;

	public NotificationScheme getNotification() {
		return notification;
	}

	public void setNotification(NotificationScheme notification) {
		this.notification = notification;
	}

	public MessageScheme getMessage() {
		return message;
	}

	public void setMessage(MessageScheme message) {
		this.message = message;
	}

}
