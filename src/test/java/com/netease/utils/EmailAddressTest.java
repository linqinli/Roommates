package com.netease.utils;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

import com.netease.common.service.impl.emailAddress;

public class EmailAddressTest {
	@Test
	public void testEmailCheck() throws Exception {
		assertEquals(emailAddress.emailCheck("hztest@corp.netease.com"), true);
	}
	
	@Test
	public void testGetCompany() throws Exception {
		assertEquals(emailAddress.getCompany("hztest@corp.netease.com"), "网易");
	}
}