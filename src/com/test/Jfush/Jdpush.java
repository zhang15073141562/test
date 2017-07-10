package com.test.Jfush;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.util.JPushUtils;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import net.sf.json.JSONObject;

/**
 * 极光推送
 * 
 * @author zhangxiongjie
 *
 */
public class Jdpush {
	protected static final Logger LOG = LoggerFactory.getLogger(Jdpush.class);

	// 在资源jpush api中定义的演示应用程序。

	public static final String TITLE = "生活科技";
	public static final String ALERT = "祝大家元旦快乐";
	public static final String MSG_CONTENT = "生活科技祝新老客户元旦快乐";
	public static final String REGISTRATION_ID = "0900e8d85ef";
	public static final String TAG = "tag_api";

	public static JPushClient jpushClient = null;

	@SuppressWarnings("deprecation")
	public static void testSendPush(String appKey, String masterSecret) {

		jpushClient = new JPushClient(masterSecret, appKey, 3);
		
		//JPushUtils jPushUtils=new JPushUtils(masterSecret,appKey,appKey);

		// 可以使用这个https代理:https://github.com/exa-network/exaproxy
		// 对于push，您所需要做的就是构建push载荷对象。
		// 生成推送的内容，这里我们先测试全部推送
		String title="极光推送";
		String content="第一次运行极光推送";
		JSONObject jsonObjectPayload=new JSONObject();
		jsonObjectPayload.put("name", "小米");
		jsonObjectPayload.put("age", "19");
		PushPayload payload = buildPushObject_android_tag_alertWithTitle(title,content,jsonObjectPayload);

		try {
			System.out.println(payload.toString());
			PushResult result = jpushClient.sendPush(payload);
			//PushResult result =jPushUtils.sendBroadcastAll(title, content, jsonObjectPayload, JPushUtils.TYPE_ANDROID);
			System.out.println(result + "................................");

			LOG.info("得到了结果 - " + result);

		} catch (APIConnectionException e) {
			((org.slf4j.Logger) LOG).error("连接错误。应该稍后重试 ", e);

		} catch (APIRequestException e) {
			((org.slf4j.Logger) LOG).error("来自JPush服务器的错误响应。应该检查并修复它 ", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
			LOG.info("Msg ID: " + e.getMsgId());
		}
	}

	/**
	 * 构建发送信息
	 * @param title
	 * @param msgContent
	 * @return
	 */
	public Message buildMessageAndroid(String title, String msgContent) {
		Message message = new Message.Builder().setTitle(title).setMsgContent(msgContent).build();
		return message;
	}

	public static PushPayload buildPushObject_all_all_alert() {
		return PushPayload.alertAll(ALERT);
	}

	/**
	 * 向所有设备发送推送信息
	 * platform 推送平台
	 * audience 推送目标
	 * notification 通知
	 * message 自定义消息
	 * options 可选参数
	 * 
	 * @return
	 */
	public static PushPayload buildPushObject_all_alias_alert() {
		PushPayload pushPayload=  PushPayload.newBuilder()
				.setPlatform(Platform.all())// 设置接受的平台
				.setAudience(Audience.all())// Audience设置为all，说明采用广播方式推送，所有用户都可以接收到
				.setNotification(Notification.alert(ALERT))
				.build();
		return pushPayload;
	}

	/**
	 * 向Android指定组发送推送信息
	 * @return
	 */
	public static PushPayload buildPushObject_android_tag_alertWithTitle(String title, String content, JSONObject jsonObjectPayload) {
		PushPayload pushPayload= PushPayload.newBuilder()
			   .setPlatform(Platform.android())	
			   .setAudience(Audience.all())
			   .setNotification(Notification.newBuilder().addPlatformNotification(AndroidNotification.newBuilder().setAlert(content)
					   .setTitle(title).addExtra("result", jsonObjectPayload.toString()).build()).build())					   
			   .build();
		return pushPayload;
	}

	/**
	 * 向android和ios发送推送信息
	 * @return
	 */
	public static PushPayload buildPushObject_android_and_ios() {
		return PushPayload.newBuilder()
			   .setPlatform(Platform.android_ios())
			   .setAudience(Audience.tag("tag1"))
			   .setNotification(Notification.newBuilder()
					          .setAlert("alert content")
			                  .addPlatformNotification(AndroidNotification.newBuilder()
			                  .setTitle("Android Title").build())
			   .addPlatformNotification(IosNotification
					                   .newBuilder()
					                   .incrBadge(1)
					                   .addExtra("extra_key", "extra_value")
					                   .build())
						      .build())
				.build();
	}

	/**
	 * 向ios指定组发送推送信息
	 * @return
	 */
	public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage(String content, JSONObject jsonObjectPayload) {
		return PushPayload.newBuilder()
			   .setPlatform(Platform.ios())
			   .setAudience(Audience.all())
			   .setNotification(Notification.newBuilder()
			   .addPlatformNotification(IosNotification.newBuilder()
			      .setAlert(content)
			      .setBadge(5)
			      .setSound("happy")
			      .addExtra("result",jsonObjectPayload.toString()).build())
			     .build())
			   .setOptions(Options.newBuilder()
		       .setApnsProduction(true)
		       .build())
			  .build();
	}

	/**
	 * 向ios所有设备推送信息
	 * @return
	 */
	public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras() {
		return PushPayload.newBuilder()
			   .setPlatform(Platform.android_ios())
			   .setAudience(Audience.newBuilder()
					   .addAudienceTarget(AudienceTarget.tag("tag1", "tag2"))
					   .addAudienceTarget(AudienceTarget.alias("alias1", "alias2"))
					   .build())
				.setMessage(Message.newBuilder()
						.setMsgContent(MSG_CONTENT)
						.addExtra("from", "JPush")
						.build())
				.build();
	}

}
