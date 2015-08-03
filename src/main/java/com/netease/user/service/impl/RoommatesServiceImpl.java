package com.netease.user.service.impl;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netease.exception.ServiceException;
import com.netease.match.service.IMatchDataService;
import com.netease.roommates.mapper.RoommatesMapper;
import com.netease.roommates.vo.MatchUserSimpleInfo;
import com.netease.user.service.IRoommatesService;

@Service
public class RoommatesServiceImpl implements IRoommatesService {
	private final static Logger logger = LoggerFactory.getLogger(RoommatesServiceImpl.class);
	@Autowired
	private RoommatesMapper roommatesMapper;
	@Autowired
	private IMatchDataService matchDataService;

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
	public List<MatchUserSimpleInfo> getAllFavorite(int userId) {
		try {
			return matchDataService.matchResultSimpleInfo(userId, roommatesMapper.selectAllFavorite(userId));

		} catch (ServiceException e) {
			logger.error("Error getting all favorite.", e);
		}
		return Collections.emptyList();
	}
}
