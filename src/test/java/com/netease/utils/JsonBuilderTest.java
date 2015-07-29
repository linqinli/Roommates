package com.netease.utils;

import static org.testng.Assert.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class JsonBuilderTest {
	private JsonBuilder jsonBuilder;
	
	@BeforeClass
	public void setUp() {
		jsonBuilder = new JsonBuilder();
	}

	@Test
	public void testAppend() {
		jsonBuilder.append("key", "value");
		assertEquals(jsonBuilder.build(), "{\"key\":\"value\"}");
	}
	
	@Test
	public void testBuild() {
		jsonBuilder.append("key", "value");
		assertEquals(jsonBuilder.build(), "{\"key\":\"value\"}");
	}
}
