package com.netease.roommates.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.exception.ServiceException;
import com.netease.match.service.IMatchDataService;
import com.netease.match.service.IMatchDetailService;
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
	private IMatchDataService matchDataService;
	@Autowired
	private IMatchDetailService matchDetailService;
	private final static String USER_ID = "userId";
	
	@RequestMapping(value = "/people/list")
	@ResponseBody
	public Map matchPeopleList(@RequestParam("id")int id,
			@RequestParam(value="p", defaultValue="1")int p,
			@RequestParam(value="xb", defaultValue="1")int xb,
			@RequestParam(value="f", defaultValue="1")int f,
			@RequestParam(value="gs", defaultValue="1")int gs,
			@RequestParam(value="cy", defaultValue="1")int cy,
			@RequestParam(value="cw", defaultValue="1")int cw, 
			@RequestParam(value="zx", defaultValue="1")int zx,
			@RequestParam(value="ws", defaultValue="1")int ws,
			@RequestParam(value="xg", defaultValue="1")int xg,
			@RequestParam(value="fk", defaultValue="1")int fk) throws ServiceException {
		
		Map resultMap = new HashMap<String, Object >();
		
		List<MatchUserSimpleInfo> resultUserInfo = new ArrayList<MatchUserSimpleInfo>();
		
		resultUserInfo = matchDataService.getMatchUserSimpleInfoByPara(id, p, xb, f, gs, cy, cw, zx, ws, xg, fk);
		
		resultMap.put("data", resultUserInfo);
		resultMap.put("errno", 0);
		return resultMap;//matchPernality.matchResultTest();
	}
	
	@RequestMapping(value = "/people/all")
	@ResponseBody
	public List showAllPeople() throws ServiceException {
		return matchDataService.selectAllUsers();//matchPernality.matchResultTest();
	}
	
	@RequestMapping(value = "/people/detail/{id}")
	@ResponseBody
	public Map matchPeopleList(HttpSession session, @PathVariable int id) throws ServiceException {
		// int curUserId = (Integer) session.getAttribute(USER_ID);
		
		MatchUserDetailInfo matchUserDetailInfo =  matchDetailService.getDetailByUser(id);	
		Map matchDetailMap = new HashMap<String, Object>();
		matchDetailMap.put("data", matchUserDetailInfo);
		matchDetailMap.put("errno", 0);
		
		return matchDetailMap;//matchPernality.matchResultTest();
	}

	
}
