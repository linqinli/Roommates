package com.netease.roommates.vo;

import com.netease.roommates.po.Personality;
import com.netease.roommates.po.User;

public class MatchUserSimpleInfo {
	private int userId;
	private String photoId;
	private String nickName;
	private String gender;
	private int matchScore;
	private String job;
	private int age;
	private String matchMessage;
	private boolean hasHouse;
	
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
	public String getGender() {
		return gender;
	}
	public void setGender(byte byteGender) {
		if(byteGender == 0) this.gender = "男";
		else this.gender = "女";
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public int getMatchScore() {
		return matchScore;
	}
	public void setMatchScore(int matchScore) {
		this.matchScore = matchScore;
	}
	public String getMatchMessage() {
		return matchMessage;
	}
	public void setMatchMessage(String matchMessage) {
		this.matchMessage = matchMessage;
	}
	
	// public List<User> userList = getUserByAddress(address);
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	
	@Override
	public String toString() {
		return "MatchUserInfo [userId=" + userId + ", photoId=" + photoId + ", gender=" + gender
				+ ", nickName=" + nickName  + ", matchScore=" + matchScore + ", matchMessage=" + 
				matchMessage + ", job=" + "]";
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public boolean isHasHouse() {
		return hasHouse;
	}
	public void setHasHouse(boolean hasHouse) {
		this.hasHouse = hasHouse;
	}
	
	public MatchUserSimpleInfo(User user, Personality personality){
		
	}
	
}
