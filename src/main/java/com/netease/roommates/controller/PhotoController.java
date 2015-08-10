package com.netease.roommates.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.exception.ControllerException;
import com.netease.exception.ServiceException;
import com.netease.roommates.po.User;
import com.netease.user.service.IFileService;
import com.netease.user.service.IUserInfoService;
import com.netease.utils.JsonBuilder;

@Controller
@RequestMapping(value = "/api/photo", produces = "application/json;charset=UTF-8")
public class PhotoController {
	private Logger logger = LoggerFactory.getLogger(PhotoController.class);
	private final static String PREFIX = "/var/www/lighttpd/Roommates/photo/photo";
	private final static String PHOTO_URL_PREFIX = "http://223.252.223.13/Roommates/photo/photo_";
	private final static String SUFFIX = ".jpg";
	private final static String USER_ID = "userId";

	@Autowired
	private IFileService photoTransportService;

	@Autowired
	private IUserInfoService userInfoService;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public String upload(HttpSession session, @RequestBody Map<String, String> params) throws ControllerException {
		try {
			JsonBuilder result = new JsonBuilder();
			int userId = (Integer) session.getAttribute(USER_ID);
			String base64Image = params.get("file");
			if (!StringUtils.isEmpty(base64Image)) {
				int idx = base64Image.indexOf(',');
				if (idx != -1) {
					base64Image = base64Image.substring(idx + 1);
					byte[] decodedBytes = DatatypeConverter.parseBase64Binary(base64Image);
					if (decodedBytes.length <= 200 * 1024) {
						photoTransportService.fildUpload(generatePathByUserId(userId), decodedBytes);
						User user = new User();
						user.setUserId(userId);
						user.setHasPhoto(true);
						userInfoService.updateUserBasicInfo(user);
						result.append("errono", 0);
						result.append("imgUrl", PHOTO_URL_PREFIX + userId + SUFFIX);
						return result.build();
					}
				}
			}
			throw new ControllerException("Photo is empty or excess max size 200KB or base64 format error.");
		} catch (ServiceException se) {
			logger.error("Error uploading photo", se);
			throw new ControllerException("Error uploading photo", se);
			
		} catch (IOException ioe) {
			logger.error("Error uploading photo", ioe);
			throw new ControllerException("Error uploading photo", ioe);
		}
	}

	@RequestMapping("/downloadBig")
	@ResponseBody
	public ResponseEntity<byte[]> downloadBigImage(int userId) {
		try {
			String path = generatePathByUserId(userId);
			if (new File(path).exists()) {
				return photoTransportService.fileDownload(path);
			} else {
				// TODO
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
				// TODO
			}
		} catch (FileNotFoundException fnfe) {
			logger.warn("photo not found fot target user, userId" + userId, fnfe);
		} catch (IOException ioe) {
			logger.error("IOException when download photo for user, userId=" + userId, ioe);
		}
		return null;
	}

	private String generatePathByUserId(int userId) {
		return PREFIX + '_' + userId + SUFFIX;
	}

	private String generateSPICPathByUserId(int userId) {
		return PREFIX + '_' + userId + '_' + "small" + SUFFIX;
	}

	@ResponseBody
	@ExceptionHandler(Exception.class)
	private String handleError(HttpServletRequest request, Exception exception) {
		logger.error("Request: " + request.getRequestURL() + " raised " + exception);
		JsonBuilder result = new JsonBuilder();
		result.append("errono", 1).append("message", exception.getMessage());
		return result.build();
	}
}
