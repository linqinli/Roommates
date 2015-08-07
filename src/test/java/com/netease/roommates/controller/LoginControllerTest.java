package com.netease.roommates.controller;


import static org.testng.Assert.*;

import java.util.Map;

import static org.mockito.Mockito.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.netease.roommates.po.Personality;
import com.netease.roommates.po.User;
import com.netease.roommates.po.UserHouse;
import com.netease.roommates.vo.LoginAndRegisterUserVO;
import com.netease.user.service.IUserInfoService;
import com.netease.user.service.impl.UserHouseService;
import com.netease.utils.HashGeneratorUtils;


public class LoginControllerTest {
	@InjectMocks
	private LoginController loginController;

	@Mock
	private IUserInfoService userInfoService;
	@Mock
	UserHouseService userHouseService;

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testLogout() throws Exception{
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpSession session = mock(HttpSession.class);
		when(request.getSession()).thenReturn(session);
		Map<String, Object> result = loginController.logout(request);
		assertEquals(result.toString(), "{result=1}");
	}
		
	
	@Test
	public void testLogin() throws Exception{
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpSession session = mock(HttpSession.class);
		UserHouse userhouse = new UserHouse();
		LoginAndRegisterUserVO g_user = new LoginAndRegisterUserVO();
		g_user.setEmail("hztest@corp.netease.com");
		g_user.setPassword("123456");
		User user = new User();
		user.setUserId(1);
		user.setPwdMD5Hash(HashGeneratorUtils.generateSaltMD5("123456"));
		user.setHasPhoto(true);
		when(userInfoService.getUserByEmail("hztest@corp.netease.com")).thenReturn(user);
		when(request.getSession()).thenReturn(session);
		when(session.getId()).thenReturn("1");
		Personality personality = new Personality();
		when(userInfoService.getUserPersonalityById(1)).thenReturn(personality);
		when(userHouseService.getUserHouseById(1)).thenReturn(userhouse);
		Map<String, Object> result = loginController.loginCheck(request, g_user);
		assertEquals(result.get("result"),1);
		
	}
}
