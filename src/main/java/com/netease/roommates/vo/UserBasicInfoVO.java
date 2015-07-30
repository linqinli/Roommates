package com.netease.roommates.vo;

import java.util.Date;

import com.netease.roommates.po.User;

public class UserBasicInfoVO {
	private byte lookstatus;
	private String name;
	private String sex;
	private Date birth;
	private String phone;
	private String company;
	private String job;

	public UserBasicInfoVO() {

	}

	public byte getLookstatus() {
		return lookstatus;
	}

	public void setLookstatus(byte lookstatus) {
		this.lookstatus = lookstatus;
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

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public void populateUser(User user) {
		user.setStatus(lookstatus);
		user.setNickName(name);
		user.setBirthday(birth);
		user.setPhoneNumber(phone);
		user.setCompany(company);
		user.setPosition(job);
		if(sex != null) {
			user.setGender((byte) (sex.equals("女") ? 1 : 0));
		}
	}
}
