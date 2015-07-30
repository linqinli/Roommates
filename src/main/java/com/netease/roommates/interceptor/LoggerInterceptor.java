package com.netease.roommates.interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoggerInterceptor extends HandlerInterceptorAdapter {
	private Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		request.getSession().setAttribute("userId", 1);
		request.setAttribute("startTime", System.currentTimeMillis());
		String time = format.format(new Date());
		
		if (logger.isDebugEnabled()) {
			logger.debug("{} {} {}", time, request.getRemoteAddr(), request.getPathTranslated());
		}
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		long startTime = (Long) request.getAttribute("startTime");
		long executeTime = System.currentTimeMillis() - startTime;

		if (logger.isDebugEnabled()) {
			logger.debug("[" + handler + "] executeTime : " + executeTime + "ms");
		}
	}
}
