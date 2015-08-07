package com.netease.user;

import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.netease.match.service.IMatchDataService;
import com.netease.roommates.mapper.RoommatesMapper;
import com.netease.user.service.impl.RoommatesServiceImpl;

public class RoommatesControllerTest {
	@InjectMocks
	private RoommatesServiceImpl roommatesService;
	
	@Mock
	private RoommatesMapper roommatesMapper;
	
	@Mock
	private IMatchDataService matchDataService;
	
	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testIsFavorite() {
		when(roommatesMapper.isFavorite(1, 2)).thenReturn(true);
		assertEquals(true, roommatesService.isFavorite(1, 2));
	}
}
