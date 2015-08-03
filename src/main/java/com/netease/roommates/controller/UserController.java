package com.netease.roommates.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.exception.ControllerException;
import com.netease.exception.ServiceException;
import com.netease.roommates.po.Personality;
import com.netease.roommates.po.User;
import com.netease.roommates.vo.QuestionnaireVO;
import com.netease.roommates.vo.TagVO;
import com.netease.roommates.vo.UserBasicInfoVO;
import com.netease.user.service.IUserInfoService;
import com.netease.utils.JsonBuilder;

@Controller
@RequestMapping(value = "/api/user", produces = "application/json;charset=UTF-8")
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
		try {
			int userId = (Integer) session.getAttribute(USER_ID);
			logger.info("User id:" + userId + " " + userBasicInfoVO);
			JsonBuilder result = new JsonBuilder();
			User user = new User();
			user.setUserId(userId);
			userBasicInfoVO.populateUser(user);
			userInfoService.updateUserBasicInfo(user);
			user = userInfoService.getUserById(userId);
			result.append("errno", 0);
			result.append("finishInfo", checkUserInfoCompletion(user));
			return result.build();
		} catch (ServiceException se) {
			logger.error("error updating basic info for target user, userId:" + session.getAttribute(USER_ID), se);
			throw new ControllerException("error updating basic info for target user", se);
		}
	}

	private int checkUserInfoCompletion(User user) {
		if (!StringUtils.isEmpty(user.getNickName()) && !StringUtils.isEmpty(user.getCompany())
				&& !StringUtils.isEmpty(user.getPosition()) && !StringUtils.isEmpty(user.getPhoneNumber())
				&& user.getGender() != null && user.getBirthday() != null) {
			return 1;
		}
		return 0;
	}

	@ResponseBody
	@RequestMapping(value = "/getUserPersonality", method = RequestMethod.GET)
	public Personality getUserPersonalityById(int id) throws ServiceException {
		return userInfoService.getUserPersonalityById(id);
	}

	@ResponseBody
	@RequestMapping(value = "/quiz", method = RequestMethod.POST)
	public String addUserPersonality(HttpSession session, @RequestBody QuestionnaireVO questionnaireVO)
			throws ControllerException {
		try {
			int userId = (Integer) session.getAttribute(USER_ID);
			JsonBuilder result = new JsonBuilder();
			Personality personality = new Personality();
			personality.setUserId(userId);
			questionnaireVO.populatePersonality(personality);
			User user = userInfoService.getUserById(userId);
			if (user.getPersonality() == null) {
				userInfoService.insertUserPersonality(personality);
			} else {
				userInfoService.updateUserPersonality(personality);
			}
			result.append("errno", 0);
			return result.build();
		} catch (ServiceException se) {
			logger.error("error add user personality", se);
			throw new ControllerException("Can not upload questionnaire", se);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getQuiz", method = RequestMethod.GET)
	public TagVO getUserPersonality(HttpSession session) throws ControllerException {
		try {
			int userId = (Integer) session.getAttribute(USER_ID);
			Personality personality = userInfoService.getUserPersonalityById(userId);
			return new TagVO(personality);
		} catch (ServiceException e) {
			logger.error("Error getting user quiz.", e);
			throw new ControllerException("Error getting user quiz.", e);
		}

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
