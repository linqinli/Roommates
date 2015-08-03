package com.netease.roommates.vo;

import java.util.Date;

import org.springframework.util.StringUtils;

import com.netease.roommates.po.User;

public class UserBasicInfoVO {
	private Byte lookStatus;
	private String nickName;
	private String gender;
	private Date birthday;
	private String phone;
	private String company;
	private String job;

	public UserBasicInfoVO() {

	}

	public Byte getLookStatus() {
		return lookStatus;
	}

	public void setLookStatus(Byte lookStatus) {
		this.lookStatus = lookStatus;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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
		user.setBirthday(birthday);
		user.setNickName(checkNull(nickName));
		user.setPhoneNumber(checkNull(phone));
		user.setCompany(checkNull(company));
		user.setPosition(checkNull(job));
		if (lookStatus != null) {
			user.setStatus(lookStatus.byteValue() ==  1 ? (byte) 1 : (byte) 0);
		}
		if (gender != null) {
			switch (gender.charAt(0)) {
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

}
