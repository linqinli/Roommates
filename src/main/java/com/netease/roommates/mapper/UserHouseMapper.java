package com.netease.roommates.mapper;

import com.netease.roommates.po.UserHouse;

public interface UserHouseMapper {
	
	public UserHouse getUserHouseById(int id);

	public void insertUserHouse(UserHouse uhouse);

	public void updateUserHouseInfo(UserHouse user);

	
}
