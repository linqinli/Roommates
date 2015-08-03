package com.netease.roommates.vo;

import java.util.List;

import com.netease.roommates.po.UserHouse;

public class MatchUserHouse {
	private int id;
	private String title;
	private List<String> images;
	private String address;
	private String community;
	private String price;
	private String description;
	private String updateTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
	public String getCommunity() {
		return community;
	}
	public void setCommunity(String community) {
		this.community = community;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	

	public MatchUserHouse(UserHouse userHouse){
		this.setId(userHouse.getUserId());
		this.setTitle(userHouse.getTitle());
		this.setImages(userHouse.getPicList());
		this.setAddress(userHouse.getArea());
		this.setCommunity(userHouse.getCommunity());
		this.setPrice(userHouse.getPrice());
		this.setDescription(userHouse.getDescription());
		this.setUpdateTime(userHouse.getUpdateTime());
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	

}
