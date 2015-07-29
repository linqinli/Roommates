package com.netease.roommates.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.exception.ServiceException;
import com.netease.match.service.impl.MatchPersonality;
import com.netease.roommates.po.User;
import com.netease.roommates.po.UserHouse;
import com.netease.roommates.vo.MatchUserDetailInfo;
import com.netease.roommates.vo.MatchUserSimpleInfo;
import com.netease.user.service.IUserHouseService;
import com.netease.user.service.IUserInfoService;

@Controller
@RequestMapping("/api")
public class MatchController {
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private IUserHouseService userHouseService;
	
	@RequestMapping(value = "/people/list")
	@ResponseBody
	public Map matchPeopleList(@RequestParam("id")int id,
			@RequestParam(value="p", defaultValue="1")int page,
			@RequestParam(value="xb", defaultValue="1")int xb,
			@RequestParam(value="f", defaultValue="1")int f,
			@RequestParam(value="gs", defaultValue="1")int gs,
			@RequestParam(value="cy", defaultValue="1")int cy,
			@RequestParam(value="cw", defaultValue="1")int cw, 
			@RequestParam(value="zx", defaultValue="1")int zx,
			@RequestParam(value="ws", defaultValue="1")int ws,
			@RequestParam(value="xg", defaultValue="1")int xg,
			@RequestParam(value="fk", defaultValue="1")int fk) throws ServiceException {
		User user = userInfoService.getUserById(id);
		MatchPersonality matchPersonality = new MatchPersonality(user);
		// matchPernality.selectUserByCondition(xb, f, gs, cy, cw, zx, ws, xg, fk);
		String sqlString = matchPersonality.generateSqlStringByCondition(xb, f, gs, cy, cw, zx, ws, xg, fk);
		
		// List<Integer> userIdList = matchPersonality.selectUserIdByCondition(xb, f, gs, cy, cw, zx, ws, xg, fk);
		List<Integer> userIdList = (List<Integer>)jdbcTemplate.queryForList(sqlString, Integer.class);;
		
		List<User> users = new ArrayList<User>();
		for( int i=0; i<userIdList.size(); ++i){
			User tempUser = userInfoService.getUserById(userIdList.get(i));
			users.add(tempUser);
		}
		List<MatchUserSimpleInfo> userMatchList = matchPersonality.matchResultSimpleInfo(users);
		
		Map resultMap = new HashMap<String, Object >();
		
		resultMap.put("data", userMatchList);
		resultMap.put("errno", 0);
		return resultMap;//matchPernality.matchResultTest();
	}
	
	@RequestMapping(value = "/people/all")
	@ResponseBody
	public List showAllPeople() throws ServiceException {
		List userList = jdbcTemplate.queryForList("select * from user_personality");
		return userList;//matchPernality.matchResultTest();
	}
	
	@RequestMapping(value = "/people/detail/{id}")
	@ResponseBody
	public Map matchPeopleList(@PathVariable int id) throws ServiceException {
		User user = userInfoService.getUserById(id);
		MatchUserDetailInfo matchUserDetailInfo =  new MatchUserDetailInfo();
		
		matchUserDetailInfo.setUserId(user.getUserId());
		matchUserDetailInfo.setPhotoId(user.getUserId(), 0);
		matchUserDetailInfo.setNickName(user.getNickName());
		matchUserDetailInfo.setGender(user.getGender());
		matchUserDetailInfo.setCompany(user.getCompany());
		matchUserDetailInfo.setTel(user.getPhoneNumber());
		if(user.getPersonality()!=null){
			if(user.getPersonality().getHasHouse()==1){
				matchUserDetailInfo.setHasHouse(true);
				UserHouse userHouse = userHouseService.getUserHouseById(user.getUserId());
				matchUserDetailInfo.setHouse(userHouse);
			}
			else matchUserDetailInfo.setHasHouse(true);
		}
		
		Map matchDetailMap = new HashMap<String, Object>();
		matchDetailMap.put("data", matchUserDetailInfo);
		matchDetailMap.put("errno", 0);
		
		return matchDetailMap;//matchPernality.matchResultTest();
	}

	
}
