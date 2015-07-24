package com.netease.roommates.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.exception.ServiceException;
import com.netease.roommates.po.User;
import com.netease.roommates.service.IUserInfoService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private IUserInfoService userInfoService;

	@RequestMapping(value = "/getUserById", method = RequestMethod.GET)
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
	public User matchable() throws ServiceException {
		User user = new User();
		user.setAddress("hangzhou");
		user.setCompany("NetEase");
		return user;
	}
}
