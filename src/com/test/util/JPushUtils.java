package com.test.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import net.sf.json.JSONObject;

/**
 * 极光推送工具类
 * @author zhangxiongjie
 * @createDate 2017-06-27
 *
 */
public class JPushUtils {
	
	protected static final Logger LOG = LoggerFactory.getLogger(JPushUtils.class);
	
	public final static int TYPE_ANDROID = 0;
	public final static int TYPE_IOS = 1;
	private String ANDROID_APP_SECRET = "";
	private String IOS_APP_SECRET = "";
	private String appkey="";
	
	public JPushUtils(){
		
	}
	
	public JPushUtils(String androidAppSecret, String iosAppSecret,String appkey){
		this.ANDROID_APP_SECRET=androidAppSecret;
		this.IOS_APP_SECRET=iosAppSecret;
		this.appkey=appkey;
	}
	
	
	/**
	 * 准备极光推送
	 * @param title
	 * @param content
	 * @param jsonObjectPayload
	 * @param deviceType
	 * @param timeToSend
	 * @return
	 * @throws Exception
	 */
	public PushResult sendBroadcastAll(String title, String content, JSONObject jsonObjectPayload, int deviceType
			) throws Exception {

		JPushClient sender = null;
		if (deviceType == TYPE_ANDROID) {
			sender = new JPushClient(ANDROID_APP_SECRET,appkey); // 需要根据appSecert来发送
		} else if (deviceType == TYPE_IOS) {
			sender = new JPushClient(IOS_APP_SECRET,appkey); // 需要根据appSecert来发送
		}
		
		PushPayload pushPayload = buildPushPayload(title, content, jsonObjectPayload, deviceType);
		System.out.println(pushPayload);
		PushResult result = sender.sendPush(pushPayload);// 推送消息给所有设备，不重试
		
		return result;
	}
	
	
	/**
	 * 根据类型选择推送
	 * @param title
	 * @param content
	 * @param jsonObjectPayload
	 * @param deviceType
	 * @return
	 */
	private PushPayload buildPushPayload(String title, String content, JSONObject jsonObjectPayload, int deviceType) {
		
		PushPayload pushPayload=null;
		if (deviceType == TYPE_ANDROID) {
			pushPayload = buildPushObject_android_tag_alertWithTitle(title, content, jsonObjectPayload);
		} else if (deviceType == TYPE_IOS) {
			//pushPayload = buildPushObject_ios_tagAnd_alertWithExtrasAndMessage(content, jsonObjectPayload);
			pushPayload = buildPushObject_ios_test(content, jsonObjectPayload);
		}
		return pushPayload;
	}
	
	public static PushPayload buildPushObject_ios_test(String content, JSONObject jsonObjectPayload) {
		return PushPayload.newBuilder()
			   .setPlatform(Platform.ios())
			   .setAudience(Audience.all())
			   .setNotification(Notification.alert(content))
			   .build();
	}


	/**
	 * 向Android指定组发送推送信息,推送方式为广播
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
	 * 向ios设备发送推送信息,推送方式为广播
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
	
	public static void main(String[] args) throws Exception {
		JPushUtils jPushUtils=new JPushUtils("2d207f871aa691288d3214a0", "2d207f871aa691288d3214a0", "83b71f7964b8ebbabb6ac61d");
		JSONObject jsonObjectPayload=new JSONObject();
		jsonObjectPayload.put("name", "小米");
		jsonObjectPayload.put("age", "19");
		
		try {
			
			PushResult result =jPushUtils.sendBroadcastAll("极光推送", "第一次极光推送", jsonObjectPayload, JPushUtils.TYPE_IOS);
			System.out.println(result + "................................");
			LOG.info("得到了结果 - " + result);
		}catch (APIConnectionException e) {
			((org.slf4j.Logger) LOG).error("连接错误。应该稍后重试 ", e);

		} catch (APIRequestException e) {
			((org.slf4j.Logger) LOG).error("来自JPush服务器的错误响应。应该检查并修复它 ", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
			LOG.info("Msg ID: " + e.getMsgId());
		}
		
	}

}
