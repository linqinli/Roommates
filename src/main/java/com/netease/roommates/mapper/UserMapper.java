package com.netease.roommates.mapper;

import com.netease.roommates.po.User;

public interface UserMapper {
	public User getUserById(int id);
	
	public void insertUser(User user);
}
