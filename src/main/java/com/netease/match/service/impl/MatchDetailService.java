package com.netease.match.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netease.exception.ServiceException;
import com.netease.match.service.IMatchDetailService;
import com.netease.roommates.po.User;
import com.netease.roommates.po.UserHouse;
import com.netease.roommates.vo.MatchUserDetailInfo;
import com.netease.user.service.IUserHouseService;
import com.netease.user.service.IUserInfoService;

@Service
public class MatchDetailService implements IMatchDetailService {
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private IUserHouseService userHouseService;
	

	@Override
	public MatchUserDetailInfo getDetailByUser(int userId) throws ServiceException {
		User user = userInfoService.getUserById(userId);
		MatchUserDetailInfo matchUserDetailInfo = new MatchUserDetailInfo();
		matchUserDetailInfo.setUserId(userId);
		matchUserDetailInfo.setPhotoId(userId, 0);
		matchUserDetailInfo.setCredit("一般");
		matchUserDetailInfo.setCompany(user.getCompany());
		matchUserDetailInfo.setJob(user.getPosition());
		matchUserDetailInfo.setAge(dateToAge(user.getBirthday()));
		matchUserDetailInfo.setGender(user.getGender());
		//tags
		matchUserDetailInfo.setTel(user.getPhoneNumber());
		
		if(user.getPersonality()!=null){
			if(user.getPersonality().getHasHouse()==2){
				matchUserDetailInfo.setHasHouse(true);
				UserHouse userHouse = userHouseService.getUserHouseById(user.getUserId());
				if(userHouse!=null) matchUserDetailInfo.setMatchUserHouse(userHouse);
			}
			else matchUserDetailInfo.setHasHouse(false);
		}
		
		
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

}
