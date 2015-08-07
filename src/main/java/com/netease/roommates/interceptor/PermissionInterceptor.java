package com.netease.roommates.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class PermissionInterceptor extends HandlerInterceptorAdapter {
	private Logger logger = LoggerFactory.getLogger(PermissionInterceptor.class);
	private final static String USER_ID = "userId";
	private static Map<String, Integer> tokenUserMap;

	static {
		tokenUserMap = new HashMap<String, Integer>();
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String path = request.getServletPath();
		if (path.startsWith("/api/login") || path.startsWith("/api/register")) {
			return true;
		}
		String token = request.getHeader("Authorization");
		if (!StringUtils.isEmpty(token)) {
			if (tokenUserMap.containsKey(token)) {
				HttpSession session = request.getSession();
				int userId = tokenUserMap.get(token);
				session.setAttribute(USER_ID, userId);
				return true;
			}
		}
		writeForbiddenInfo(request, response);
		return false;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		Object userId = request.getSession().getAttribute(USER_ID);
		if (userId != null && !tokenUserMap.containsValue(userId)) {
			tokenUserMap.put(request.getSession().getId(), (Integer) userId);
		}
	}

	private void writeForbiddenInfo(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html;charset=utf-8");
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "用户未登录");
		} catch (IOException ioe) {
			logger.error("", ioe);
		}
	}
    
	public static void removeToken(String token) {
		tokenUserMap.remove(token);
	}
	
	public static void removeUserId(int userId) {
		if(tokenUserMap.containsValue(userId)) {
			for(String token : tokenUserMap.keySet()) {
				if(tokenUserMap.get(token).equals(userId)) {
					tokenUserMap.remove(token);
					break;
				}
			}
		}
	}
}
