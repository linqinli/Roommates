package com.netease.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netease.exception.ServiceException;
import com.netease.roommates.mapper.RoommatesMapper;
import com.netease.roommates.po.User;
import com.netease.roommates.po.UserHouse;
import com.netease.roommates.vo.MatchUserDetailInfo;
import com.netease.user.service.IRoommatesService;
import com.netease.user.service.IUserHouseService;

@Service
public class RoommatesServiceImpl implements IRoommatesService {
	@Autowired
	private RoommatesMapper roommatesMapper;
	@Autowired
	private IUserHouseService userHouseService;

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
		List<User> userList = roommatesMapper.selectAllFavorite(userId);
		List<MatchUserDetailInfo> matchUsers = new ArrayList<MatchUserDetailInfo>();
		for (User user : userList) {
			matchUsers.add(transformToMatchUserDetailInfo(user));
		}
		return matchUsers;
	}

	private MatchUserDetailInfo transformToMatchUserDetailInfo(User user) {

		MatchUserDetailInfo matchUserDetailInfo = new MatchUserDetailInfo();
		try {
			matchUserDetailInfo.setUserId(user.getUserId());
			matchUserDetailInfo.setPhotoId(user.getUserId(), 0);
			matchUserDetailInfo.setNickName(user.getNickName());
			matchUserDetailInfo.setGender(user.getGender());
			matchUserDetailInfo.setCompany(user.getCompany());
			matchUserDetailInfo.setTel(user.getPhoneNumber());
			if (user.getPersonality() != null) {
				if (user.getPersonality().getHasHouse() == 1) {
					matchUserDetailInfo.setHasHouse(true);
					UserHouse userHouse = userHouseService.getUserHouseById(user.getUserId());
					matchUserDetailInfo.setHouse(userHouse);
				} else {
					matchUserDetailInfo.setHasHouse(false);
				}
			}
		} catch (ServiceException se) {
			// Swallow it.
		}
		return matchUserDetailInfo;
	}
}
