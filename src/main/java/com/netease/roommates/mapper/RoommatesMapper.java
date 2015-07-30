package com.netease.roommates.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.netease.roommates.po.User;

public interface RoommatesMapper {
	public void insertHate(@Param("userId")int userId, @Param("hate")int hate);
	
	public void insertFavorite(@Param("userId")int userId, @Param("favorite")int favorite);
	
	public void deleteFavorite(@Param("userId")int userId, @Param("favorite")int favorite);
	
	public List<User> selectAllFavorite(@Param("userId")int userId);
}
