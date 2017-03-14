package com.renlink.push.msg.adapter;

import com.renlink.push.msg.lang.JPushProperty;
import com.renlink.push.msg.scheme.MessageScheme;
import com.renlink.push.msg.scheme.MessageSchemeCollection;
import com.renlink.push.msg.scheme.NotificationScheme;
import com.renlink.push.msg.scheme.NotificationSchemeCollection;
import com.renlink.push.msg.scheme.SynthetizeScheme;
import com.renlink.push.msg.utils.JPushUtils;

import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;

public class JPushAdapter implements IPushAdapter {

	private String masterSecret;
	private String appKey;


	public String getMasterSecret() {
		return masterSecret;
	}

	public void setMasterSecret(String masterSecret) {
		this.masterSecret = masterSecret;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	private boolean push(JPushProperty property) throws Exception {
		
		PushPayload load = PushPayload.newBuilder()
				.setPlatform(property.getPlatform())
				.setAudience(property.getAudience())
				.setNotification(property.getNotification())
				.setMessage(property.getMessage()).build();
		
//		 PushPayload.newBuilder()
//	                .setPlatform(Platform.android_ios())
//	                .setAudience(Audience.newBuilder()
//	                        .addAudienceTarget(AudienceTarget.tag("tag1", "tag2"))
//	                        .addAudienceTarget(AudienceTarget.alias("alias1", "alias2"))
//	                        .build())
//	                .setMessage(Message.newBuilder()
//	                        .setMsgContent(MSG_CONTENT)
//	                        .addExtra("from", "JPush")
//	                        .build())
//	                .build();
		
//		System.out.println(load.toString());
		try {
			
			PushResult result = JPushUtils.push(masterSecret, appKey,load);
			return result.isResultOK();
		} catch (Exception e) {
			throw e;
		}
	}
	
	
//	private boolean batchPush(List<JPushProperty> propertys) throws Exception{
//		
//		
//		
//	}

	@Override
	public boolean pushNotification(NotificationScheme scheme) throws Exception {

		if (scheme == null) {
			throw new NullPointerException("Notification scheme be nll");
		}

		JPushProperty property = JPushProperty.newBuilder().parseScheme(scheme)
				.build();

		return push(property);
	}

	@Override
	public boolean pushMessage(MessageScheme scheme) throws Exception {
		if (scheme == null) {
			throw new NullPointerException("Message scheme be nll");
		}

		JPushProperty property = JPushProperty.newBuilder().parseScheme(scheme)
				.build();
		return push(property);
	}

	@Override
	public boolean push(SynthetizeScheme scheme) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean batchPushNotification(NotificationSchemeCollection schemes)
			throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean batchPushMessage(MessageSchemeCollection schemes)
			throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	

}
