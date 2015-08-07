package com.netease.roommates.controller;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.netease.exception.ServiceException;
import com.netease.match.service.impl.MatchDetailService;
import com.netease.user.service.IUserInfoService;

public class MatchDetailServiceTest {
	@InjectMocks
	private MatchDetailService matchDetailService;

	@Mock
	private IUserInfoService userInfoService;
	
	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testgetDetailByUser1() throws ServiceException{
		matchDetailService.getDetailByUser(29);
	}
	
	@Test
	public void testgetDetailByUser2() throws ServiceException{
		matchDetailService.getDetailByUser(29,10);
	}
}
