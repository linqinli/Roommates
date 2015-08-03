package com.netease.roommates.po;

import java.io.Serializable;

public class Personality implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private int userId;
	private int smoking = 1;
	private int pet = 1;
	private int dailySchedule = 1;
	private int visitor = 1;
	private int cleanliness = 1;
	private int personCharacter = 1;
	
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

	public int getCleanliness() {
		return cleanliness;
	}


	public void setCleanliness(int cleanliness) {
		this.cleanliness = cleanliness;
	}


	public int getPersonCharacter() {
		return personCharacter;
	}


	public void setPersonCharacter(int personCharacter) {
		this.personCharacter = personCharacter;
	}

}
