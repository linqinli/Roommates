package com.netease.roommates.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

//Typical junit examples are given as follows,
//and testNG framework is another alternative.
public class UserControllerTest {
	private UserController userController;
	
	@Before
	public void setUp() { 
		userController = new UserController();
	}
	@Test
	public void testHello() {
		assertEquals("index", userController.hello());
	}
	public void testGetUserById() {
		
	}
}
