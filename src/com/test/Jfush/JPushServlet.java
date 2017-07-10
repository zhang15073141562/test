package com.test.Jfush;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.util.JPushUtils;

import cn.jpush.api.push.PushResult;
import net.sf.json.JSONObject;


public class JPushServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;  
    
    private static final String appKey ="8f8769e0df8aa77fb3a09e8b";  
    private static final String masterSecret = "76b6d0627162fd81f9cc11be"; 
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Jdpush.testSendPush(appKey,masterSecret);
/*		JPushUtils jPushUtils=new JPushUtils(masterSecret,appKey,appKey);
		JSONObject jsonObjectPayload=new JSONObject();
		jsonObjectPayload.put("name", "小米");
		jsonObjectPayload.put("age", "19");
		String title="极光推送";
		String content="第一次运行极光推送";
		try {
			PushResult result =jPushUtils.sendBroadcastAll(title, content, jsonObjectPayload, JPushUtils.TYPE_ANDROID);
			System.out.println(result + "................................");
		} catch (Exception e) {
		
			e.printStackTrace();
		}*/
        System.out.println("sucess");  
	}

}
