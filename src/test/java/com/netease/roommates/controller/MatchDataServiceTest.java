package com.netease.roommates.controller;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.netease.exception.ServiceException;
import com.netease.match.service.IMatchSqlService;
import com.netease.match.service.impl.MatchDataService;
import com.netease.user.service.IUserInfoService;

public class MatchDataServiceTest {
	@InjectMocks
	private MatchDataService matchDataService;
	
	@Mock
	private JdbcTemplate jdbcTemplate;
	@Mock
	private IUserInfoService userInfoService;
	@Mock
	private IMatchSqlService matchSqlService;
	
	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testgetMatchUserSimpleInfoByPara() throws ServiceException{
		matchDataService.getMatchUserSimpleInfoByPara(78, 1, 1, 1, 1, 3, 2, 3, 2, 1, 1);
	}
	
}
