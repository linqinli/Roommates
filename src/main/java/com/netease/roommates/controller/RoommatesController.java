package com.netease.roommates.controller;

import java.util.List;
import java.util.Map;

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

import com.netease.exception.ControllerException;
import com.netease.roommates.vo.MatchUserSimpleInfo;
import com.netease.user.service.IRoommatesService;
import com.netease.utils.JsonBuilder;

@Controller
@RequestMapping(value = "/api/people", produces = "application/json;charset=UTF-8")
public class RoommatesController {
	private Logger logger = LoggerFactory.getLogger(RoommatesController.class);
	private final static String USER_ID = "userId";

	@Autowired
	private IRoommatesService roommatesService;

	@ResponseBody
	@RequestMapping(value = "/forbid", method = RequestMethod.POST)
	public String addHate(HttpSession session, @RequestBody Map<String, String> params) throws ControllerException {
		int userId = (Integer) session.getAttribute(USER_ID);
		String forbidId = params.get("forbidId");
		if (forbidId == null) {
			throw new ControllerException("Required field forbidId is not present.");
		}
		int forId = Integer.valueOf(forbidId);
		roommatesService.removeFavorite(userId, forId);
		roommatesService.addHate(userId, forId);
		JsonBuilder result = new JsonBuilder();
		return result.append("errno", 0).build();
	}

	@ResponseBody
	@RequestMapping(value = "/fav", method = RequestMethod.GET)
	public String getAllFavorite(HttpSession session) {
		int userId = (Integer) session.getAttribute(USER_ID);
		List<MatchUserSimpleInfo> favorites = roommatesService.getAllFavorite(userId);
		JsonBuilder result = new JsonBuilder();
		result.append("errno", 0);
		result.append("data", favorites);
		return result.build();
	}

	@ResponseBody
	@RequestMapping(value = "/fav", method = RequestMethod.POST)
	public String addFavorite(HttpSession session, @RequestBody Map<String, String> params) throws ControllerException {
		int userId = (Integer) session.getAttribute(USER_ID);
		String favId = params.get("favId");
		if (favId == null) {
			throw new ControllerException("Required field favId is not present.");
		}
		roommatesService.addFavorite(userId, Integer.valueOf(favId));
		JsonBuilder result = new JsonBuilder();
		return result.append("errno", 0).build();
	}

	@ResponseBody
	@RequestMapping(value = "/fav/del", method = RequestMethod.POST)
	public String removeFavorite(HttpSession session, @RequestBody Map<String, String> params)
			throws ControllerException {
		int userId = (Integer) session.getAttribute(USER_ID);
		String favId = params.get("favId");
		if (favId == null) {
			throw new ControllerException("Required field favId is not present.");
		}
		roommatesService.removeFavorite(userId, Integer.valueOf(favId));
		JsonBuilder result = new JsonBuilder();
		return result.append("errno", 0).build();
	}

	@ResponseBody
	@ExceptionHandler(Exception.class)
	public String handleError(HttpServletRequest req, Exception exception) {
		logger.error("Request: " + req.getRequestURL() + " raised " + exception);
		JsonBuilder result = new JsonBuilder();
		result.append("errno", 1).append("message", exception.getMessage());
		return result.build();
	}
}
