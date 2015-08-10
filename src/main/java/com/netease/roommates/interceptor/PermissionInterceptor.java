package com.netease.roommates.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class PermissionInterceptor extends HandlerInterceptorAdapter {
	private Logger logger = LoggerFactory.getLogger(PermissionInterceptor.class);
	private final static String USER_ID = "userId";
	private Map<Integer, String> userTokenMapper;

	{
		userTokenMapper = new HashMap<Integer, String>();
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String path = request.getServletPath();
		HttpSession session = request.getSession();
		if (path.startsWith("/api/login") || path.startsWith("/api/register")) {
			session.removeAttribute(USER_ID);
			return true;
		}

		if (session.getAttribute(USER_ID) != null) {
			int userId = (Integer) session.getAttribute(USER_ID);
			if (userTokenMapper.containsKey(userId) && userTokenMapper.get(userId).equals(session.getId())) {
				return true;
			}
			session.invalidate();
			logger.info("Session id {} of userId {} has been invalidated.", session.getId(), userId);
		}
		writeForbiddenInfo(request, response);
		return false;
	}

	private void writeForbiddenInfo(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT");
			response.setHeader("Access-Control-Allow-Headers",
					"Origin, X-Requested-With, Content-Type, Accept, Authorization, If-Modified-Since");
			response.setContentType("text/html;charset=utf-8");
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "用户未登录");
		} catch (IOException ioe) {
			logger.error("", ioe);
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		String path = request.getServletPath();
		Object userIdObj = request.getSession().getAttribute(USER_ID);
		if (path.startsWith("/api/login") && userIdObj != null) {
			int userId = Integer.valueOf(userIdObj.toString());
			userTokenMapper.put(userId, request.getSession().getId());
		}
	}
}
