package com.rareboom.member.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.jms.Destination;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.rareboom.member.dao.entity.MemberDetail;
import com.rareboom.member.dao.entity.MemberSummary;
import com.rareboom.member.scheme.LoginScheme;
import com.rareboom.member.scheme.MemberLicenseScheme;
import com.rareboom.member.scheme.ModifyMemberLicenseScheme;
import com.rareboom.member.scheme.ModifyMemberScheme;
import com.rareboom.member.scheme.RegisterScheme;
import com.rareboom.member.scheme.UploadHeadImageScheme;
import com.rareboom.member.service.IMemberManagerService;
import com.rareboom.member.service.adapter.MemberSummaryDaoAdapter;
import com.rareboom.member.service.lang.ActionCommand;
import com.rareboom.member.service.lang.ActionCommand.Action;
import com.rareboom.member.util.ImageUtil;

@Service("memberManagerService")
public class MemberManagerService extends BaseManager implements IMemberManagerService {

	private static Logger log = LoggerFactory.getLogger(MemberManagerService.class);

	@Autowired
	private MemberSummaryDaoAdapter memberSummaryDaoAdapter;
	@Autowired
	private ValidateService validateService;
	@Autowired
	@Qualifier("memberManagerQueue")
	private Destination destination;

	@Override
	public void register(RegisterScheme scheme) throws Exception {
		log.trace("MemberManager Service Register Member");
		try {
			ActionCommand command = new ActionCommand();
			command.setBody(scheme);
			command.setAction(Action.REGISTER);
			sendMessage(destination, command);
		} catch (Exception e) {
			throw e;
		}
	}

	// @Cacheable(value = "memberSummaryCache", key = "#scheme.memberAccount")
	public MemberSummary login(@Valid LoginScheme scheme) throws Exception {
		try {
			MemberSummary summary = new MemberSummary();
			summary.setMemberAccount(scheme.getMemberAccount());
			summary.setMemberType(scheme.getMemberType());
			summary.setTypeName(scheme.getTypeName());
			summary.setLastLoginDate(new Date());
			summary.setLastLoginTime(new Date());
			memberSummaryDaoAdapter.update(summary);
			MemberDetail detail = new MemberDetail();
			return summary;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void modifyMember(ModifyMemberScheme scheme) throws Exception {
		try {
			validateService.validate(scheme);
			ActionCommand command = new ActionCommand();
			command.setBody(scheme);
			command.setAction(Action.MODIFY_MEMBER);
			sendMessage(destination, command);//
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void modifyMemberLicense(@Valid ModifyMemberLicenseScheme scheme) throws Exception {
		try {
			ActionCommand command = new ActionCommand();
			command.setBody(scheme);
			command.setAction(Action.MODIFY_MEMBERLICENSE);
			sendMessage(destination, command);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void addMemberLicense(MemberLicenseScheme scheme) throws Exception {
		try {
			ActionCommand command = new ActionCommand();
			command.setBody(scheme);
			command.setAction(Action.ADD_MEMBERLICENSE);
			sendMessage(destination, command);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public String uploadHeadImage(UploadHeadImageScheme scheme) throws Exception {
		InputStream in = null;
		FileOutputStream fos = null;
		String url = null;
		try {
			validateService.validate(scheme);
			String memberId = scheme.getMemberId();
			String base64 = scheme.getBase64();
			byte[] imgByte = ImageUtil.base64byte2(base64);
			in = new ByteArrayInputStream(imgByte);
			String path = getClass().getResource("/").getPath();
			path = path.substring(1, path.indexOf("classes"));
			Properties prop = new Properties();
			prop.load(new FileInputStream(path + "config/headImageUpload.properties"));
			String storePath = prop.getProperty("storePath");
			String realPath = storePath + memberId + prop.getProperty("extName");
			System.out.println(realPath);
			url = prop.getProperty("baseUrl") + memberId + prop.getProperty("extName");// 图片访问地址
			File file = new File(storePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			fos = new FileOutputStream(new File(realPath));
			byte[] b = new byte[1024];
			int len = 0;
			while ((len = in.read(b)) != -1) {
				fos.write(b, 0, len);
			}
			ModifyMemberScheme modifyMemberScheme = new ModifyMemberScheme();
			modifyMemberScheme.setMemberId(scheme.getMemberId());
			modifyMemberScheme.setHeadImage(url);
			ActionCommand command = new ActionCommand();
			command.setBody(modifyMemberScheme);
			command.setAction(Action.MODIFY_MEMBER);
			sendMessage(destination, command);
		} catch (Exception e) {
			throw e;
		} finally {
			if (fos != null) {
				fos.flush();
				fos.close();
			}
			if (in != null) {
				in.close();
			}
		}
		return url;
	}
}
