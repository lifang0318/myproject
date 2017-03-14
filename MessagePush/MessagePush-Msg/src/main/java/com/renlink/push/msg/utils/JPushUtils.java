package com.renlink.push.msg.utils;

import java.util.Collection;
import java.util.Map;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.Audience.Builder;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.WinphoneNotification;

public class JPushUtils {

	private static ThreadLocal<JPushClient> currentClient = new ThreadLocal<JPushClient>();

	public static JPushClient create(String masterSecret, String appKey) {

		JPushClient client = currentClient.get();
		if (client == null) {
			client = new JPushClient(masterSecret, appKey);
			currentClient.set(client);
		}
		return client;
	}


	public static Audience createAudience(Collection<String> tags,
			Collection<String> aliases) {

		Builder build = null;
		if (tags != null && tags.size() > 0) {
			build = Audience.newBuilder();
			build.addAudienceTarget(AudienceTarget.tag(tags));
		}

		if (aliases != null && aliases.size() > 0) {
			if (build != null) {
				build.addAudienceTarget(AudienceTarget.alias(aliases));
			} else {
				build = Audience.newBuilder();
				build.addAudienceTarget(AudienceTarget.alias(aliases));
			}
		}

		if (build != null) {
			return build.build();
		} else {
			return null;
		}
	}

	public static IosNotification createIosNote(Object alert, int badge,
			String sound, String category, boolean contentAvailable,
			Map<String, String> extras) {
		IosNotification.Builder builder = IosNotification.newBuilder();
		builder.setAlert(alert).setBadge(badge).setCategory(category)
				.setContentAvailable(contentAvailable).setSound(sound);

		if (extras != null && !extras.isEmpty()) {
			builder.addExtras(extras);
		}

		return builder.build();
	}

	public static IosNotification createIosNote(Object alert, String sound,
			Map<String, String> extras) {

		IosNotification.Builder builder = IosNotification.newBuilder();
		builder.setAlert(alert).setSound(sound);

		if (extras != null && !extras.isEmpty()) {
			builder.addExtras(extras);
		}
		return builder.build();
	}

	public static AndroidNotification createAndroidNote(Object alert,
			String title, int builderId, Map<String, String> extras) {
		AndroidNotification.Builder builder = AndroidNotification.newBuilder();
		builder.setAlert(alert).setTitle(title).setBuilderId(builderId);

		if (extras != null && !extras.isEmpty()) {
			builder.addExtras(extras);
		}
		return builder.build();
	}

	public static AndroidNotification createAndroidNote(Object alert,
			String title, Map<String, String> extras) {

		AndroidNotification.Builder builder = AndroidNotification.newBuilder();
		builder.setAlert(alert).setTitle(title);

		if (extras != null && !extras.isEmpty()) {
			builder.addExtras(extras);
		}
		return builder.build();
	}

	public static WinphoneNotification createWinphoneNote(Object alert,
			String title, String openPage, Map<String, String> extras) {

		WinphoneNotification.Builder builder = WinphoneNotification
				.newBuilder();
		builder.setAlert(alert).setTitle(title).setOpenPage(openPage);

		if (extras != null && !extras.isEmpty()) {
			builder.addExtras(extras);
		}
		return builder.build();

	}

	public static Message createMsg(String title, String content,
			String contentType, Map<String, String> extras) {

		Message.Builder builder = Message.newBuilder();
		builder.setTitle(title).setContentType(contentType)
				.setMsgContent(content);

		if (extras != null && !extras.isEmpty()) {
			builder.addExtras(extras);
		}
		return builder.build();
	}

	public static PushResult push(JPushClient client, PushPayload load)
			throws APIConnectionException, APIRequestException {
		return client.sendPush(load);
	}
	
	
	public static PushResult push(String masterSecret, String appKey,
			PushPayload load) throws APIConnectionException,
			APIRequestException {
		JPushClient jpush = create(masterSecret, appKey);
		return push(jpush, load);
	}


}
