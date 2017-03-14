package com.rareboom.member.api.impl;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.rareboom.member.api.IMemberManagerController;
import com.rareboom.member.api.RedisCacheUtil;
import com.rareboom.member.api.lang.ActionStatus;
import com.rareboom.member.api.lang.GeneralRequest;
import com.rareboom.member.api.lang.GeneralResponse;
import com.rareboom.member.api.util.JSONUtil;
import com.rareboom.member.api.util.ValidMobile;
import com.rareboom.member.api.util.ValidPassword;
import com.rareboom.member.dao.entity.MemberLicense;
import com.rareboom.member.dao.entity.MemberSummary;
import com.rareboom.member.dao.type.MemberStatus;
import com.rareboom.member.dao.type.MemberType;
import com.rareboom.member.scheme.FindMemberSummaryScheme;
import com.rareboom.member.scheme.LoginScheme;
import com.rareboom.member.scheme.MemberLicenseScheme;
import com.rareboom.member.scheme.ModifyMemberLicenseScheme;
import com.rareboom.member.scheme.ModifyMemberScheme;
import com.rareboom.member.scheme.RegisterScheme;
import com.rareboom.member.scheme.UploadHeadImageScheme;
import com.rareboom.member.service.IMemberFindService;
import com.rareboom.member.service.IMemberManagerService;
import com.rareboom.member.service.lang.ParameterException;

@Controller("memberManagerController")
public class MemberManagerController implements IMemberManagerController {
	private static Logger loger = LogManager.getLogger(MemberManagerController.class);

	@Resource
	private IMemberManagerService memberManagerService;

	@Resource
	private IMemberFindService memberFindService;

	@Autowired
	private RedisCacheUtil redisCache;

	@Override
	public String registerMember(String jsonStr) {
		GeneralResponse response = new GeneralResponse();
		try {
			GeneralRequest request = JSONUtil.jSONToModel(jsonStr, GeneralRequest.class);
			if (null == request.getBody() && "".equals(request.getBody())) {
				response.setActionStatus(ActionStatus.ACTION_FAIL);
				response.setMessage("请输入注册账号和密码");
				return JSONUtil.modelToJSON(response);
			}
			RegisterScheme scheme = JSONUtil.jSONToModel(request.getBody().toString(), RegisterScheme.class);
			// 验证入参
			// 1.验证帐号是否为手机号格式
			if (null == scheme.getMemberAccount() || !ValidMobile.isMobileNO(scheme.getMemberAccount())) {
				response.setActionStatus(ActionStatus.ACTION_FAIL);
				response.setMessage("帐号需为手机号格式");
				return JSONUtil.modelToJSON(response);
			}

			// 2.验证密码是否为空
			if (null == scheme.getLoginPassword() || "".equals(scheme.getLoginPassword())) {
				response.setActionStatus(ActionStatus.ACTION_FAIL);
				response.setMessage("密码不能为空");
				return JSONUtil.modelToJSON(response);
			}
			// 3.验证密码位数
			if (!ValidPassword.isValidPassword(scheme.getLoginPassword())) {
				response.setActionStatus(ActionStatus.ACTION_FAIL);
				response.setMessage("密码格式有误");
				return JSONUtil.modelToJSON(response);
			}
			// 4.验证该手机号是否已被注册 ......
			List<String> memberPhones = (List<String>) redisCache.get("memberPhones");
			if (null != memberPhones && memberPhones.contains(scheme.getMemberAccount())) {
				response.setActionStatus(ActionStatus.ACTION_FAIL);
				response.setMessage("该手机号已被注册");
				return JSONUtil.modelToJSON(response);
			}

			// 5.验证是否存在对应的授权记录 .....
			List<MemberLicense> licenses = (List<MemberLicense>) redisCache.get("licenses");
			MemberLicense memberLicense = null;
			for (int i = 0; i < licenses.size(); i++) {
				if (licenses.get(i).getMemberAccount().equals(scheme.getMemberAccount())) {
					memberLicense = licenses.get(i);
					break;
				}
			}
			// 5.1 若存在授权记录
			if (memberLicense != null) {
				// 6.验证许可是否在有效期内
				Date validTime = memberLicense.getValidTime();
				Date currentDate = new Date();
				// 若许可不在有效期内
				if (currentDate.getTime() > validTime.getTime()) {
					scheme.setMemberStatus(MemberStatus.ACTIVATION.getTag());
					scheme.setStatusName(MemberStatus.ACTIVATION.getTagName());
					scheme.setMemberType(MemberType.OVERVALIDDATE.getTag());
					scheme.setTypeName(MemberType.OVERVALIDDATE.getTagName());
					scheme.setMemberPhone(scheme.getMemberAccount());
					memberManagerService.register(scheme);
					redisCache.update("memberPhones", scheme.getMemberPhone());
					response.setMessage("注册成功，请及时续费");
					return JSONUtil.modelToJSON(response);
				}
				// 6.2 若许可在有效期内
				scheme.setMemberStatus(MemberStatus.ACTIVATION.getTag());
				scheme.setStatusName(MemberStatus.ACTIVATION.getTagName());
				scheme.setMemberType(MemberType.COMMON.getTag());
				scheme.setTypeName(MemberType.COMMON.getTagName());
				scheme.setMemberPhone(scheme.getMemberAccount());
				memberManagerService.register(scheme);
				memberPhones.add(scheme.getMemberPhone());
				redisCache.update("memberPhones", scheme.getMemberPhone());
				response.setMessage("注册成功,状态已激活");
				return JSONUtil.modelToJSON(response);
			} else {
				// 6.3 若不存在授权记录
				scheme.setMemberStatus(MemberStatus.ACTIVATION.getTag());
				scheme.setStatusName(MemberStatus.UNACTIVATION.getTagName());
				scheme.setMemberType(MemberType.UNAUTHORIZED.getTag());
				scheme.setTypeName(MemberType.UNAUTHORIZED.getTagName());
				scheme.setMemberPhone(scheme.getMemberAccount());
				memberManagerService.register(scheme);
				// 更新缓存
				redisCache.update("memberPhones", scheme.getMemberPhone());
				response.setMessage("注册成功,状态待激活");
				return JSONUtil.modelToJSON(response);
			}

		} catch (Exception e) {
			response.setActionStatus(ActionStatus.REQUEST_FAIL);
			loger.error(e.getMessage(), e);
		}
		return JSONUtil.modelToJSON(response);
	}

	@Override
	public String loginMember(String jsonStr) {
		GeneralResponse response = new GeneralResponse();
		try {
			GeneralRequest request = JSONUtil.jSONToModel(jsonStr, GeneralRequest.class);
			if (null == request.getBody() && "".equals(request.getBody())) {
				response.setActionStatus(ActionStatus.ACTION_FAIL);
				response.setMessage("请输入账号和密码");
				return JSONUtil.modelToJSON(response);
			}
			LoginScheme scheme = JSONUtil.jSONToModel(request.getBody().toString(), LoginScheme.class);
			FindMemberSummaryScheme findMemberSummaryScheme = new FindMemberSummaryScheme();
			findMemberSummaryScheme.setMemberAccount(scheme.getMemberAccount());
			findMemberSummaryScheme.setPassword(scheme.getLoginPassword());
			// 验证用户名和密码是否正确
			List<MemberSummary> summaries = memberFindService.findMemberSummaryList(findMemberSummaryScheme);

			if (null != summaries && summaries.size() > 0) {
				MemberSummary summary = summaries.get(0);
				Date validDate = summary.getValidTime();
				Date currentDate = new Date();
				summary.setLastLoginDate(currentDate);
				summary.setLastLoginTime(currentDate);
				// 验证是否在有效期内
				if (currentDate.getTime() > validDate.getTime()) {
					scheme.setMemberType(MemberType.OVERVALIDDATE.getTag());
					scheme.setTypeName(MemberType.OVERVALIDDATE.getTagName());
					memberManagerService.login(scheme);
					summary.setMemberType(scheme.getMemberType());
					summary.setTypeName(scheme.getTypeName());
					response.setBody(summary);
					response.setMessage("登录成功，已超有效期，请尽快续费");
					return JSONUtil.modelToJSON(response);
				}
				memberManagerService.login(scheme);
				response.setBody(summary);
				response.setMessage("登录成功");
			} else {
				response.setActionStatus(ActionStatus.ACTION_FAIL);
				response.setMessage("用户名或密码错误");
			}
		} catch (Exception e) {
			response.setActionStatus(ActionStatus.REQUEST_FAIL);
			loger.error(e.getMessage(), e);
		}
		return JSONUtil.modelToJSON(response);
	}

	@Override
	public String modifyMember(String jsonStr) {
		GeneralResponse response = new GeneralResponse();
		try {
			GeneralRequest request = JSONUtil.jSONToModel(jsonStr, GeneralRequest.class);
			ModifyMemberScheme scheme = JSONUtil.jSONToModel(request.getBody().toString(), ModifyMemberScheme.class);
			memberManagerService.modifyMember(scheme);
			response.setMessage("操作成功");
		} catch (ParameterException e) {
			response.setActionStatus(ActionStatus.ACTION_FAIL);
			response.setMessage(e.getMessage());
		} catch (Exception e) {
			response.setActionStatus(ActionStatus.REQUEST_FAIL);
			loger.error(e.getMessage(), e);
		}
		return JSONUtil.modelToJSON(response);
	}

	@Override
	public String addMemberLicense(String jsonStr) {
		GeneralResponse response = new GeneralResponse();
		try {
			GeneralRequest request = JSONUtil.jSONToModel(jsonStr, GeneralRequest.class);
			MemberLicenseScheme scheme = JSONUtil.jSONToModel(request.getBody().toString(), MemberLicenseScheme.class);
			memberManagerService.addMemberLicense(scheme);
			MemberLicense license = JSONUtil.jSONToModel(request.getBody().toString(), MemberLicense.class);
			license.setValidTime(new Date());
			redisCache.update("licenses", license);
			response.setMessage("操作成功");
		} catch (Exception e) {
			response.setActionStatus(ActionStatus.REQUEST_FAIL);
			loger.error(e.getMessage(), e);
		}
		return JSONUtil.modelToJSON(response);

	}

	@Override
	public String modifyMemberLicense(String jsonStr) {
		GeneralResponse response = new GeneralResponse();
		try {
			GeneralRequest request = JSONUtil.jSONToModel(jsonStr, GeneralRequest.class);
			ModifyMemberLicenseScheme scheme = JSONUtil.jSONToModel(request.getBody().toString(),
					ModifyMemberLicenseScheme.class);
			memberManagerService.modifyMemberLicense(scheme);
			response.setMessage("操作成功");
		} catch (Exception e) {
			response.setActionStatus(ActionStatus.REQUEST_FAIL);
			loger.error(e.getMessage(), e);
		}
		return JSONUtil.modelToJSON(response);
	}

	@Override
	public String uploadHeadImage(String jsonStr) {
		GeneralResponse response = new GeneralResponse();
		try {
			GeneralRequest request = JSONUtil.jSONToModel(jsonStr, GeneralRequest.class);
			UploadHeadImageScheme scheme = JSONUtil.jSONToModel(request.getBody().toString(),
					UploadHeadImageScheme.class);
			String url = memberManagerService.uploadHeadImage(scheme);
			response.setBody(url);
			response.setMessage("头像上传成功");
		} catch (ParameterException e) {
			response.setActionStatus(ActionStatus.ACTION_FAIL);
			response.setMessage(e.getMessage());
		} catch (Exception e) {
			response.setActionStatus(ActionStatus.REQUEST_FAIL);
			loger.error(e.getMessage(), e);
		}
		return JSONUtil.modelToJSON(response);
	}
}
