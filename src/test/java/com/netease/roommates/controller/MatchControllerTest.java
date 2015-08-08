package com.netease.roommates.controller;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.netease.exception.ServiceException;
import com.netease.match.service.IMatchDataService;
import com.netease.match.service.IMatchDetailService;

public class MatchControllerTest {

	@InjectMocks
	private MatchController matchController;
	@Mock
	private IMatchDataService matchDataService;
	@Mock
	private IMatchDetailService matchDetailService;
	
	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testmatchPeopleList() throws ServiceException{
		matchController.matchPeopleList(29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
	}
	
	@Test
	public void testmatchPeopleDetail() throws ServiceException{
		matchController.matchPeopleDetail(null, 29);
	}
	
	@Test
	public void testsearchPeopleList() throws ServiceException{
		matchController.searchPeopleList("hello", 78, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
	}
}
