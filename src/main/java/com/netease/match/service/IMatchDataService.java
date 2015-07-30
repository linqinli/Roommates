package com.netease.match.service;

import java.util.List;
import java.util.Map;

import com.netease.exception.ServiceException;

public interface IMatchDataService {
	
	public List selectAllUsers() throws ServiceException;
	
	public List<Integer> getUserIdListByCondition(int xb, int f, int gs, int cy, int cw,
			int zx, int ws, int xg, int fk) throws ServiceException;
	
	// public Map<String, Object> getMatchUserByCondition(int id, int xb, int f, int gs, int cy, int cw,
	//		int zx, int ws, int xg, int fk) throws ServiceException;

}
