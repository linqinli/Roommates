package com.netease.user.service;

import com.netease.exception.ServiceException;
import com.netease.roommates.po.UserHouse;

public interface IUserHouseService {
	
	public void updateUserHouseInfo(UserHouse uhouse) throws ServiceException;
	
	public void insertUserHouse(UserHouse uhouse) throws ServiceException;
	
	public UserHouse getUserHouseById(int userId) throws ServiceException;
}
