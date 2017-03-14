package com.renlink.push.msg.scheme;

public class IosNotificationScheme extends NotificationScheme{

	private static final long serialVersionUID = 1L;
	
	private Integer badge;
	private String sound;
	private String category;
	private boolean contentAvailable = false;
	
	public Integer getBadge() {
		return badge;
	}
	public void setBadge(Integer badge) {
		this.badge = badge;
	}
	public String getSound() {
		return sound;
	}
	public void setSound(String sound) {
		this.sound = sound;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public boolean isContentAvailable() {
		return contentAvailable;
	}
	public void setContentAvailable(boolean contentAvailable) {
		this.contentAvailable = contentAvailable;
	}
	
}
