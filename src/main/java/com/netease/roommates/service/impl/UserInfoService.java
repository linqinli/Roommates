package com.netease.roommates.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.netease.roommates.mapper.UserMapper;
import com.netease.roommates.po.User;
import com.netease.roommates.service.IUserInfoService;

@Service
public class UserInfoService implements IUserInfoService {
	@Autowired
	private UserMapper userMapper;
	
	@Cacheable(value="userCache", key="#id")
	public User getUserById(int id) {
		System.out.println("UserInfoService.getUserById");
		return userMapper.getUserById(id);
	}
	
	public void insertUser(User user) {
		userMapper.insertUser(user);
	}
}
