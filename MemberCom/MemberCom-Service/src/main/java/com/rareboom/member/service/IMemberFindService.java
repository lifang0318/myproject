package com.rareboom.member.service;

import java.util.List;
import javax.validation.Valid;
import com.rareboom.member.dao.entity.MemberDetail;
import com.rareboom.member.dao.entity.MemberLicense;
import com.rareboom.member.dao.entity.MemberSummary;
import com.rareboom.member.scheme.FindMemberDetailScheme;
import com.rareboom.member.scheme.FindMemberLicenseScheme;
import com.rareboom.member.scheme.FindMemberSummaryScheme;

public interface IMemberFindService {
	public MemberSummary findMemberSummaryById(String id) throws Exception;

	public List<MemberSummary> findMemberSummaryList(@Valid FindMemberSummaryScheme scheme) throws Exception;

	public MemberDetail findMemberDetailById(String id) throws Exception;

	public List<MemberDetail> findMemberDetailList(@Valid FindMemberDetailScheme scheme) throws Exception;

	public MemberLicense findMemberLicenseByAccount(String account) throws Exception;

	public List<MemberLicense> findMemberLicenseList(@Valid FindMemberLicenseScheme scheme) throws Exception;
	
}
