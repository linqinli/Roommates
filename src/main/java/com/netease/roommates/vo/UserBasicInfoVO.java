package com.netease.roommates.vo;

import java.util.Date;

import org.springframework.util.StringUtils;

import com.netease.roommates.po.User;

public class UserBasicInfoVO {
	private Byte lookstatus;
	private String name;
	private String sex;
	private Date birth;
	private String phone;
	private String company;
	private String job;

	public UserBasicInfoVO() {

	}

	public Byte getLookstatus() {
		return lookstatus;
	}

	public void setLookstatus(Byte lookstatus) {
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
		user.setBirthday(birth);
		user.setNickName(checkNull(name));
		user.setPhoneNumber(checkNull(phone));
		user.setCompany(checkNull(company));
		user.setPosition(checkNull(job));
		if (lookstatus != null) {
			user.setStatus(lookstatus.byteValue() ==  1 ? (byte) 1 : (byte) 0);
		}
		if (sex != null) {
			switch (sex.charAt(0)) {
			case '男':
				user.setGender((byte) 0);
				break;
			case '女':
				user.setGender((byte) 1);
				break;
			default:
				user.setGender(null); // blank
			}
		} else {
			user.setGender(null);
		}
	}

	private String checkNull(String str) {
		if (!StringUtils.isEmpty(str)) {
			return str;
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		return "UserBasicInfoVO [lookstatus=" + lookstatus + ", name=" + name + ", sex=" + sex + ", birth=" + birth
				+ ", phone=" + phone + ", company=" + company + ", job=" + job + "]";
	}

}
