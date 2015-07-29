package com.netease.utils;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

import com.netease.exception.HashGenerationException;

public class HashGeneratorUtilsTest {
	@Test
	public void testGenerateMD5() throws HashGenerationException {
		assertEquals(HashGeneratorUtils.generateMD5("12345"), HashGeneratorUtils.generateMD5("12345"));
	}

	@Test
	public void testGenerateSaltMD5() throws HashGenerationException {
		assertEquals(HashGeneratorUtils.generateSaltMD5("12345", "roommates_salt_2&g3(*EH"), HashGeneratorUtils.generateSaltMD5("12345"));
	}
	
	@Test
	public void testGenerateSHA1() throws HashGenerationException {
		assertEquals(HashGeneratorUtils.generateSHA1("12345"), HashGeneratorUtils.generateSHA1("12345"));
	}
	
	@Test
	public void testGenerateSHA256() throws HashGenerationException {
		assertEquals(HashGeneratorUtils.generateSHA256("12345"), HashGeneratorUtils.generateSHA256("12345"));
	}
}