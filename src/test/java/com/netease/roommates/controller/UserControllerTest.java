package com.netease.roommates.controller;

import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.netease.exception.ControllerException;
import com.netease.exception.ServiceException;
import com.netease.roommates.po.Personality;
import com.netease.roommates.po.User;
import com.netease.roommates.vo.QuestionnaireVO;
import com.netease.roommates.vo.UserBasicInfoVO;
import com.netease.user.service.IUserInfoService;

public class UserControllerTest {
	@InjectMocks
	private UserController userController;

	@Mock
	private IUserInfoService userInfoService;

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetUserById()
			throws ServiceException, JsonParseException, JsonMappingException, IOException, ControllerException {
		User user = generateUser();
		int userId = user.getUserId();
		when(userInfoService.getUserById(userId)).thenReturn(user);
		User user2 = userController.getUserById(userId);
		verify(userInfoService).getUserById(userId);
		assertEqualUser(user, user2);
	}

	@Test
	public void testGetUserPersonalityByUserId() throws ServiceException {
		Personality personality = new Personality();
		personality.setUserId(1);
		when(userInfoService.getUserPersonalityById(1)).thenReturn(personality);
		Personality personality2 = userController.getUserPersonalityById(1);
		verify(userInfoService).getUserPersonalityById(1);
		assertEquals(personality.getUserId(), personality2.getUserId());
	}

	@Test
	public void testUpdateUserBasicInfo() throws ControllerException, ServiceException {
		User user = generateUser();
		user.setUserId(1);
		HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("userId")).thenReturn(1);
		when(userInfoService.getUserById(user.getUserId())).thenReturn(user);
		String result = userController.updateUserBasicInfo(session, new UserBasicInfoVO());
		assertEquals(result, "{\"errno\":0,\"finishInfo\":0}");
	}

	@Test
	public void testAddUserPersonality() throws JsonProcessingException, ControllerException {
		HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("userId")).thenReturn(1);
		String result = userController.addUserPersonality(session, new QuestionnaireVO());
		assertEquals(result, "{\"errno\":0}");
	}

	private void assertEqualUser(User u1, User u2) {
		assertEquals(u1.getUserId(), u2.getUserId());
		assertEquals(u1.getAddress(), u2.getAddress());
		assertEquals(u1.getCompany(), u2.getCompany());
	}

	private User generateUser() {
		return generateUser(null);
	}

	private User generateUser(String addr) {
		Random rand = new Random();
		int userId = rand.nextInt();
		User user = new User();
		user.setUserId(userId);
		if (addr != null) {
			user.setAddress(addr);
		} else {
			user.setAddress("address" + userId);
		}
		user.setCompany("company" + userId);
		return user;
	}
}
