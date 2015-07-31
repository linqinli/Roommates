package com.netease.roommates.po;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int userId;
	private String userName;
	private byte gender;
	private byte status;
	private String nickName;
	private Date birthday;
	private String position;
	private String pwdMD5Hash;
	private String phoneNumber;
	private String companyEmail;
	private String company;
	private String address;
	private String constellation;
	private Personality personality;
	private UserHouse userHouse;
	
	public User() {
		// used for serialization.
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public byte getGender() {
		return gender;
	}

	public void setGender(byte gender) {
		this.gender = gender;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPwdMD5Hash() {
		return pwdMD5Hash;
	}

	public void setPwdMD5Hash(String pwdMD5Hash) {
		this.pwdMD5Hash = pwdMD5Hash;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyMail) {
		this.companyEmail = companyMail;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getConstellation() {
		return constellation;
	}

	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}

	public Personality getPersonality() {
		return personality;
	}

	public void setPersonality(Personality personality) {
		this.personality = personality;
	}

	public UserHouse getUserHouse() {
		return userHouse;
	}

	public void setUserHouse(UserHouse userHouse) {
		this.userHouse = userHouse;
	}
	
}
