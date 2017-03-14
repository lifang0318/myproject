package com.renlink.push.msg.scheme;

public class AndroidNotificationScheme extends NotificationScheme{

	private static final long serialVersionUID = 1L;
	
	private Integer builderId;

	public Integer getBuilderId() {
		return builderId;
	}

	public void setBuilderId(Integer builderId) {
		this.builderId = builderId;
	}
	
}
