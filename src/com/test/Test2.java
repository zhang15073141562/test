package com.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.jar.Attributes.Name;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * 一个测试类
 * @author zhangxiongjie
 *
 */
public class Test2 {
	public static void main(String[] args) {
		Test2 test2 = new Test2();
		//test2.createXml("E:\\workplace\\test\\src\\com\\test\\test.xml");
		test2.parserXml("E:\\workplace\\test\\src\\com\\test\\test.xml");
	}

	/**
	 * 创建xml
	 * 
	 * @param fileName
	 */
	public void createXml(String fileName) {
		Document document = DocumentHelper.createDocument();
		Element employees = document.addElement("employees");
		Element employee = employees.addElement("employee");
		Element emoloyee2=employees.addElement("employee");
		Element name1=emoloyee2.addElement("name");
		name1.setText("liming");
		Element sex1 = emoloyee2.addElement("sex");
		sex1.setText("m");
		Element age1 = emoloyee2.addElement("age");
		age1.setText("29");
		Element name = employee.addElement("name");
		name.setText("ddvip");
		Element sex = employee.addElement("sex");
		sex.setText("m");
		Element age = employee.addElement("age");
		age.setText("29");
		try {
			Writer fileWriter = new FileWriter(fileName);
			XMLWriter xmlWriter = new XMLWriter(fileWriter);
			xmlWriter.write(document);
			xmlWriter.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 解析xml
	 * 
	 * @param fileName
	 */
	@SuppressWarnings("rawtypes")
	public void parserXml(String fileName) {
		ArrayList<Information> list=new ArrayList<Information>();
		File inputXml=new File(fileName);
		SAXReader saxReader=new SAXReader();
		try {
			Document document=saxReader.read(inputXml);
			Element employees=document.getRootElement();
			for(Iterator i=employees.elementIterator();i.hasNext();){//通过迭代器 进行遍历 获得数据
				Element employee=(Element)i.next();
				Information in=new Information();
				for(Iterator j=employee.elementIterator();j.hasNext();){
					Element node=(Element)j.next();
					if(node.getName().equals("name")){
						in.setName(node.getText());
					}else if(node.getName().equals("sex")){
						in.setSex(node.getText());
					}else if(node.getName().equals("age")){
						in.setAge(Integer.parseInt(node.getText()));
					}
				}
				list.add(in);					
				
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}		
        for (Information information : list) {//循环输出解析得到的值
			System.out.println(information.getName()+":"+information.getSex()+":"+information.getAge());
		}
		System.out.println("解析完xml文件");
	}
}
