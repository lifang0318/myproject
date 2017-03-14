package com.rareboom.member.api;

import org.springframework.stereotype.Controller;

@Controller
public interface IMemberFindController {

	public String findMemberSummaryById(String jsonStr);

	public String findMemberSummaryByAccount(String jsonStr);

	public String findMemberSummaryList(String jsonStr);

	public String findMemberDetailById(String jsonStr);

	public String findMemberDetailList(String jsonStr);

	public String findMemberLicenseByAccount(String jsonStr);

	public String findMemberLicenseList(String jsonStr);

}
