package com.netease.roommates.po;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class BatchPhotoModel {

	private List<MultipartFile> files;
	
	private int userId;
	
	private String title;
	private String price;
	private String community;
	private String area;
	private String description;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	public BatchPhotoModel(int userId, List<MultipartFile> files) {
		super();
		this.files = files;
		this.userId = userId;
	}

	public BatchPhotoModel() {
		super();
	}
}