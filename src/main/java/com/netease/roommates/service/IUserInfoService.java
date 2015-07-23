package com.netease.roommates.service;

import com.netease.exception.ServiceException;
import com.netease.roommates.po.User;

public interface IUserInfoService {
	public User getUserById(int id) throws ServiceException;
	
	public void updateUserBasicInfo(User user) throws ServiceException;
	
	public void insertUser(User user) throws ServiceException;
}
