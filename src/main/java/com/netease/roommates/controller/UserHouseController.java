package com.netease.roommates.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.netease.exception.ServiceException;
import com.netease.roommates.po.BatchPhotoModel;
import com.netease.roommates.po.UserHouse;
import com.netease.user.service.IUserHouseService;

@Controller
@RequestMapping("/api/userhouse")
public class UserHouseController {
	
	private Logger logger = LoggerFactory.getLogger(PhotoController.class);
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private final static String save_prefix = "/var/www/lighttpd/Roommates/photo/house/";
//	private final static String save_prefix = "E:/photo/house/";
	
	private final static String PREFIX = "http://223.252.223.13/Roommates/photo/house/";
	private final static String SUFFIX = ".jpg";
	
	@Autowired
	private IUserHouseService userHouseService;
	
	/**
	 * 按id查询房源
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/{id}")
	@ResponseBody
	public HashMap<String,Object> getUserHouseById(@PathVariable int id) throws ServiceException {
		
		HashMap<String,Object> result = new HashMap<String,Object>();
		
		if(!"".equals(id)){
			UserHouse uHouse = userHouseService.getUserHouseById(id);
			List<String> picLst = new ArrayList<String>();
			
			if(uHouse.getPictures().contains(".jpg")){
				String[] pics = uHouse.getPictures().split("\n");
				for(String pic:pics){
					picLst.add(PREFIX + pic);
				}
			}
			uHouse.setPicList(picLst);
			
			result.put("errno", "0");
			result.put("data", uHouse);
			
		}else{
			result.put("errno", "1");
			result.put("data", new HashMap());
		}
		return result;
	}
	
	/**
	 * 添加房源信息
	 * @param model
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> insertUserHouse(BatchPhotoModel model) throws ServiceException {
		
		Map<String, String> result = new HashMap<String, String>();//
		
		Integer userId = model.getUserId();
		
		//上传图片
		StringBuilder pics = new StringBuilder();
		List<MultipartFile> files = model.getFiles();
		try {
			File path = new File(save_prefix);
			if(!path.exists()){
				path.mkdirs();
			}
			for(int i=0;i<files.size();i++){
				
				MultipartFile file = files.get(i);
				if (!file.isEmpty()) {
					byte[] bytes = file.getBytes();
					String i_fileName = generatePathByUserId(userId,i);
					fildUpload(i_fileName, bytes);
					
					pics.append(userId + "_" + i + SUFFIX + "\n");
				}
			}
			
		} catch (IOException ioe) {
			logger.error("Error uploading photo, userId:" + userId, ioe);
			
			result.put("state", "failure");
			
			return result;
		}
		
		//添加新的记录到数据库
		UserHouse uhouse = new UserHouse();
		uhouse.setUserId(userId);
		
		uhouse.setPictures(pics.toString());
		
		uhouse.setTitle(model.getTitle());
		uhouse.setPrice(model.getPrice());
		uhouse.setArea(model.getArea());
		uhouse.setCommunity(model.getCommunity());
		uhouse.setDescription(model.getDescription());
		uhouse.setUpdateTime(sdf.format(new Date()));
		
		try{
			userHouseService.insertUserHouse(uhouse);
			result.put("state", "success");
			return result;
			
		}catch(Exception e){
			logger.error("Error insert house, userId:" + userId, e);
		}
		result.put("state", "failure");
		
		return result;
	}
	
	/**
	 * 更新房源信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> updateUserHouse(BatchPhotoModel model) throws ServiceException {
		
		Map<String, String> result = new HashMap<String, String>();
		
		Integer userId = model.getUserId();
		if("".equals(userId)||userId==null){
			result.put("state", "failure");
			return result;
		}
		
		UserHouse uhouse = new UserHouse();
		uhouse.setUserId(userId);
//		uhouse.setPictures(request.getParameter("images"));
		uhouse.setTitle(model.getTitle());
		uhouse.setPrice(model.getPrice());
		uhouse.setArea(model.getArea());
		uhouse.setCommunity(model.getCommunity());
		uhouse.setDescription(model.getDescription());
		uhouse.setUpdateTime(sdf.format(new Date()));
		
		try{
			userHouseService.updateUserHouseInfo(uhouse);
			result.put("state", "success");
			return result;
			
		}catch(Exception e){
			logger.error("Error insert house, userId:" + userId, e);
		}
		result.put("state", "failure");
		
		return result;
	}
	
	/**
	 * 删除房源信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/delete/{id}")
	@ResponseBody
	public Map<String,String> deleteUserHouse(@PathVariable int id) throws ServiceException {
		
		Map<String, String> result = new HashMap<String, String>();
		try{
			userHouseService.deleteUserHouse(id);
			result.put("state", "success");
			return result;
			
		}catch(Exception e){
			logger.error("Error insert house, userId:" + id, e);
		}
		result.put("state", "failure");
		return result;
	}
	
	public String generatePathByUserId(int userId,Integer index) {
		
		if(index==null){
			return save_prefix + userId + SUFFIX;
		}
		return save_prefix + userId + "_" + index + SUFFIX;
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