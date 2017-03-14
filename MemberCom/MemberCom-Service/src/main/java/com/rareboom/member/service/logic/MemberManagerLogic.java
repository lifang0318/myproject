package com.rareboom.member.service.logic;

import java.util.Date;
import org.springframework.stereotype.Component;
import com.rareboom.member.dao.entity.MemberDetail;
import com.rareboom.member.dao.entity.MemberLicense;
import com.rareboom.member.dao.entity.MemberSummary;
import com.rareboom.member.dao.lang.MemberRegisterCommand;
import com.rareboom.member.dao.lang.ModifyMemberCommand;
import com.rareboom.member.dao.lang.ModifyMemberLicenseCommand;
import com.rareboom.member.scheme.ModifyMemberScheme;
import com.rareboom.member.scheme.MemberLicenseScheme;
import com.rareboom.member.scheme.ModifyMemberLicenseScheme;
import com.rareboom.member.scheme.RegisterScheme;
import com.rareboom.member.service.event.MemberManagerEvent;
import com.rareboom.member.service.event.MemberManagerEvent.Action;
import com.rareboom.member.util.sequence;

@Component
public class MemberManagerLogic extends BaseLogic {

	public void register(RegisterScheme scheme) throws Exception {
		try {

			MemberDetail detail = new MemberDetail();
			String memberId = sequence.nextId();
			detail.setMemberId(memberId);
			detail.setMemberAccount(scheme.getMemberAccount());
			detail.setLoginPassword(scheme.getLoginPassword());
			detail.setMemberStatus(scheme.getMemberStatus());
			detail.setStatusName(scheme.getStatusName());
			detail.setMemberType(scheme.getMemberType());
			detail.setTypeName(scheme.getTypeName());
			detail.setMemberPhone(scheme.getMemberPhone());
			detail.setRegisterDate(new Date());
			detail.setRegisterTime(new Date());
			detail.setRegisterLocation(scheme.getRegisterLocation());

			MemberSummary memberSummary = new MemberSummary();
			memberSummary.setMemberId(detail.getMemberId());
			memberSummary.setMemberAccount(detail.getMemberAccount());
			memberSummary.setLoginPassword(detail.getLoginPassword());
			memberSummary.setMemberStatus(detail.getMemberStatus());
			memberSummary.setStatusName(detail.getStatusName());
			memberSummary.setMemberType(detail.getMemberType());
			memberSummary.setTypeName(detail.getTypeName());
			memberSummary.setMemberPhone(detail.getMemberPhone());
			memberSummary.setLastLoginDate(new Date());
			memberSummary.setLastLoginTime(new Date());
			memberSummary.setRegisterLocation(detail.getRegisterLocation());
			memberSummary.setRegisterDate(detail.getRegisterDate());
			memberSummary.setRegisterTime(detail.getRegisterTime());
			memberSummary.setValidTime(new Date());

			MemberRegisterCommand command = new MemberRegisterCommand();
			command.setDetail(detail);
			command.setSummary(memberSummary);
			MemberManagerEvent event = new MemberManagerEvent(command);
			event.setAction(Action.REGISTER);
			sendEvent(event);

		} catch (Exception e) {
			throw e;
		}
	}

	public void modifyMember(ModifyMemberScheme scheme) throws Exception {
		try {

			MemberDetail detail = new MemberDetail();
			detail.setMemberId(scheme.getMemberId());
			detail.setMemberAccount(scheme.getMemberAccount());
			detail.setLoginPassword(scheme.getLoginPassword());
			detail.setMemberType(scheme.getMemberType());
			detail.setTypeName(scheme.getTypeName());
			detail.setMemberStatus(scheme.getMemberStatus());
			detail.setStatusName(scheme.getStatusName());
			detail.setHeadImage(scheme.getHeadImage());
			detail.setNickName(scheme.getNickName());
			detail.setMemberName(scheme.getMemberName());
			detail.setMemberPhone(scheme.getMemberPhone());
			detail.setMemberMail(scheme.getMemberMail());
			detail.setRegisterTime(scheme.getRegisterTime());
			detail.setRegisterDate(scheme.getRegisterDate());
			detail.setRegisterLocation(scheme.getRegisterLocation());
			detail.setMemberBirthday(scheme.getMemberBirthday());
			detail.setExtendProperty(scheme.getExtendProperty());
			detail.setMemberImage(scheme.getMemberImage());
			detail.setSex(scheme.getSex());
			detail.setAge(scheme.getAge());
			detail.setMemberChannel(scheme.getMemberChannel());
			detail.setChannelName(scheme.getChannelName());
			detail.setVestEnterpriseId(scheme.getVestEnterpriseId());
			detail.setVestEnterpriseName(scheme.getVestEnterpriseName());

			MemberSummary summary = new MemberSummary();
			summary.setMemberId(scheme.getMemberId());
			summary.setMemberAccount(scheme.getMemberAccount());
			summary.setLoginPassword(scheme.getLoginPassword());
			summary.setMemberType(scheme.getMemberType());
			summary.setTypeName(scheme.getTypeName());
			summary.setMemberStatus(scheme.getMemberStatus());
			summary.setStatusName(scheme.getStatusName());
			summary.setHeadImage(scheme.getHeadImage());
			summary.setNickName(scheme.getNickName());
			summary.setMemberName(scheme.getMemberName());
			summary.setMemberPhone(scheme.getMemberPhone());
			summary.setMemberMail(scheme.getMemberMail());
			summary.setRegisterTime(scheme.getRegisterTime());
			summary.setRegisterDate(scheme.getRegisterDate());
			summary.setRegisterLocation(scheme.getRegisterLocation());
			summary.setValidTime(scheme.getValidTime());
			ModifyMemberCommand command = new ModifyMemberCommand();
			command.setDetail(detail);
			command.setSummary(summary);
			MemberManagerEvent event = new MemberManagerEvent(command);
			event.setAction(Action.MODIFY_MEMBER);
			sendEvent(event);
		} catch (Exception e) {
			throw e;
		}
	}

	public void addMemberLicense(MemberLicenseScheme scheme) throws Exception {
		try {
			MemberLicense license = new MemberLicense();
			license.setMemberAccount(scheme.getMemberAccount());
			license.setMemberName(scheme.getMemberName());
			license.setEnterpriseId(scheme.getEnterpriseId());
			license.setEnterpriseName(scheme.getEnterpriseName());
			license.setCreateDate(new Date());
			license.setCreateTime(new Date());
			license.setLicenseStatus(scheme.getLicenseStatus());
			license.setStatusName(scheme.getStatusName());
			license.setValidTime(new Date());
			license.setToken(scheme.getToken());

			MemberManagerEvent event = new MemberManagerEvent(license);
			event.setAction(Action.ADD_MEMBERLICENSE);
			sendEvent(event);
		} catch (Exception e) {
			throw e;
		}
	}

	public void modifyMemberLicense(ModifyMemberLicenseScheme scheme) throws Exception {
		try {

			MemberLicense license = new MemberLicense();
			license.setMemberAccount(scheme.getMemberAccount());
			license.setEnterpriseId(scheme.getEnterpriseId());
			license.setEnterpriseName(scheme.getEnterpriseName());
			license.setLicenseStatus(scheme.getLicenseStatus());
			license.setStatusName(scheme.getStatusName());
			license.setValidTime(new Date());
			license.setToken(scheme.getToken());

			MemberDetail detail = new MemberDetail();
			detail.setMemberAccount(scheme.getMemberAccount());
			detail.setMemberStatus(scheme.getLicenseStatus());
			detail.setStatusName(scheme.getStatusName());

			MemberSummary summary = new MemberSummary();
			summary.setMemberAccount(scheme.getMemberAccount());
			summary.setValidTime(scheme.getValidTime());
			summary.setMemberStatus(scheme.getLicenseStatus());
			summary.setStatusName(scheme.getStatusName());

			ModifyMemberLicenseCommand command = new ModifyMemberLicenseCommand();
			command.setLicense(license);
			command.setDetail(detail);
			command.setSummary(summary);

			MemberManagerEvent event = new MemberManagerEvent(command);
			event.setAction(Action.MODIFY_MEMBERLICENSE);
			sendEvent(event);
		} catch (Exception e) {
			throw e;
		}

	}
}
