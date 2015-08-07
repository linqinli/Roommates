package com.netease.roommates.controller;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.netease.exception.ServiceException;

public class MatchControllerTest {

	@InjectMocks
	private MatchController matchController;
	
	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testmatchPeopleList() throws ServiceException{
		// matchController.matchPeopleList(29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
	}
	
	@Test
	public void testmatchPeopleDetail() throws ServiceException{
		// matchController.matchPeopleDetail(null, 29);
	}
}
