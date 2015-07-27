package com.netease.roommates.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.exception.ServiceException;
import com.netease.roommates.match.MatchPersonality;
import com.netease.roommates.po.Personality;
import com.netease.roommates.po.User;
import com.netease.roommates.vo.MatchUserInfo;
import com.netease.user.service.IUserInfoService;

@Controller
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private IUserInfoService userInfoService;
	
	@RequestMapping(value = "/getUser", method = RequestMethod.GET)
	@ResponseBody
	public User getUserById(int id) throws ServiceException {
		User user = userInfoService.getUserById(id);
		System.out.println(user);
		return user;
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public void addUser(@RequestBody User user) throws ServiceException {
		userInfoService.insertUser(user);
	}

	@RequestMapping(value = "/updateUserBasicInfo", method = RequestMethod.POST)
	public void updateUser(@RequestBody User user) throws ServiceException {
		userInfoService.updateUserBasicInfo(user);
	}
	
	@RequestMapping(value = "/matchable")
	@ResponseBody
	public List<MatchUserInfo> matchable() throws ServiceException {
		User user = new User();
		user.setAddress("hangzhou");
		user.setCompany("NetEase");
		MatchPersonality matchPernality = new MatchPersonality(user);
		return matchPernality.matchResultTest();
	}
	
	@RequestMapping(value = "/getUserPersonality", method = RequestMethod.GET)
	@ResponseBody
	public Personality getUserPersonalityById(int id) throws ServiceException {
		return userInfoService.getUserPersonality(id);
	}

	@RequestMapping(value = "/addUserPersonality", method = RequestMethod.POST)
	public void addUserPersonality(@RequestBody Personality personality) throws ServiceException {
		userInfoService.insertUserPersonality(personality);
	}

	@RequestMapping(value = "/updateUserPersonality", method = RequestMethod.POST)
	public void updateUserPersonality(@RequestBody Personality personality) throws ServiceException {
		userInfoService.updateUserPersonality(personality);
	}
	
	@RequestMapping(value = "/getUserListByAddress", method = RequestMethod.GET)
	@ResponseBody
	public List<User> getUserListByAddress(String address) throws ServiceException, UnsupportedEncodingException {
		address = new String(address.getBytes("ISO-8859-1"), "UTF-8");
		return userInfoService.getUserListByAddress(address);
	}
}
