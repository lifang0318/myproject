package com.rareboom.member.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.rareboom.member.dao.entity.MemberDetail;
import com.rareboom.member.dao.entity.MemberLicense;
import com.rareboom.member.dao.entity.MemberSummary;
import com.rareboom.member.scheme.FindMemberDetailScheme;
import com.rareboom.member.scheme.FindMemberLicenseScheme;
import com.rareboom.member.scheme.FindMemberSummaryScheme;
import com.rareboom.member.service.IMemberFindService;

@Component
public class StratedAddCacheListener implements ApplicationListener<ContextRefreshedEvent> {

	private final Logger log = Logger.getLogger(StratedAddCacheListener.class);

	@Autowired
	private RedisCacheUtil redisCache;
	@Autowired
	private IMemberFindService memberFindService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		System.out.println("开始缓存数据... ");
		try {
			FindMemberDetailScheme detailScheme = new FindMemberDetailScheme();
			List<MemberDetail> details = memberFindService.findMemberDetailList(detailScheme);
			List<String> memberPhones = new ArrayList<String>();
			for (int i = 0; i < details.size(); i++) {
				memberPhones.add(details.get(i).getMemberPhone());
			}
			redisCache.set("memberPhones", memberPhones);
			System.out.println(redisCache.get("memberPhones"));
			FindMemberLicenseScheme licenseScheme = new FindMemberLicenseScheme();
			List<MemberLicense> licenses = memberFindService.findMemberLicenseList(licenseScheme);
			redisCache.set("licenses", licenses);
			FindMemberSummaryScheme summaryScheme = new FindMemberSummaryScheme();
			List<MemberSummary> summaries = memberFindService.findMemberSummaryList(summaryScheme);
			redisCache.set("memberSummaries", summaries);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
