package com.netease.roommates.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.netease.exception.ServiceException;
import com.netease.roommates.po.BatchPhotoModel;
import com.netease.roommates.po.UserHouse;
import com.netease.user.service.IUserHouseService;

public class UserHouseControllerTest {
	
	@InjectMocks
	private UserHouseController uhouseController;

	@Mock
	private IUserHouseService uhouseService;
	
	int userId = 1;
	
	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetUserHouseById() throws ServiceException {
		
		UserHouse uhouse = new UserHouse();
		
		when(uhouseService.getUserHouseById(userId)).thenReturn(uhouse);
		
		uhouseController.getUserHouseById(userId);
		//验证方法是否执行
		verify(uhouseService).getUserHouseById(userId);
	}
	
	@Test
	public void testInsertUserHouse() throws ServiceException {
		
		BatchPhotoModel model = new BatchPhotoModel();
		model.setUserId(userId);
		
//		UserHouse uhouse = new UserHouse();
		
		HttpSession session = mock(HttpSession.class);
		
		uhouseController.insertUserHouse(session, model);
		
//		verify(uhouseService).insertUserHouse(uhouse);
	}
	
	@Test
	public void testUpdateUserHouse() throws ServiceException {
		BatchPhotoModel model = new BatchPhotoModel();
		model.setUserId(userId);
		
		HttpSession session = mock(HttpSession.class);
		
		uhouseController.updateUserHouse(session, model);
	}
	
	@Test
	public void testDeleteUserHouse() throws ServiceException {
		
		when(uhouseService.deleteUserHouse(userId)).thenReturn(true);
		
		Map<String, String> result = uhouseController.deleteUserHouse(userId);
		
		verify(uhouseService).deleteUserHouse(userId);
		
		assertEquals(result.toString(), "{errno=0}");
	}
}