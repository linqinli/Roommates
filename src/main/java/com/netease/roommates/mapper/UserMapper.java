package com.netease.roommates.mapper;

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
	
	public User getUserByName(String name) throws StorageException;

}
