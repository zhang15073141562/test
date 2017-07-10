package com.test;

/**
 * 一个测试的javabean
 * @author zhangxiongjie
 *
 */
public class Information {
	
	private String name;//姓名
	
	private String sex;//性别
	
	private Integer age;//年龄
	
	

	public Information() {
		super();
	}
	
	

	public Information(String name, String sex, Integer age) {
		super();
		this.name = name;
		this.sex = sex;
		this.age = age;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
	

}
