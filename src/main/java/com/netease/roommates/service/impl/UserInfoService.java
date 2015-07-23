package com.netease.roommates.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.netease.exception.ServiceException;
import com.netease.exception.StorageException;
import com.netease.roommates.mapper.UserMapper;
import com.netease.roommates.po.User;
import com.netease.roommates.service.IUserInfoService;

@Service
public class UserInfoService implements IUserInfoService {
	private Logger log = LoggerFactory.getLogger(UserInfoService.class);
	@Autowired
	private UserMapper userMapper;

	//@Cacheable(value = "userCache", key = "#id")
	public User getUserById(int id) throws ServiceException {
		try {
			log.debug("UserInfoService.getUserById");
			return userMapper.getUserById(id);
		} catch (StorageException se) {
			log.error("error getting target user by id: " + id, se);
			throw new ServiceException(se);
		}
	}
	
	@CachePut(value="userCache", key = "#user.getUserId()")
	public void updateUserBasicInfo(User user) throws ServiceException {
		try {
			userMapper.updateUserBasicInfo(user);
		} catch (StorageException se) {
			log.error("error updatting target user: " + user, se);
			throw new ServiceException(se);
		}
	}

	public void insertUser(User user) {

	}
}
