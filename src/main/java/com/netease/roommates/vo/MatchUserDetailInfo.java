package com.netease.roommates.vo;

import java.util.List;

import com.netease.roommates.po.UserHouse;

public class MatchUserDetailInfo {
	private int userId;
	private String photoId;
	private String nickName;
	private String company;
	private String job;
	private int age;
	private String gender;
	private List<String> tags;
	private String tel;
	private UserHouse house;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getPhotoId() {
		return photoId;
	}
	public void setPhotoId(int userid, int mode) {
		String prefix = "http://223.252.223.13/Roommates/photo/photo_";
		String smallSuffix = "_small.jpg";
		String suffix = ".jpg";
		this.photoId = prefix + userid + suffix;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(byte byteGender) {
		if(byteGender == 0) this.gender = "男";
		else this.gender = "女";
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public UserHouse getHouse() {
		return house;
	}
	public void setHouse(UserHouse house) {
		this.house = house;
	}
}
