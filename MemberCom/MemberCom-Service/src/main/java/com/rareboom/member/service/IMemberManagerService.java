package com.rareboom.member.service;

import javax.validation.Valid;
import com.rareboom.member.dao.entity.MemberSummary;
import com.rareboom.member.scheme.LoginScheme;
import com.rareboom.member.scheme.MemberLicenseScheme;
import com.rareboom.member.scheme.ModifyMemberLicenseScheme;
import com.rareboom.member.scheme.ModifyMemberScheme;
import com.rareboom.member.scheme.RegisterScheme;
import com.rareboom.member.scheme.UploadHeadImageScheme;

public interface IMemberManagerService {

	public void register(@Valid RegisterScheme scheme) throws Exception;

	public MemberSummary login(@Valid LoginScheme scheme) throws Exception;

	public void modifyMember(@Valid ModifyMemberScheme scheme) throws Exception;

	public void addMemberLicense(@Valid MemberLicenseScheme scheme) throws Exception;

	public void modifyMemberLicense(@Valid ModifyMemberLicenseScheme scheme) throws Exception;

	public String uploadHeadImage(@Valid UploadHeadImageScheme scheme) throws Exception;

}
