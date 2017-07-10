package com.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Test {

	public static void main(String[] args) {
		// System.out.println(uuid());
		/*
		 * String code="123"; String language=""; if (code != null &&
		 * code.length() >= 1) { if (language != null && language.length() >= 1)
		 * { code=code+"-"+language; } } System.out.println(code);
		 */
/*		try {
			propertiesAna();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}*/

		testdate();
	}
	
	public static void testdate(){
		
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(new Date());

		calendar.add(Calendar.MONTH, 1);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=simpleDateFormat.format(calendar.getTime());
		try {
			Date date=simpleDateFormat.parse(time);
			System.out.println("增加月份后的日期："+date);
		} catch (ParseException e) {			
			e.printStackTrace();
		}

	}

	/**
	 * 解析json 
	 */
	public static void analysis() {
		JSONObject jsonobj = JSONObject.fromObject("{'name':'xiazdong','age':20}");
		String name = jsonobj.getString("name");
		int age = jsonobj.getInt("age");

		JSONArray jsonarray = JSONArray.fromObject("[{'name':'xiazdong','sex':'m','age':20},{'name':'xzdong','sex':'m','age':15}]");

		ArrayList<Information> list=new ArrayList<Information>();
		//把json数据放入实体类里面
		for (Object object : jsonarray) {
			Information in=new Information();
			JSONObject j1=(JSONObject) object;
			in.setName(j1.getString("name"));
			in.setSex(j1.getString("sex"));
			in.setAge(Integer.parseInt(j1.getString("age")));
		    list.add(in);
		}
		for (Information information : list) {
			System.out.println(information.getName()+":"+information.getSex()+":"+information.getAge());
		}
	}

	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 解析properties文件
	 * 
	 * @throws FileNotFoundException
	 */
	public static void propertiesAna() throws FileNotFoundException {
		try {
			// InputStream inStream = new FileInputStream(new
			// File("E:\\workplace\\test\\src\\com\\test\\comm.properties"));
			InputStream inStream = Test.class.getResourceAsStream("comm.properties");
			Properties properties = new Properties();
			properties.load(inStream);
			String key = properties.getProperty("name");
			String path = Test.class.getResource("").getPath();
			System.out.println(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
