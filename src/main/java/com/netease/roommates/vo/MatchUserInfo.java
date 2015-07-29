package com.netease.roommates.vo;

public class MatchUserInfo {
	private int userId;
	private String userName;
	private String photoId;
	private byte gender;
	private String nickName;
	private String company;
	private String job;
	private int matchScore;
	private String matchMessage;
	private int age;
	
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
	public String getPhotoId() {
		return photoId;
	}
	public void setPhotoId(int userid, int mode) {
		String prefix = "http://223.252.223.13/Roommates/photo/photo_";
		String smallSuffix = "_small.jpg";
		String suffix = ".jpg";
		this.photoId = prefix + userid + suffix;
	}
	public byte getGender() {
		return gender;
	}
	public void setGender(byte gender) {
		this.gender = gender;
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
		return "MatchUserInfo [userId=" + userId + ", userName=" + userName + ", photoId=" + photoId + ", gender=" + gender
				+ ", nickName=" + nickName + "company="+ company + "matchScore=" + matchScore + "matchMessage=" + 
				matchMessage + "job=" + "]";
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}
