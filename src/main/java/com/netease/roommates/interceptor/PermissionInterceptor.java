package com.netease.roommates.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class PermissionInterceptor extends HandlerInterceptorAdapter {
	private Logger logger = LoggerFactory.getLogger(PermissionInterceptor.class);
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String path = request.getServletPath();
		if(path.startsWith("/api/login") || path.startsWith("/api/register")) {
			return true;
		}
		return checkLogin(request, response);
	}

	private boolean checkLogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (request.getSession().getAttribute("userId") == null) {
				response.setContentType("text/html;charset=utf-8");
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "用户未登录");
				return false;
			}
		} catch (IOException ioe) {
			logger.error("", ioe);
		}
		return true;
	}

}
