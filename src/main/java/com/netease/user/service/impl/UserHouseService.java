package com.netease.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netease.exception.ServiceException;
import com.netease.roommates.mapper.UserHouseMapper;
import com.netease.roommates.po.UserHouse;
import com.netease.user.service.IUserHouseService;

@Service
public class UserHouseService implements IUserHouseService {
	@Autowired
	private UserHouseMapper uhouseMapper;
	
	public void insertUserHouse(UserHouse uhouse) throws ServiceException {
		uhouseMapper.insertUserHouse(uhouse);
	}
	
	public void updateUserHouseInfo(UserHouse uhouse) throws ServiceException {
		uhouseMapper.updateUserHouseInfo(uhouse);
	}

	@Override
	public UserHouse getUserHouseById(int userId) throws ServiceException {
		return uhouseMapper.getUserHouseById(userId);
	}

	@Override
	public boolean deleteUserHouse(int userId) throws ServiceException {
		uhouseMapper.deleteUserHouse(userId);
		return true;
	}
}
