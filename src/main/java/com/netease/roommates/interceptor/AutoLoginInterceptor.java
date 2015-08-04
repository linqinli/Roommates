package com.netease.roommates.interceptor;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.netease.common.service.AutoLoginRedisService;

public class AutoLoginInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private AutoLoginRedisService autoLoginRedisService;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String previousSessionId = null;
		if (request.getCookies() == null) {
			return checkLogin(request, response);
		}
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals("JSESSIONID")) {
				previousSessionId = cookie.getValue();
				break;
			}
		}
		request.setAttribute("previousSessionId", previousSessionId);
		if (!StringUtils.isEmpty(previousSessionId)) {
			String currentSessionId = request.getSession().getId();
			if (!previousSessionId.equals(currentSessionId)) {
				String userId = autoLoginRedisService.getUserIdBySessionId(previousSessionId);
				if (userId != null) {
					HttpSession session = request.getSession();
					session.setAttribute("userId", Integer.parseInt(userId));
					session.setAttribute("isLogin", true);
					autoLoginRedisService.deleteSessionIdAndUserId(previousSessionId);
					// autoLoginRedisService.putSessionIdAndUserId(currentSessionId,
					// userId);
					// response.addCookie(new Cookie("JSESSIONID",
					// currentSessionId));
				}
			}
		}
		if (!checkLogin(request, response)) {
			return false;
		}
		return true;
	}

	private boolean checkLogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			String path = request.getServletPath();
			if(path.startsWith("/api/login") || path.startsWith("/api/register")) {
				return true;
			}
			if (request.getSession().getAttribute("userId") == null) {
				response.setContentType("text/html;charset=utf-8");
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "用户未登录");
				return false;
			}
		} catch (IOException ioe) {
			//TODO
		}
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HttpSession session = request.getSession();
		if (request.getAttribute("previousSessionId") == null
				|| !request.getAttribute("previousSessionId").equals(session.getId())) {
			Object userId = session.getAttribute("userId");
			if (userId != null) {
				String uid = userId.toString();
				autoLoginRedisService.putSessionIdAndUserId(session.getId(), uid);

			}
		}
	}

}
