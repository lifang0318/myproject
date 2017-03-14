package com.rareboom.member.dao.type;

public enum MemberStatus {
	
	
	ACTIVATION("A","激活"),UNACTIVATION("N","待激活");
	
	private String tag;
	private String tagName;
	
	MemberStatus(String tag, String tagName){
		this.tag = tag;
		this.tagName = tagName;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	
}
