package com.rareboom.member.dao.type;

public enum MemberType {
	
	COMMON("C","普通"),UNAUTHORIZED("U","未授权"),OVERVALIDDATE("O","超期");
	
	private String tag;
	
	private String tagName;
	
	MemberType(String tag, String tagName){
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
