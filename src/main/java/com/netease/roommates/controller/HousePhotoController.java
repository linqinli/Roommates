package com.netease.roommates.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.netease.roommates.po.BatchPhotoModel_old;

@Controller
@RequestMapping("/api/housePhoto")
public class HousePhotoController {
	private Logger logger = LoggerFactory.getLogger(HousePhotoController.class);
	private final static String PREFIX = "/var/www/lighttpd/Roommates/photo/house/";
	
//	private final static String PREFIX = "E:/photo/house/";
	private final static String SUFFIX = ".jpg";
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> upload(int userId, MultipartFile file) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			
			File path = new File(PREFIX);
			if(!path.exists()){
				path.mkdirs();
			}
			
			if (!file.isEmpty()) {
				byte[] bytes = file.getBytes();
				fildUpload(generatePathByUserId(userId,null), bytes);
				result.put("state", "success");
				return result;
			}
		} catch (IOException ioe) {
			logger.error("Error uploading photo, userId:" + userId, ioe);
		}
		result.put("state", "failure");
		return result;
	}
	
	@RequestMapping(value = "/batchUpload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> upload(int userId, BatchPhotoModel_old model) {
		
		Map<String, String> result = new HashMap<String, String>();
		List<MultipartFile> files = model.getFiles();
		try {
			
			File path = new File(PREFIX);
			if(!path.exists()){
				path.mkdirs();
			}
			
			for(int i=0;i<files.size();i++){
				
				MultipartFile file = files.get(i);
				
				if (!file.isEmpty()) {
					byte[] bytes = file.getBytes();
					fildUpload(generatePathByUserId(userId,i), bytes);
				}
			}
			result.put("state", "success");
			return result;
			
		} catch (IOException ioe) {
			logger.error("Error uploading photo, userId:" + userId, ioe);
		}
		result.put("state", "failure");
		return result;
	}
	
	private String generatePathByUserId(int userId,Integer index) {
		
		if(index==null){
			return PREFIX + userId + SUFFIX;
		}
		return PREFIX + userId + '_' + index + SUFFIX;
	}
	
	public void fildUpload(String name, byte[] photo) throws IOException {
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(name));
			bos.write(photo);
			bos.flush();
		} finally {
			if (bos != null) {
				bos.close();
			}
		}
	}
}