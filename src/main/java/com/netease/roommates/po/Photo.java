package com.netease.roommates.po;

import java.io.Serializable;

public class Photo implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private int userId;
	private Object photo;
	
	public Photo() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Object getPhoto() {
		return photo;
	}

	public void setPhoto(Object photo) {
		this.photo = photo;
	}
	
}
