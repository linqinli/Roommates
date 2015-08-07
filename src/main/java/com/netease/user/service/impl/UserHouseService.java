package com.netease.user.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.netease.exception.ServiceException;
import com.netease.roommates.controller.PhotoController;
import com.netease.roommates.mapper.UserHouseMapper;
import com.netease.roommates.po.UserHouse;
import com.netease.user.service.IFileService;
import com.netease.user.service.IUserHouseService;

@Service
public class UserHouseService implements IUserHouseService {
	private Logger logger = LoggerFactory.getLogger(PhotoController.class);
	private final static String PREFIX = "/var/www/lighttpd/Roommates/photo/house/";
	private static int count = 0;

	@Autowired
	private UserHouseMapper uhouseMapper;
	@Autowired
	private IFileService photoTransportService;

	public void insertUserHouse(UserHouse uhouse) throws ServiceException {
		uhouseMapper.insertUserHouse(uhouse);
	}

	public void updateUserHouseInfo(UserHouse uhouse) throws ServiceException {
		uhouseMapper.updateUserHouseInfo(uhouse);
	}

	@Override
	public UserHouse getUserHouseById(int userId) throws ServiceException {
		return uhouseMapper.getUserHouseById(userId);
	}

	@Override
	public boolean deleteUserHouse(int userId) throws ServiceException {
		UserHouse userHouser = uhouseMapper.getUserHouseById(userId);
		List<String> picLst = new ArrayList<String>();
		if(!StringUtils.isEmpty(userHouser.getPictures())) {
			String[] photoNames = userHouser.getPictures().split(";");
			for (String photoName : photoNames) {
				picLst.add(PREFIX + photoName);
			}
		}
		uhouseMapper.deleteUserHouse(userId);
		for(String photoName : picLst) {
			photoTransportService.deleteFile(PREFIX + photoName);
		}
		return true;
	}

	@Override
	public List<String> saveBase64Image(int userId, List<String> images) throws ServiceException {
		try {
			List<String> photoNames = new ArrayList<String>();
			List<String> base64Images = new ArrayList<String>();
			for (String img : images) {
				int idx = -1;
				if ((idx = img.indexOf(',')) != -1) {
					img = img.substring(idx + 1);
				}
				base64Images.add(img);
			}

			initializeCount();
			for (String img : base64Images) {
				byte[] decoded = DatatypeConverter.parseBase64Binary(img);
				String photoName = getPhotoName(userId);
				photoNames.add(photoName);
				photoTransportService.fildUpload(PREFIX + photoName, decoded);
			}
			return photoNames;
		} catch (IOException ioe) {
			logger.error("Error uploading house photo for user " + userId, ioe);
			throw new ServiceException("Error uploading house photo ", ioe);
		}
	}
	
	@Override
	public void deleteImage(int userId, List<String> photoNames) {
		if(photoNames == null) {
			return;
		}
		UserHouse userHouser = uhouseMapper.getUserHouseById(userId);
		if(StringUtils.isEmpty(userHouser.getPictures())) {
			return;
		}
		String[] picList = userHouser.getPictures().split(";");
		StringBuilder sb = new StringBuilder();
		for(String pic : picList) {
			if(!photoNames.contains(pic)) {
				sb.append(pic);
				sb.append(';');
			}
		}
		if(sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		userHouser.setPictures(sb.toString());
		uhouseMapper.updateUserHouseInfo(userHouser);
		
		for(String photoName : photoNames) {
			photoTransportService.deleteFile(PREFIX + photoName);
		}
	}
	
	public List<String> addImage(int userId, List<String> images) throws ServiceException {
		List<String> photoNames = saveBase64Image(userId, images);
		StringBuilder sb = new StringBuilder();
		for(String photoName : photoNames) {
			sb.append(';');
			sb.append(photoName);
		}
		UserHouse userHouser = uhouseMapper.getUserHouseById(userId);
		String pictures = userHouser.getPictures();
		if(!StringUtils.isEmpty(pictures)) {
			pictures += sb.toString();
		} else if(sb.length() > 0){
			sb.deleteCharAt(0);
			pictures = sb.toString();
		}
		userHouser.setPictures(pictures);
		uhouseMapper.updateUserHouseInfo(userHouser);
		return photoNames;
	}
	
	private String getPhotoName(int userId) {
		return userId + "_" + generateId() + ".jpg";
	}

	private static void initializeCount() {
		count = 0;
	}

	private static long generateId() {
		long id = System.currentTimeMillis() << 6;
		return id + count++;
	}

}
