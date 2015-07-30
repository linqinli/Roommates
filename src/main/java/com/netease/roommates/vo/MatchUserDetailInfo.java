package com.netease.roommates.vo;

import java.util.List;
import com.netease.roommates.po.UserHouse;
import com.netease.roommates.vo.MatchUserHouse;

public class MatchUserDetailInfo {
	private int userId;
	private String photoId;
	private String nickName;
	private String credit;
	private String company;
	private String job;
	private String age;
	private String gender;
	private List<String> tags;
	private String tel;
	private boolean hasHouse;
	private MatchUserHouse matchUserHouse;
	private boolean isFav;
	
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
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
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
	public boolean isHasHouse() {
		return hasHouse;
	}
	public void setHasHouse(boolean hasHouse) {
		this.hasHouse = hasHouse;
	}
	public MatchUserHouse getMatchUserHouse() {
		return matchUserHouse;
	}
	public void setMatchUserHouse(UserHouse userHouse) {
		this.matchUserHouse = new MatchUserHouse(userHouse);
	}
	public boolean getIsFav() {
		return isFav;
	}
	public void setFav(boolean isFav) {
		this.isFav = isFav;
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	
	
}
