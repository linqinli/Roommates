package com.netease.roommates.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.netease.exception.ServiceException;

//Typical junit examples are given as follows,
//and testNG framework is another alternative.
public class UserControllerTest {
	private UserController userController;
	
	@Before
	public void setUp() { 
		userController = new UserController();
	}
	@Test
	public void testHello() throws ServiceException {
		assertEquals("index", "index");
	}
	public void testGetUserById() {
		
	}
}
