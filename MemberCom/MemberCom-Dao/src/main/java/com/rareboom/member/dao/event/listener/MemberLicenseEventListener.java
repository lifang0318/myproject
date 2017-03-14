package com.rareboom.member.dao.event.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rareboom.member.dao.IMemberDetailDao;
import com.rareboom.member.dao.IMemberLicenseDao;
import com.rareboom.member.dao.IMemberSummaryDao;
import com.rareboom.member.dao.entity.MemberDetail;
import com.rareboom.member.dao.entity.MemberLicense;
import com.rareboom.member.dao.entity.MemberSummary;
import com.rareboom.member.dao.event.MemberLicenseEvent;
import com.rareboom.member.dao.lang.ModifyMemberLicenseCommand;

@Component
public class MemberLicenseEventListener implements ApplicationListener<MemberLicenseEvent> {
	private static Logger log = LoggerFactory.getLogger(MemberLicenseEventListener.class);

	@Autowired
	private IMemberLicenseDao memberLicenseDao;

	@Autowired
	private IMemberDetailDao memberDetailDao;
	
	@Autowired
	private IMemberSummaryDao memberSummaryDao;
	
	public void onApplicationEvent(MemberLicenseEvent event) {
		MemberLicenseEvent.Action action = event.getAction();
		switch (action) {
		case ADD:
			MemberLicense addLicense = (MemberLicense) event.getSource();
			add(addLicense);
			break;
		case UPDATE:
			ModifyMemberLicenseCommand modifyLicenseCommand= (ModifyMemberLicenseCommand) event.getSource();
			update(modifyLicenseCommand);
			break;
		default:
			break;
		}
	}

	private void add(MemberLicense License) {
		try {
			memberLicenseDao.insert(License);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	private void update(ModifyMemberLicenseCommand command) {
		try {
			MemberLicense license=command.getLicense();
			MemberDetail detail=command.getDetail();
			MemberSummary summary=command.getSummary();
			memberLicenseDao.update(license);
			memberDetailDao.update(detail);
			memberSummaryDao.update(summary);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
