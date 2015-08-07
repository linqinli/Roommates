package com.netease.roommates.mapper;

import java.util.List;

import com.netease.roommates.po.Personality;
import com.netease.roommates.po.User;

public interface UserMapper {
	public User getUserById(int id);

	public void insertUser(User user);

	public void updateUserBasicInfo(User user);

	public Personality getUserPersonality(int userId);

	public void insertUserPersonality(Personality personality);

	public void updateUserPersonality(Personality personality);

	public List<User> getUserListByAddress(String address);

	public List<User> getUserListByCompany(String company);

	public List<User> getUserByName(String name);
	
	public User getUserByEmail(String email);
	
	public int isQuestionnaireAll(int userId);
	
	//public void logoutById(int userId);
}
