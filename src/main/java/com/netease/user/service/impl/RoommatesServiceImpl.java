package com.netease.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netease.exception.ServiceException;
import com.netease.match.service.IMatchDetailService;
import com.netease.roommates.mapper.RoommatesMapper;
import com.netease.roommates.po.User;
import com.netease.roommates.vo.MatchUserDetailInfo;
import com.netease.user.service.IRoommatesService;
import com.netease.user.service.IUserHouseService;

@Service
public class RoommatesServiceImpl implements IRoommatesService {
	@Autowired
	private RoommatesMapper roommatesMapper;
	@Autowired
	private IUserHouseService userHouseService;
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
			List<User> userList = roommatesMapper.selectAllFavorite(userId);
			for (User user : userList) {
				matchUsers.add(matchDetailService.getDetailByUser(user.getUserId()));
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return matchUsers;
	}
}
