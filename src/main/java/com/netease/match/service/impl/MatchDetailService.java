package com.netease.match.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netease.exception.ServiceException;
import com.netease.match.service.IMatchDetailService;
import com.netease.roommates.mapper.RoommatesMapper;
import com.netease.roommates.po.User;
import com.netease.roommates.po.UserHouse;
import com.netease.roommates.vo.MatchUserDetailInfo;
import com.netease.roommates.vo.TagVO;
import com.netease.user.service.IRoommatesService;
import com.netease.user.service.IUserHouseService;
import com.netease.user.service.IUserInfoService;

@Service
public class MatchDetailService implements IMatchDetailService {
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private IUserHouseService userHouseService;
	@Autowired
	private RoommatesMapper roommatesMapper;
	@Autowired
	private IRoommatesService roommatesService;
	

	@Override
	public MatchUserDetailInfo getDetailByUser(int userId) throws ServiceException {
		User user = userInfoService.getUserById(userId);
		MatchUserDetailInfo matchUserDetailInfo = new MatchUserDetailInfo();
		if(user == null) return null;
		matchUserDetailInfo.setUserId(userId);
		if(user.getHasPhoto()) matchUserDetailInfo.setPhotoId(user.getUserId(), 0);
		else matchUserDetailInfo.setPhotoId(-1, 0);
		matchUserDetailInfo.setCredit("一般");
		if(user.getCompany()!=null) matchUserDetailInfo.setCompany(user.getCompany());
		if(user.getPosition()!=null) matchUserDetailInfo.setJob(user.getPosition());
		if(user.getBirthday()!=null) matchUserDetailInfo.setAge(dateToAge(user.getBirthday()));
		if(user.getGender()!=null) matchUserDetailInfo.setGender(user.getGender());
		if(user.getNickName()!=null) matchUserDetailInfo.setNickName(user.getNickName());
		//tags
		if(user.getPersonality()!=null) matchUserDetailInfo.setTags(new TagVO(user.getPersonality()));
		if(user.getPhoneNumber()!=null) matchUserDetailInfo.setTel(user.getPhoneNumber());

		if(user.getUserHouse()!=null){
			matchUserDetailInfo.setHasHouse(true);
			UserHouse userHouse = userHouseService.getUserHouseById(user.getUserId());
			if(userHouse!=null) matchUserDetailInfo.setMatchUserHouse(userHouse);
		}
		else matchUserDetailInfo.setHasHouse(false);
		
		return matchUserDetailInfo;
	}
	
	@Override
	public String dateToAge(Date date){
		String age = "";
		if(date==null) return age;
		Date curDate = new Date();
		int curYear = curDate.getYear();
		int birYear = date.getYear();
		age = (curYear-birYear)+"岁";
		return age;
	}

	@Override
	public MatchUserDetailInfo getDetailByUser(int curUserId, int userId) throws ServiceException {
		User user = userInfoService.getUserById(userId);
		MatchUserDetailInfo matchUserDetailInfo = new MatchUserDetailInfo();
		if(user == null) return null;
		matchUserDetailInfo.setUserId(userId);
		if(user.getHasPhoto()) matchUserDetailInfo.setPhotoId(user.getUserId(), 0);
		else matchUserDetailInfo.setPhotoId(-1, 0);
		matchUserDetailInfo.setCredit("一般");
		if(user.getNickName()!=null) matchUserDetailInfo.setNickName(user.getNickName());
		if(user.getCompany()!=null) matchUserDetailInfo.setCompany(user.getCompany());
		if(user.getPosition()!=null) matchUserDetailInfo.setJob(user.getPosition());
		if(user.getBirthday()!=null) matchUserDetailInfo.setAge(dateToAge(user.getBirthday()));
		if(user.getGender()!=null) matchUserDetailInfo.setGender(user.getGender());
		//tags
		if(user.getPersonality()!=null) matchUserDetailInfo.setTags(new TagVO(user.getPersonality()));
		if(user.getPhoneNumber()!=null) matchUserDetailInfo.setTel(user.getPhoneNumber());
		
		if(user.getUserHouse()!=null){
			matchUserDetailInfo.setHasHouse(true);
			UserHouse userHouse = userHouseService.getUserHouseById(user.getUserId());
			if(userHouse!=null) matchUserDetailInfo.setMatchUserHouse(userHouse);
		}
		else matchUserDetailInfo.setHasHouse(false);
		
		List<Integer> userIds = roommatesMapper.selectAllFavorite(curUserId);
		if(userIds.indexOf(userId) != -1) matchUserDetailInfo.setFav(true);
		else matchUserDetailInfo.setFav(false);
		return matchUserDetailInfo;
	}

}
