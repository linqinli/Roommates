package com.netease.roommates.service;

import com.netease.roommates.po.User;

public interface IUserInfoService {
	public User getUserById(int id);
	
	public void insertUser(User user);
}
