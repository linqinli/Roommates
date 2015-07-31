package com.netease.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netease.exception.ServiceException;
import com.netease.match.service.IMatchDetailService;
import com.netease.roommates.mapper.RoommatesMapper;
import com.netease.roommates.vo.MatchUserDetailInfo;
import com.netease.user.service.IRoommatesService;

@Service
public class RoommatesServiceImpl implements IRoommatesService {
	private final static Logger logger = LoggerFactory.getLogger(RoommatesServiceImpl.class);
	@Autowired
	private RoommatesMapper roommatesMapper;
	@Autowired
	private IMatchDetailService matchDetailService;

	@Override
	public void addHate(int userId, int hate) {
		roommatesMapper.insertHate(userId, hate);
	}

	@Override
	public void addFavorite(int userId, int favorite) {
		roommatesMapper.insertFavorite(userId, favorite);
	}

	@Override
	public void removeFavorite(int userId, int favorite) {
		roommatesMapper.deleteFavorite(userId, favorite);
	}

	@Override
	public List<MatchUserDetailInfo> getAllFavorite(int userId) {
		List<MatchUserDetailInfo> matchUsers = new ArrayList<MatchUserDetailInfo>();
		try {
			for (int favUserId : roommatesMapper.selectAllFavorite(userId)) {
				matchUsers.add(matchDetailService.getDetailByUser(favUserId));
			}
		} catch (ServiceException e) {
			logger.error("Error getting all favorite.", e);
		}
		return matchUsers;
	}
}
