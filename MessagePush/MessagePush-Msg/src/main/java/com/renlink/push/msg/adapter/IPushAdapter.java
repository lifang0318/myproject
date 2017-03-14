package com.renlink.push.msg.adapter;

import com.renlink.push.msg.scheme.MessageScheme;
import com.renlink.push.msg.scheme.MessageSchemeCollection;
import com.renlink.push.msg.scheme.NotificationScheme;
import com.renlink.push.msg.scheme.NotificationSchemeCollection;
import com.renlink.push.msg.scheme.SynthetizeScheme;

public interface IPushAdapter {
		
	public boolean pushNotification(NotificationScheme scheme) throws Exception;
	
	public boolean batchPushNotification(NotificationSchemeCollection schemes) throws Exception;
	
	public boolean pushMessage(MessageScheme scheme) throws Exception;
	
	public boolean batchPushMessage(MessageSchemeCollection schemes) throws Exception;
	
	public boolean push(SynthetizeScheme scheme) throws Exception;

}
