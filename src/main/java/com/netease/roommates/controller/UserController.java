package com.netease.roommates.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.netease.roommates.po.Personality;
import com.netease.roommates.po.User;
import com.netease.roommates.vo.QuestionnaireVO;
import com.netease.roommates.vo.UserBasicInfoVO;
import com.netease.user.service.IUserInfoService;
import com.netease.utils.JsonBuilder;

@Controller
@RequestMapping(value="/api/user", produces="application/json;charset=UTF-8")
public class UserController {
	private static final String USER_ID = "userId";
	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private IUserInfoService userInfoService;
	
	@ResponseBody
	@RequestMapping(value = "/getUser", method = RequestMethod.GET)
	public User getUserById(int userId) throws ControllerException {
		try {
			User user = userInfoService.getUserById(userId);
			return user;
		} catch (ServiceException se) {
			throw new ControllerException("Error getting user info.", se);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/updateUserBasicInfo", method = RequestMethod.POST)
	public String updateUserBasicInfo(HttpSession session, @RequestBody UserBasicInfoVO userBasicInfoVO)
			throws ControllerException {
		session.setAttribute(USER_ID, 1);
		try {
			JsonBuilder result = new JsonBuilder();
			User user = new User();
			user.setUserId((Integer) session.getAttribute(USER_ID));
			userBasicInfoVO.populateUser(user);
			userInfoService.updateUserBasicInfo(user);
			result.append("errno", 0);
			return result.build();
		} catch (ServiceException se) {
			logger.error("error updating basic info for target user, userId:" + session.getAttribute(USER_ID), se);
			throw new ControllerException("error updating basic info for target user", se);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/getUserPersonality", method = RequestMethod.GET)
	public Personality getUserPersonalityById(int id) throws ServiceException {
		return userInfoService.getUserPersonality(id);
	}
	
	@ResponseBody
	@RequestMapping(value = "/quiz", method = RequestMethod.POST)
	public String addUserPersonality(HttpSession session, @RequestBody QuestionnaireVO questionnaireVO)
			throws ControllerException, JsonProcessingException {
		session.setAttribute(USER_ID, 1);
		try {
			JsonBuilder result = new JsonBuilder();
			Personality personality = new Personality();
			personality.setUserId((Integer) session.getAttribute(USER_ID));
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
	
	@ResponseBody
	@RequestMapping(value = "/getUserListByAddress", method = RequestMethod.GET)
	public List<User> getUserListByAddress(String address) throws ServiceException, UnsupportedEncodingException {
		address = new String(address.getBytes("ISO-8859-1"), "UTF-8");
		return userInfoService.getUserListByAddress(address);
	}
	
	@ResponseBody
	@ExceptionHandler(Exception.class)
	public String handleError(HttpServletRequest req, Exception exception) {
		logger.error("Request: " + req.getRequestURL() + " raised " + exception, exception);
		JsonBuilder result = new JsonBuilder();
		result.append("errno", 1).append("message", exception.getMessage());
		return result.build();
	}
}
