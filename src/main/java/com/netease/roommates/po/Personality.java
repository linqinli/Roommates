package com.netease.roommates.po;

import java.io.Serializable;
import java.util.Date;

public class Personality implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userId;
	private int smoking;
	private int pet;
	private int dailySchedule;
	private int visitor;
	private Date birthday;
	private String constellation;
	private int sanitation;
	private int character;
	
	public Personality() {
		
	}
	
	
	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public int getSmoking() {
		return smoking;
	}

	public void setSmoking(int smoking) {
		this.smoking = smoking;
	}

	public int getPet() {
		return pet;
	}

	public void setPet(int pet) {
		this.pet = pet;
	}

	public int getDailySchedule() {
		return dailySchedule;
	}

	public void setDailySchedule(int dailySchedule) {
		this.dailySchedule = dailySchedule;
	}

	public int getVisitor() {
		return visitor;
	}

	public void setVisitor(int visitor) {
		this.visitor = visitor;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getConstellation() {
		return constellation;
	}

	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}

	public int getSanitation() {
		return sanitation;
	}

	public void setSanitation(int sanitation) {
		this.sanitation = sanitation;
	}

	public int getCharacter() {
		return character;
	}

	public void setCharacter(int character) {
		this.character = character;
	}
	
	
}
