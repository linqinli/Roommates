package com.netease.roommates.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.netease.user.service.FileService;

@Controller
@RequestMapping("/api/photo")
public class PhotoController {
	private Logger logger = LoggerFactory.getLogger(PhotoController.class);
	private final static String PREFIX = "/var/www/lighttpd/Roommates/photo/photo";
	private final static String SUFFIX = ".jpg";
	@Autowired
	FileService photoTransportService;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> upload(int userId, MultipartFile file) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			if (!file.isEmpty()) {
				byte[] bytes = file.getBytes();
				photoTransportService.fildUpload(generatePathByUserId(userId), bytes);
				result.put("state", "success");
				return result;
			}
		} catch (IOException ioe) {
			logger.error("Error uploading photo, userId:" + userId, ioe);
		}
		result.put("state", "failure");
		return result;
	}

	@RequestMapping("/downloadBig")
	@ResponseBody
	public ResponseEntity<byte[]> downloadBigImage(int userId) {
		try {
			String path = generatePathByUserId(userId);
			if (new File(path).exists()) {
				return photoTransportService.fileDownload(path);
			} else {
				//TODO
			}
		} catch (FileNotFoundException fnfe) {
			logger.warn("photo not found fot target user, userId" + userId, fnfe);
		} catch (IOException ioe) {
			logger.error("IOException when download photo for user, userId=" + userId, ioe);
		}
		return null;
	}
	
	@RequestMapping("/downloadSmall")
	@ResponseBody
	public ResponseEntity<byte[]> downloadSmallImage(int userId) {
		try {
			String path = generateSPICPathByUserId(userId);
			if (new File(path).exists()) {
				return photoTransportService.fileDownload(path);
			} else {
				//TODO
			}
		} catch (FileNotFoundException fnfe) {
			logger.warn("photo not found fot target user, userId" + userId, fnfe);
		} catch (IOException ioe) {
			logger.error("IOException when download photo for user, userId=" + userId, ioe);
		}
		return null;
	}
	
	private String generatePathByUserId(int userId) {
		return PREFIX + '_' +  userId + SUFFIX;
	}
	
	private String generateSPICPathByUserId(int userId) {
		return PREFIX + '_' + userId + '_'+ "small" + SUFFIX;
	}
}
