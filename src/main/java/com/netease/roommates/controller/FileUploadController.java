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
public class FileUploadController {
	private Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	private final static String PREFIX = "D:\\photo\\";
	private final static String SUFFIX = "_photo.jpg";
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
			ioe.printStackTrace();
		}
		result.put("state", "failure");
		return result;
	}

	@RequestMapping("/download")
	@ResponseBody
	public ResponseEntity<byte[]> download(int userId) {
		try {
			String path = generatePathByUserId(userId);
			if (new File(path).exists()) {
				return photoTransportService.fileDownload(userId, path);
			} else {
				
			}
		} catch (FileNotFoundException fnfe) {
			logger.warn("photo not found fot target user, userId" + userId, fnfe);
		} catch (IOException ioe) {
			logger.error("IOException when download photo for user, userId=" + userId, ioe);
		}
		return null;
	}

	private String generatePathByUserId(int userId) {
		return PREFIX + userId + SUFFIX;
	}
}
