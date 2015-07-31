package com.netease.roommates.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface RoommatesMapper {
	public void insertHate(@Param("userId")int userId, @Param("hate")int hate);
	
	public void insertFavorite(@Param("userId")int userId, @Param("favorite")int favorite);
	
	public void deleteFavorite(@Param("userId")int userId, @Param("favorite")int favorite);
	
	public List<Integer> selectAllFavorite(@Param("userId")int userId);
}
