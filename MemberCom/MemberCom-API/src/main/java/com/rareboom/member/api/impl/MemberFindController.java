package com.rareboom.member.api.impl;

import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.rareboom.member.api.IMemberFindController;
import com.rareboom.member.api.lang.ActionStatus;
import com.rareboom.member.api.lang.GeneralRequest;
import com.rareboom.member.api.lang.GeneralResponse;
import com.rareboom.member.api.util.JSONUtil;
import com.rareboom.member.dao.entity.MemberDetail;
import com.rareboom.member.dao.entity.MemberLicense;
import com.rareboom.member.dao.entity.MemberSummary;
import com.rareboom.member.scheme.FindMemberDetailScheme;
import com.rareboom.member.scheme.FindMemberLicenseScheme;
import com.rareboom.member.scheme.FindMemberSummaryScheme;
import com.rareboom.member.service.IMemberFindService;

@Controller
public class MemberFindController implements IMemberFindController {

	private static Logger loger = LogManager.getLogger(MemberFindController.class);

	@Autowired
	private IMemberFindService memberFindService;

	@Override
	public String findMemberSummaryById(String jsonStr) {
		GeneralResponse response = new GeneralResponse();
		try {
			GeneralRequest request = JSONUtil.jSONToModel(jsonStr, GeneralRequest.class);
			FindMemberSummaryScheme scheme = JSONUtil.jSONToModel(request.getBody().toString(),
					FindMemberSummaryScheme.class);
			String id = scheme.getMemberId();
			MemberSummary summary = memberFindService.findMemberSummaryById(id);
			if (summary != null) {
				response.setBody(summary);
			} else {
				response.setMessage("未查询到相关记录");
			}
		} catch (Exception e) {
			response.setActionStatus(ActionStatus.REQUEST_FAIL);
			loger.error(e.getMessage(), e);
		}
		return JSONUtil.modelToJSON(response);
	}

	@Override
	public String findMemberSummaryList(String jsonStr) {
		GeneralResponse response = new GeneralResponse();
		try {
			GeneralRequest request = JSONUtil.jSONToModel(jsonStr, GeneralRequest.class);
			FindMemberSummaryScheme scheme = JSONUtil.jSONToModel(request.getBody().toString(),
					FindMemberSummaryScheme.class);
			List<MemberSummary> summaries = memberFindService.findMemberSummaryList(scheme);
			if (null !=summaries && summaries.size()>0) {
				response.setBody(summaries);
			} else {
				response.setMessage("未查询到相关记录");
			}
		} catch (Exception e) {
			response.setActionStatus(ActionStatus.REQUEST_FAIL);
			loger.error(e.getMessage(), e);
		}
		return JSONUtil.modelToJSON(response);
	}

	@Override
	public String findMemberDetailById(String jsonStr) {
		GeneralResponse response = new GeneralResponse();
		try {
			GeneralRequest request = JSONUtil.jSONToModel(jsonStr, GeneralRequest.class);
			FindMemberDetailScheme scheme = JSONUtil.jSONToModel(request.getBody().toString(),
					FindMemberDetailScheme.class);
			String id = scheme.getMemberId();
			MemberDetail detail = memberFindService.findMemberDetailById(id);
			if (detail != null) {
				response.setBody(detail);
			} else {
				response.setMessage("未查询到相关记录");
			}
		} catch (Exception e) {
			response.setActionStatus(ActionStatus.REQUEST_FAIL);
			loger.error(e.getMessage(), e);
		}
		return JSONUtil.modelToJSON(response);
	}

	@Override
	public String findMemberDetailList(String jsonStr) {
		GeneralResponse response = new GeneralResponse();
		try {
			GeneralRequest request = JSONUtil.jSONToModel(jsonStr, GeneralRequest.class);
			FindMemberDetailScheme scheme = JSONUtil.jSONToModel(request.getBody().toString(),
					FindMemberDetailScheme.class);
			List<MemberDetail> details = memberFindService.findMemberDetailList(scheme);
			if (null != details && details.size()>0) {
				response.setBody(details);
			} else {
				response.setMessage("未查询到相关记录");
			}
		} catch (Exception e) {
			response.setActionStatus(ActionStatus.REQUEST_FAIL);
			loger.error(e.getMessage(), e);
		}
		return JSONUtil.modelToJSON(response);
	}

	@Override
	public String findMemberLicenseByAccount(String jsonStr) {
		GeneralResponse response = new GeneralResponse();
		try {
			GeneralRequest request = JSONUtil.jSONToModel(jsonStr, GeneralRequest.class);
			FindMemberLicenseScheme scheme = JSONUtil.jSONToModel(request.getBody().toString(),
					FindMemberLicenseScheme.class);
			String account = scheme.getMemberAccount();
			MemberLicense license = memberFindService.findMemberLicenseByAccount(account);
			if (license != null) {
				response.setBody(license);
			} else {
				response.setMessage("未查询到相关记录");
			}
		} catch (Exception e) {
			response.setActionStatus(ActionStatus.REQUEST_FAIL);
			loger.error(e.getMessage(), e);
		}
		return JSONUtil.modelToJSON(response);
	}

	@Override
	public String findMemberLicenseList(String jsonStr) {
		GeneralResponse response = new GeneralResponse();
		try {
			GeneralRequest request = JSONUtil.jSONToModel(jsonStr, GeneralRequest.class);
			FindMemberLicenseScheme scheme = JSONUtil.jSONToModel(request.getBody().toString(),
					FindMemberLicenseScheme.class);
			List<MemberLicense> licenses = memberFindService.findMemberLicenseList(scheme);
			if (null != licenses && licenses.size()>0) {
				response.setBody(licenses);
			} else {
				response.setMessage("未查询到相关记录");
			}
		} catch (Exception e) {
			response.setActionStatus(ActionStatus.REQUEST_FAIL);
			loger.error(e.getMessage(), e);
		}
		return JSONUtil.modelToJSON(response);

	}

	@Override
	public String findMemberSummaryByAccount(String jsonStr) {
		GeneralResponse response = new GeneralResponse();
		try {
			GeneralRequest request = JSONUtil.jSONToModel(jsonStr, GeneralRequest.class);
			FindMemberSummaryScheme scheme = JSONUtil.jSONToModel(request.getBody().toString(),
					FindMemberSummaryScheme.class);
			List<MemberSummary> summaries = memberFindService.findMemberSummaryList(scheme);
			boolean flag = false;
			if (null != summaries && summaries.size() > 0) {
                flag = true;
                response.setBody(flag);
				return JSONUtil.modelToJSON(response);
			} else {
				response.setBody(flag);
				response.setMessage("此帐号未注册");
			}
		} catch (Exception e) {
			response.setActionStatus(ActionStatus.REQUEST_FAIL);
			loger.error(e.getMessage(), e);
		}
		return JSONUtil.modelToJSON(response);
	}
}
