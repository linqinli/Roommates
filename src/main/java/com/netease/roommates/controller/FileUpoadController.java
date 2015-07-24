package com.netease.roommates.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.netease.user.service.FileService;

@Controller
@RequestMapping("/photo")
public class FileUpoadController {
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
				photoTransportService.fildUpload(userId + SUFFIX, bytes);
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
	public ResponseEntity<byte[]> download(int userId) throws IOException {
		return photoTransportService.fileDownload(userId, userId + SUFFIX);
	}

}
