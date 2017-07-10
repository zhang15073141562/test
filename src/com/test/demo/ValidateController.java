package com.test.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

/**
 * 验证码验证控制层
 * @author zhangxiongjie
 *
 */
public class ValidateController {
	
	public ValidateController(){
		
	}
	
	/**
	 * 进行校验
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void test(HttpServletRequest request,HttpServletResponse response) throws IOException{
		JSONObject jsonObject = new JSONObject();
		response.setContentType("application/josn;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String code = request.getParameter("code");
		System.out.println(code);
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		Map<String, Object> map = new HashMap<>();
		String sessionCode = (String) session.getAttribute("code");  
		if (!StringUtils.equalsIgnoreCase(code, sessionCode)) {
			map.put("message", "验证码错误");
			jsonObject.put("data", map);
			jsonObject.put("status", false);
		} else{
			map.put("message", "验证码正确");
			jsonObject.put("data", map);
			jsonObject.put("status", false);
		}
		out.println(jsonObject);
		out.flush();
		out.close();
	}

}
