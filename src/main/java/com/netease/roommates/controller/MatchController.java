package com.netease.roommates.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.exception.ServiceException;
import com.netease.match.service.impl.MatchDataHandler;
import com.netease.match.service.impl.MatchPersonality;
import com.netease.roommates.po.User;
import com.netease.roommates.vo.MatchUserInfo;
import com.netease.user.service.IUserInfoService;

@Controller
@RequestMapping("/api/match")
public class MatchController {
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private MatchDataHandler matchDataHandler;
	
	@RequestMapping(value = "/test")
	@ResponseBody
	public List<MatchUserInfo> matchable() throws ServiceException {
		User user = new User();
		user.setAddress("hangzhou");
		user.setCompany("NetEase");
		MatchPersonality matchPernality = new MatchPersonality(user);
		return matchPernality.matchResultTest();
	}
	
	@RequestMapping(value = "/people/list")
	@ResponseBody
	public User matchPeopleList(@RequestParam("id")int id,
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
		if(user.getPersonality()==null){
			MatchPersonality matchPernality = new MatchPersonality(user);
			// matchPernality.selectUserByCondition(xb, f, gs, cy, cw, zx, ws, xg, fk);
			// matchPernality.selectUserByCondition(xb, f, gs, cy, cw, zx, ws, xg, fk);
			matchDataHandler = new MatchDataHandler();
			matchDataHandler.selectUserByCondition(xb, f, gs, cy, cw, zx, ws, xg, fk);
		}
		
		return user;//matchPernality.matchResultTest();
	}
	
}
