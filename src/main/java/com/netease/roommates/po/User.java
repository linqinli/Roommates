package com.netease.roommates.po;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int userId;
	private String userName;
	private int photoId;
	private byte gender;
	private String nickName;
	private Date birthday;
	private String position;
	private String pwdMD5Hash;
	private String phoneNumber;
	private String companyEmail;
	private String company;
	private String address;
	private String weChat;
	private String QQ;
	private String yixin;
	private Personality personality;
	
	public User() {
		// used for serialization.
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getPhotoId() {
		return photoId;
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

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
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

	public String getWeChat() {
		return weChat;
	}

	public void setWeChat(String weChat) {
		this.weChat = weChat;
	}

	public String getQQ() {
		return QQ;
	}

	public void setQQ(String QQ) {
		this.QQ = QQ;
	}

	public String getYixin() {
		return yixin;
	}

	public void setYixin(String yixin) {
		this.yixin = yixin;
	}

	public Personality getPersonality() {
		return personality;
	}

	public void setPersonality(Personality personality) {
		this.personality = personality;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", photoId=" + photoId + ", gender=" + gender
				+ ", nickName=" + nickName + ", pwdMD5Hash=" + pwdMD5Hash + ", phoneNumber=" + phoneNumber
				+ ", companyEmail=" + companyEmail + ", weChat=" + weChat + ", QQ=" + QQ + ", yixin=" + yixin
				+ ", personality=" + personality + "]";
	}
	
}
