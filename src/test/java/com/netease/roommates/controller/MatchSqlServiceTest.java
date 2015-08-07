package com.netease.roommates.controller;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.netease.match.service.impl.MatchSqlService;

public class MatchSqlServiceTest {
	@InjectMocks
	private MatchSqlService matchSqlService;
	
	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testgenerateSqlStrByCondition(){
		matchSqlService.generateSqlStrByCondition(78, 1, 1, 1, 2, 1, 1, 1, 1, 1);
	}
	
	@Test
	public void testgenerateXbSql(){
		matchSqlService.generateXbSql(2);
	}
}
