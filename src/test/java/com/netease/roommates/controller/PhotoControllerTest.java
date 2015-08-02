package com.netease.roommates.controller;

import static org.mockito.Mockito.*;

import java.io.IOException;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

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
	public void testUpload() {
		MultipartFile file = mock(MultipartFile.class);
		photoController.upload(123, file);
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
