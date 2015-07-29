package com.netease.roommates.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.netease.exception.ControllerException;
import com.netease.exception.ServiceException;
import com.netease.match.service.impl.MatchPersonality;
import com.netease.roommates.po.Personality;
import com.netease.roommates.po.User;
import com.netease.roommates.vo.MatchUserInfo;
import com.netease.roommates.vo.QuestionnaireVO;
import com.netease.roommates.vo.UserBasicInfoVO;
import com.netease.user.service.IUserInfoService;
import com.netease.utils.JsonBuilder;

@Controller
@RequestMapping("/api/user")
public class UserController {
	private static final String USER_ID = "userId";
	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private IUserInfoService userInfoService;

	@RequestMapping(value = "/getUser", method = RequestMethod.GET)
	@ResponseBody
	public User getUserById(int userId) {
		try {
			User user = userInfoService.getUserById(userId);
			return user;
		} catch (ServiceException se) {
			return null;
		} 
	}

	@RequestMapping(value = "/updateUserBasicInfo", method = RequestMethod.POST)
	@ResponseBody
	public String updateUserBasicInfo(HttpServletRequest request, @RequestBody UserBasicInfoVO userBasicInfoVO)
			throws ControllerException {
		request.setAttribute(USER_ID, 1);
		try {
			JsonBuilder result = new JsonBuilder();
			User user = new User();
			user.setUserId((Integer) request.getAttribute(USER_ID));
			userBasicInfoVO.populateUser(user);
			userInfoService.updateUserBasicInfo(user);
			result.append("errno", 0);
			return result.build();
		} catch (ServiceException se) {
			logger.error("error updating basic info for target user, userId:" + request.getAttribute(USER_ID), se);
			throw new ControllerException("error updating basic info for target user", se);
		}
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

	@RequestMapping(value = "/quiz", method = RequestMethod.POST)
	@ResponseBody
	public String addUserPersonality(HttpServletRequest request, @RequestBody QuestionnaireVO questionnaireVO)
			throws ControllerException, JsonProcessingException {
		request.setAttribute(USER_ID, 1);
		try {
			JsonBuilder result = new JsonBuilder();
			Personality personality = new Personality();
			personality.setUserId((Integer) request.getAttribute(USER_ID));
			questionnaireVO.populatePersonality(personality);
			userInfoService.insertUserPersonality(personality);
			result.append("errno", 0);
			return result.build();
		} catch (ServiceException se) {
			logger.error("error add user personality", se);
			throw new ControllerException("Can not upload questionnaire", se);
		}
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

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public String handleError(HttpServletRequest req, Exception exception) {
		logger.error("Request: " + req.getRequestURL() + " raised " + exception);
		JsonBuilder result = new JsonBuilder();
		result.append("errno", 1).append("message", exception.getMessage());
		return result.build();
	}
}
