package com.rareboom.member.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rareboom.member.dao.entity.MemberDetail;
import com.rareboom.member.dao.entity.MemberLicense;
import com.rareboom.member.dao.entity.MemberSummary;
import com.rareboom.member.dao.lang.MemberDetailFindCondition;
import com.rareboom.member.dao.lang.MemberLicenseFindCondition;
import com.rareboom.member.dao.lang.MemberSummaryFindCondition;
import com.rareboom.member.scheme.FindMemberDetailScheme;
import com.rareboom.member.scheme.FindMemberLicenseScheme;
import com.rareboom.member.scheme.FindMemberSummaryScheme;
import com.rareboom.member.service.IMemberFindService;
import com.rareboom.member.service.adapter.MemberDetailDaoAdapter;
import com.rareboom.member.service.adapter.MemberLicenseDaoAdapter;
import com.rareboom.member.service.adapter.MemberSummaryDaoAdapter;

@Service("memberFindService")
public class MemberFindService extends BaseManager implements IMemberFindService {
	private static Logger log = LoggerFactory.getLogger(MemberFindService.class);
	@Autowired
	private MemberDetailDaoAdapter memberDetailDaoAdapter;
	@Autowired
	private MemberSummaryDaoAdapter memberSummaryDaoAdapter;
	@Autowired
	private MemberLicenseDaoAdapter memberLicenseDaoAdapter;

	@Override
	public MemberSummary findMemberSummaryById(String id) throws Exception {
		try {
			return memberSummaryDaoAdapter.find(id);
		} catch (Exception e) {
			throw e;
		}
	}

	//@Cacheable(value = "memberSummaryCache", key = "#scheme")
	public List<MemberSummary> findMemberSummaryList(FindMemberSummaryScheme scheme) throws Exception {
		try {
			MemberSummaryFindCondition condition = new MemberSummaryFindCondition();
			condition.setMemberAccount(scheme.getMemberAccount());
			condition.setPassword(scheme.getPassword());
			condition.setMemberMail(scheme.getMemberMail());
			condition.setMemberPhone(scheme.getMemberPhone());
			condition.setMemberStatus(scheme.getMemberStatus());
			condition.setMemberType(scheme.getMemberType());
			condition.setRegisterLocation(scheme.getRegisterLocation());
			return memberSummaryDaoAdapter.findList(condition);

		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public MemberDetail findMemberDetailById(String id) throws Exception {
		try {
			return memberDetailDaoAdapter.find(id);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<MemberDetail> findMemberDetailList(FindMemberDetailScheme scheme) throws Exception {
		try {
			MemberDetailFindCondition condition = new MemberDetailFindCondition();
			condition.setMemberAccount(scheme.getMemberAccount());
			condition.setMemberPhone(scheme.getMemberPhone());
			condition.setMemberMail(scheme.getMemberMail());
			condition.setMemberStatus(scheme.getMemberStatus());
			condition.setMemberType(scheme.getMemberType());
			condition.setRegisterLocation(scheme.getRegisterLocation());
			return memberDetailDaoAdapter.findList(condition);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public MemberLicense findMemberLicenseByAccount(String account) throws Exception {
		try {
			return memberLicenseDaoAdapter.find(account);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<MemberLicense> findMemberLicenseList(FindMemberLicenseScheme scheme) throws Exception {

		try {
			MemberLicenseFindCondition condition = new MemberLicenseFindCondition();
			condition.setEnterpriseId(scheme.getEnterpriseId());
			condition.setEnterpriseName(scheme.getEnterpriseName());
			condition.setLicenseStatus(scheme.getLicenseStatus());
			return memberLicenseDaoAdapter.findList(condition);
		} catch (Exception e) {
			throw e;
		}
	}

}
