package com.netease.user.service;

import java.util.List;

import com.netease.exception.ServiceException;
import com.netease.exception.StorageException;
import com.netease.roommates.po.Personality;
import com.netease.roommates.po.User;

public interface IUserInfoService {
	public User getUserById(int id) throws ServiceException;
	
	public void updateUserBasicInfo(User user) throws ServiceException;
	
	public void insertUser(User user) throws ServiceException;
	
	public Personality getUserPersonality(int id) throws ServiceException;
	
	public void insertUserPersonality(Personality personality) throws ServiceException;
	
	public void updateUserPersonality(Personality personality) throws ServiceException;
	
	public List<User> getUserListByAddress(String address) throws ServiceException;

	public List<User> getUserListByCompany(String company) throws ServiceException;

	public User getUserByName(String name) throws ServiceException; 
}
