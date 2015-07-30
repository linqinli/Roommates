package com.netease.match.service;

import java.util.Date;

import com.netease.exception.ServiceException;
import com.netease.roommates.vo.MatchUserDetailInfo;

public interface IMatchDetailService {
	
	public MatchUserDetailInfo getDetailByUser(int userId) throws ServiceException;
	
	public String dateToAge(Date date) throws ServiceException;
	
}
