package com.renlink.push.msg.lang;

import java.util.Map;

import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

import com.renlink.push.msg.scheme.AndroidNotificationScheme;
import com.renlink.push.msg.scheme.PushScheme;
import com.renlink.push.msg.scheme.IosNotificationScheme;
import com.renlink.push.msg.scheme.MessageScheme;
import com.renlink.push.msg.scheme.NotificationScheme;
import com.renlink.push.msg.scheme.WinPhoneNotificationScheme;
import com.renlink.push.msg.utils.JPushUtils;

public class JPushProperty {

	private Platform platform;
	private Message message;
	private Notification notification;
	private Audience audience;

	public JPushProperty(Platform platform, Audience audience, Message msg,
			Notification note) {
		this.platform = platform;
		this.message = msg;
		this.audience = audience;
		this.notification = note;
	}

	public Platform getPlatform() {
		return platform;
	}

	public Message getMessage() {
		return message;
	}

	public Notification getNotification() {
		return notification;
	}

	public Audience getAudience() {
		return audience;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static class Builder {

		private Platform platform;
		private Message message = null;
		private Notification notification = null;
		private Audience audience = null;

		public Builder setPlatform(Platform platform) {
			this.platform = platform;
			return this;
		}

		public Builder setMessage(Message message) {
			this.message = message;
			return this;
		}

		public Builder setNotification(Notification notification) {
			this.notification = notification;
			return this;
		}

		public Builder setAudience(Audience audience) {
			this.audience = audience;
			return this;
		}

		public Builder parseScheme(PushScheme scheme) {

			if (scheme == null) {
				return null;
			}

			buildPlatform(scheme.getPlatform());
			buildAudience(scheme);
			buildNotification(scheme);
			buildMessage(scheme);
			return this;
		}

		public JPushProperty build() {
			return new JPushProperty(platform, audience, message, notification);
		}

		private void buildPlatform(PlatformType type) {

			switch (type) {
			case ALL:
				this.platform = Platform.all();
				break;
			case IOS:
				this.platform = Platform.ios();
				break;
			case ANDROID:
				this.platform = Platform.android();
				break;
			case WINPHONE:
				this.platform = Platform.winphone();
				break;
			case IOS_ANDROID:
				this.platform = Platform.android_ios();
				break;
			default:
				break;
			}
		}

		private void buildAudience(PushScheme scheme) {
			this.audience = JPushUtils.createAudience(scheme.getTags(),
					scheme.getAlias());
		}

		private void buildNotification(PushScheme scheme) {

			String title = null;
			Object alert = null;
			Map<String, String> extras = null;

			if (scheme instanceof NotificationScheme) {
				alert = ((NotificationScheme) scheme).getAlert();
				this.notification = Notification.alert(alert);
			}

			if (scheme instanceof AndroidNotificationScheme) {

				alert = ((AndroidNotificationScheme) scheme).getAlert();
				title = scheme.getTitle();
				extras = scheme.getExtras();
				Integer builderId = ((AndroidNotificationScheme) scheme)
						.getBuilderId();

				this.notification = Notification
						.newBuilder()
						.addPlatformNotification(
								JPushUtils.createAndroidNote(alert, title,
										builderId, extras)).build();
			}

			if (scheme instanceof IosNotificationScheme) {
				alert = ((IosNotificationScheme) scheme).getAlert();
				title = scheme.getTitle();
				extras = scheme.getExtras();
				Integer badge = ((IosNotificationScheme) scheme).getBadge();
				String sound = ((IosNotificationScheme) scheme).getSound();
				String category = ((IosNotificationScheme) scheme)
						.getCategory();
				boolean contentAvailable = ((IosNotificationScheme) scheme)
						.isContentAvailable();

				this.notification = Notification
						.newBuilder()
						.addPlatformNotification(
								JPushUtils.createIosNote(alert, badge, sound,
										category, contentAvailable, extras))
						.build();
			}

			if (scheme instanceof WinPhoneNotificationScheme) {
				alert = ((WinPhoneNotificationScheme) scheme).getAlert();
				title = scheme.getTitle();
				extras = scheme.getExtras();
				String openPage = ((WinPhoneNotificationScheme) scheme)
						.getOpenPage();

				this.notification = Notification
						.newBuilder()
						.addPlatformNotification(
								JPushUtils.createWinphoneNote(alert, title,
										openPage, extras)).build();
			}
		}

		private void buildMessage(PushScheme scheme) {

			if (scheme instanceof MessageScheme) {

				String content = ((MessageScheme) scheme).getMsgContent();
				String title = scheme.getTitle();
				String contentType = ((MessageScheme) scheme).getMsgType();
				Map<String, String> extras = scheme.getExtras();

				this.message = JPushUtils.createMsg(title, content,
						contentType, extras);
			}
		}

	}
}
