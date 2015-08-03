package com.netease.user.service;

import java.util.List;

import com.netease.roommates.vo.MatchUserSimpleInfo;

public interface IRoommatesService {
	public void addHate(int userId, int hate);
	
	public void addFavorite(int userId, int favorite);
	
	public void removeFavorite(int userId, int favorite);
	
	public List<MatchUserSimpleInfo> getAllFavorite(int userId);
}
