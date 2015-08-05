package com.netease.utils;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

import com.netease.common.service.impl.CheckWord;

public class CheckWordTest {
	@Test
	public void testCheckIll() throws Exception {
		assertEquals(CheckWord.check("select"), true);
	}
	
	@Test
	public void testCheck() throws Exception {
		assertEquals(CheckWord.check("test"), false);
	}
}