package com.netease.roommates.controller;

import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.netease.roommates.po.User;
import com.netease.roommates.vo.LoginAndRegisterUserVO;
import com.netease.user.service.IUserInfoService;
import com.netease.utils.HashGeneratorUtils;

public class RegisterControllerTest {
	@InjectMocks
	private RegisterController registerController;

	@Mock
	private IUserInfoService userInfoService;

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCheck() throws Exception{
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpSession session = mock(HttpSession.class);
		when( request.getParameter("userId")).thenReturn("1");
		when(request.getSession()).thenReturn(session);
		when(session.getId()).thenReturn("1");
		when(session.getAttribute("userId")).thenReturn(1);
		User user = new User();
		user.setCompanyEmail("hztest@corp.netease.com");
		when(userInfoService.getUserById(1)).thenReturn(user);
		Map<String, Object> result = registerController.check(request);
		assertEquals(result.get("result"), 1);
	}
	
	
	@Test
	public void testUserCheck() throws Exception{
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter("checkid")).thenReturn("1");
		when(request.getParameter("checkname")).thenReturn(HashGeneratorUtils.generateSaltMD5("test"));
		when(request.getParameter("checkemail")).thenReturn("hztest@corp.netease.com");
		User user = new User();
		user.setNickName("test");
		when(userInfoService.getUserById(1)).thenReturn(user);
		Map<String, Object> result = registerController.userCheck(request);
		assertEquals(result.toString(),"{验证结果=邮箱验证成功}");
	}
	
	
	@Test
	public void testRegister() throws Exception{
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpSession session = mock(HttpSession.class);
		LoginAndRegisterUserVO g_user = new LoginAndRegisterUserVO();
		String t_name = "test";
		String t_email = "hztest@corp.netease.com";
		String t_password = "123456";
		g_user.setNickname(t_name);
		g_user.setEmail(t_email);
		g_user.setPassword(t_password);
		User user = new User();
		user.setNickName(t_name);
		user.setPwdMD5Hash(HashGeneratorUtils.generateSaltMD5(t_password));
		user.setCompany("网易");
		user.setUserId(1);
		when(userInfoService.getUserByEmail(t_email)).thenReturn(null);
		List<User> existList = new ArrayList<User>();
		existList.add(user);
		when(userInfoService.getUserByName(t_name)).thenReturn(existList);
		when(request.getSession()).thenReturn(session);
		Map<String, Object> result = registerController.register(request, g_user);
		assertEquals(result.get("result"), 1);
	}
}
