package com.netease.match.service;

import static org.testng.Assert.*;

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
		String str = matchSqlService.generateXbSql(2);
		String result=" s.gender=0 and";
		assertEquals(str, result);
	}
	
	@Test
	public void testgenerateGsSql(){
		String str = matchSqlService.generateGsSql(2);
		String result = " s.company='网易' and";
		assertEquals(str,result);
	}
	
	@Test
	public void testgenerateCySql(){
		String str = matchSqlService.generateCySql(2);
		String result = " p.smoking=2 and";
		assertEquals(str,result);
	}
	
	@Test
	public void testgenerateZxSql(){
		String str = matchSqlService.generateZxSql(2);
		String result = " p.dailySchedule=2 and";
		assertEquals(str,result);
	}
	
	@Test
	public void testgenerateWsSql(){
		String str = matchSqlService.generateWsSql(2);
		String result = " p.cleanliness=2 and";
		assertEquals(str,result);
	}
	
	@Test
	public void testgenerateXgSql(){
		String str = matchSqlService.generateXgSql(2);
		String result = " p.personCharacter=2 and";
		assertEquals(str,result);
	}
	
	@Test
	public void testgenerateFkSql(){
		String str = matchSqlService.generateFkSql(2);
		String result = " p.visitor=2 and";
		assertEquals(str,result);
	}
	
	@Test
	public void testgenerateCwSql(){
		String str = matchSqlService.generateCwSql(2);
		String result = " p.pet=2 and";
		assertEquals(str,result);
	}
}
