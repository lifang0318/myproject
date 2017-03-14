package com.rareboom.member.api;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Test;
import com.alibaba.fastjson.JSON;
import com.caucho.hessian.client.HessianProxyFactory;
import com.rareboom.member.api.lang.GeneralRequest;
import com.rareboom.member.api.util.JSONUtil;
import com.rareboom.member.scheme.FindMemberSummaryScheme;
import com.rareboom.member.scheme.LoginScheme;
import com.rareboom.member.scheme.MemberLicenseScheme;
import com.rareboom.member.scheme.ModifyMemberLicenseScheme;
import com.rareboom.member.scheme.ModifyMemberScheme;
import com.rareboom.member.scheme.RegisterScheme;
import com.rareboom.member.scheme.UploadHeadImageScheme;

public class MemberApplicationTest {
	@Test
	public void login() throws MalformedURLException {
		String url = "http://localhost:8080/MemberCom-API/remote/loginService";
		HessianProxyFactory factory = new HessianProxyFactory();
		IMemberManagerController app = (IMemberManagerController) factory.create(IMemberManagerController.class, url);
		GeneralRequest request = new GeneralRequest();
		LoginScheme scheme = new LoginScheme();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = formatter.format(new Date(1476165195478L));
		System.out.println(dateStr);
		scheme.setLoginPassword("abcd123456");
		scheme.setMemberAccount("18180107511");
		request.setBody(scheme);
		System.out.println(JSON.toJSONString(request));
		String result = app.loginMember(JSON.toJSONString(request));
		System.out.println(result);
	}

	@Test
	public void registerMember() throws MalformedURLException {
		String url = "http://localhost:8080/MemberCom-API/remote/registerService";
		HessianProxyFactory factory = new HessianProxyFactory();
		IMemberManagerController app = (IMemberManagerController) factory.create(IMemberManagerController.class, url);
		GeneralRequest request = new GeneralRequest();
		RegisterScheme scheme = new RegisterScheme();
		// scheme.setMemberAccount("");
		scheme.setMemberAccount("18180107511");
		scheme.setLoginPassword("abcd123456");
		// scheme.setRegisterLocation("成都");
		/*
		 * scheme.setMemberPhone("18780107573"); scheme.setMemberName("小王");
		 * scheme.setMemberMail("12345@atguigu.com");
		 */
		request.setBody(scheme);
		System.out.println(JSON.toJSONString(request));
		String result = app.registerMember(JSON.toJSONString(request));
		System.out.println(result);
	}

	@Test
	public void find() throws MalformedURLException {
		String url = "http://localhost:8080/MemberCom-API/remote/findService";
		HessianProxyFactory factory = new HessianProxyFactory();
		IMemberFindController app = (IMemberFindController) factory.create(IMemberFindController.class, url);
		GeneralRequest request = new GeneralRequest();
		/*
		 * FindMemberSummaryScheme scheme = new FindMemberSummaryScheme();
		 * scheme.setMemberMail("155331979@qq.com"); request.setBody(scheme);
		 * System.out.println(JSON.toJSONString(request)); String result =
		 * app.findMemberSummaryList(JSON.toJSONString(request));
		 * System.out.println(result);
		 */
		FindMemberSummaryScheme scheme1 = new FindMemberSummaryScheme();
		scheme1.setMemberAccount("18200390358");
		request.setBody(scheme1);
		System.out.println(JSONUtil.modelToJSON(request));
		String result1 = app.findMemberSummaryList(JSONUtil.modelToJSON(request));
		System.out.println(result1);
	}

	@Test
	public void modifyMember() throws MalformedURLException {
		String url = "http://localhost:8080/MemberCom-API/remote/updateMemberService";
		HessianProxyFactory factory = new HessianProxyFactory();
		IMemberManagerController app = (IMemberManagerController) factory.create(IMemberManagerController.class, url);
		GeneralRequest request = new GeneralRequest();
		ModifyMemberScheme scheme = new ModifyMemberScheme();
		scheme.setMemberAccount("13882019773");
		scheme.setLoginPassword("abcd123456");
		request.setBody(scheme);
		System.out.println(JSON.toJSONString(request));
		String result = app.modifyMember(JSON.toJSONString(request));
		System.out.println(result);
	}

	@Test
	public void addMemberLicense() throws MalformedURLException {
		String url = "http://localhost:8080/MemberCom-API/remote/addMemberLicenseService";
		HessianProxyFactory factory = new HessianProxyFactory();
		IMemberManagerController app = (IMemberManagerController) factory.create(IMemberManagerController.class, url);
		GeneralRequest request = new GeneralRequest();
		MemberLicenseScheme scheme = new MemberLicenseScheme();
		scheme.setMemberAccount("18180107511");
		scheme.setMemberName("张三");
		scheme.setEnterpriseId("2");
		scheme.setEnterpriseName("佳禧");
		scheme.setLicenseStatus("A");
		scheme.setStatusName("有效");
		scheme.setToken("abc");
		request.setBody(scheme);
		System.out.println(JSON.toJSONString(request));
		String result = app.addMemberLicense(JSON.toJSONString(request));
		System.out.println(result);
	}

	@Test
	public void modifyMemberLicense() throws MalformedURLException {
		String url = "http://localhost:8080/MemberCom-API/remote/updateMemberLicenseService";
		HessianProxyFactory factory = new HessianProxyFactory();
		IMemberManagerController app = (IMemberManagerController) factory.create(IMemberManagerController.class, url);
		GeneralRequest request = new GeneralRequest();
		ModifyMemberLicenseScheme scheme = new ModifyMemberLicenseScheme();
		scheme.setMemberAccount("18180107578");
		scheme.setValidTime(new Date());
		request.setBody(scheme);
		System.out.println(JSON.toJSONString(request));
		String result = app.modifyMemberLicense(JSON.toJSONString(request));
		System.out.println(result);
	}

	@Test
	public void uploadHeadImage() throws MalformedURLException, FileNotFoundException {
		String url = "http://localhost:8080/MemberCom-API/remote/uploadHeadImageService";
		HessianProxyFactory factory = new HessianProxyFactory();
		IMemberManagerController app = (IMemberManagerController) factory.create(IMemberManagerController.class, url);
		GeneralRequest request = new GeneralRequest();
		// String path =
		// "C:\\Users\\Administrator\\Desktop\\手机照片\\IMG_20150809_155247_HDR.jpg";

		// InputStream data = new BufferedInputStream(new
		// FileInputStream(path));
		// 调用文件上传服务
		String imageInfo = "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";
		UploadHeadImageScheme scheme = new UploadHeadImageScheme();
		scheme.setMemberId("1476432960872000");
		scheme.setBase64(imageInfo);
		request.setBody(scheme);
		System.out.println(JSON.toJSONString(request));
		String result = app.uploadHeadImage(JSON.toJSONString(request));
		System.out.println(result);
	}
}
