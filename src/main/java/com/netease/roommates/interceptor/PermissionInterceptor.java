package com.netease.roommates.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class PermissionInterceptor extends HandlerInterceptorAdapter {
	private Logger logger = LoggerFactory.getLogger(PermissionInterceptor.class);
	private final static String USER_ID = "userId";

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String path = request.getServletPath();
		if (path.startsWith("/api/login") || path.startsWith("/api/register")) {
			return true;
		}
		if(request.getSession().getAttribute(USER_ID) != null) {
			return true;
		}
		writeForbiddenInfo(request, response);
		return false;
	}

	private void writeForbiddenInfo(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html;charset=utf-8");
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "用户未登录");
		} catch (IOException ioe) {
			logger.error("", ioe);
		}
	}
    
}
