package com.netease.roommates.mapper;

import java.util.List;

import com.netease.exception.StorageException;
import com.netease.roommates.po.Personality;
import com.netease.roommates.po.User;

public interface UserMapper {
	public User getUserById(int id) throws StorageException;

	public void insertUser(User user) throws StorageException;

	public void updateUserBasicInfo(User user) throws StorageException;

	public Personality getUserPersonality(int id) throws StorageException;

	public void insertUserPersonality(Personality personality) throws StorageException;

	public void updateUserPersonality(Personality personality) throws StorageException;

	public List<User> getUserListByAddress(String address) throws StorageException;

	public List<User> getUserListByCompany(String company) throws StorageException;

	public List<User> getUserByName(String name) throws StorageException;
	
	public User getUserByEmail(String email) throws StorageException;
	
	public int isQuestionnaireAll(int userId) throws StorageException;
	
	public Personality getUserPersonalityById(int userId) throws StorageException;
}
