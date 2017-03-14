package com.renlink.push.msg;

import com.renlink.push.msg.scheme.MessageScheme;
import com.renlink.push.msg.scheme.MessageSchemeCollection;
import com.renlink.push.msg.scheme.NotificationScheme;
import com.renlink.push.msg.scheme.NotificationSchemeCollection;
import com.renlink.push.msg.scheme.SynthetizeScheme;

public interface IController {

	public void pushMsg(MessageScheme scheme) throws Exception;

	public void batchPushMsg(MessageSchemeCollection collection)
			throws Exception;

	public void pushNotification(NotificationScheme scheme) throws Exception;

	public void batchPushNotification(NotificationSchemeCollection collection)
			throws Exception;

	public void push(SynthetizeScheme scheme) throws Exception;
	
	public void pushMsgAndroid(MessageScheme scheme) throws Exception;

}
