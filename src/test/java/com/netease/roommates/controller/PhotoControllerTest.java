package com.netease.roommates.controller;

import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;
import org.testng.annotations.BeforeClass;

import com.netease.user.service.FileService;

public class PhotoControllerTest {
	
	@InjectMocks
	private PhotoController photoController;
	
	@Mock
	private FileService fileService;
	
	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	public void testUpload() {
		MultipartFile file = mock(MultipartFile.class);
		
	}
}
