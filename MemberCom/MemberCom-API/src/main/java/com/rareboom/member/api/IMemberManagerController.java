package com.rareboom.member.api;

public interface IMemberManagerController {
	public String registerMember(String jsonStr);

	public String loginMember(String jsonStr);

	public String modifyMember(String jsonStr);
	
	public String addMemberLicense(String jsonStr);
	
	public String modifyMemberLicense(String jsonStr);
	
	public String uploadHeadImage(String jsonStr);
}
