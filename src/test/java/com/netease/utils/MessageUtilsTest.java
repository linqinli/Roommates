package com.netease.utils;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class MessageUtilsTest {
  @Test
  public void testGetMessage() {
	  assertEquals(MessageUtils.getMessage("INVAITATION"), "合租邀请");
  }
}
