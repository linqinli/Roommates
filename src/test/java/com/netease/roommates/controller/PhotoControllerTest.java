package com.netease.roommates.controller;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.netease.exception.ControllerException;
import com.netease.user.service.IFileService;

public class PhotoControllerTest {
	
	@InjectMocks
	private PhotoController photoController;
	
	@Mock
	private IFileService fileService;
	
	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testUpload() throws ControllerException {
		HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("userId")).thenReturn(1);
		photoController.upload(session, new HashMap<String, String>());
	}
	
	@Test
	public void testDownload() throws IOException {
		ResponseEntity<byte[]> res = new ResponseEntity<byte[]>(HttpStatus.ACCEPTED);
		when(fileService.fileDownload("/var/www/lighttpd/Roommates/photo/photo_123.jpg")).thenReturn(res);
		photoController.downloadBigImage(123);
		//assertEquals(photoController.downloadBigImage(123), null);
		//verify(fileService.fileDownload("/var/www/lighttpd/Roommates/photo/photo_123.jpg"));
	}
}
