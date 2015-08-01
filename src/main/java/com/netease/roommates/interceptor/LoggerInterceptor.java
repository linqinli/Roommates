package com.netease.roommates.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoggerInterceptor extends HandlerInterceptorAdapter {
	private Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// request.getSession().setAttribute("userId", 29);
		request.setAttribute("startTime", System.currentTimeMillis());

		String ip = getRemoteAddress(request);
		String userId = String.valueOf(request.getSession().getAttribute("userId"));
		String url = request.getRequestURI();
		if (logger.isInfoEnabled()) {
			logger.info("{} {} {} Starting", ip, userId, url);
		}
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		long startTime = (Long) request.getAttribute("startTime");
		long executeTime = System.currentTimeMillis() - startTime;
		
		String ip = getRemoteAddress(request);
		String userId = String.valueOf(request.getSession().getAttribute("userId"));
		String url = request.getRequestURI();
		
		if (logger.isInfoEnabled()) {
			logger.info("{} {} {} executeTime: {}ms", ip, userId, url, executeTime);
		}
	}

	private String getRemoteAddress(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (ip == null) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
