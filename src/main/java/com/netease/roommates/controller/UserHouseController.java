package com.netease.roommates.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.exception.ServiceException;
import com.netease.roommates.po.BatchPhotoModel;
import com.netease.roommates.po.UserHouse;
import com.netease.user.service.IUserHouseService;

@Controller
@RequestMapping("/api/userhouse")
public class UserHouseController {
	
	private Logger logger = LoggerFactory.getLogger(PhotoController.class);
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
		
		UserHouse uHouse = userHouseService.getUserHouseById(id);
		if(uHouse == null) {
			result.put("errno", "1");
//			result.put("message", "The user does not have house info.");
			result.put("message", "当前用户不存在房源信息");
			return result;
		}
		
		List<String> picLst = new ArrayList<String>();
		
		if(uHouse.getPictures()!=null && !uHouse.getPictures().equals("")){
			String[] pics = uHouse.getPictures().split("\n");
			for(String pic:pics){
				picLst.add(pic);
			}
		}
		uHouse.setPicList(picLst);
		
		result.put("errno", "0");
		result.put("data", uHouse);
			
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
	public Map<String,Object> insertUserHouse(HttpSession session,BatchPhotoModel model) throws ServiceException {
		
		Map<String, Object> result = new HashMap<String, Object>();//
		
		Integer userId = model.getUserId();
		//取session中的userid
		if(session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
		
		//保存图片
		StringBuilder pics = new StringBuilder();
		List<String> files = model.getFiles();
		for(int i=0; files!=null && i<files.size();i++){
			
			String file = files.get(i);
			if (!file.isEmpty()) {
				pics.append(file + "\n");
			}
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
			result.put("errno", "0");
			return result;
			
		}catch(Exception e){
			logger.error("Error insert house, userId:" + userId, e);
			
			result.put("errno", "1");
			result.put("message", "保存用户信息失败" + e.getMessage());
			result.put("data", uhouse);
		}
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
	public Map<String,Object> updateUserHouse(HttpSession session,BatchPhotoModel model) throws ServiceException {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		Integer userId = model.getUserId();
		if("".equals(userId)||userId==null){
			result.put("errno", "1");
			result.put("message", "用户id为空");
			return result;
		}
		//取session中的userid
		if(session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
		
		//保存图片
		StringBuilder pics = new StringBuilder();
		List<String> files = model.getFiles();
		for(int i=0; files!=null && i<files.size();i++){
			
			String file = files.get(i);
			if (!file.isEmpty()) {
				pics.append(file + "\n");
			}
		}
		
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
			userHouseService.updateUserHouseInfo(uhouse);
			result.put("errno", "0");
			result.put("data", uhouse);
			return result;
			
		}catch(Exception e){
			logger.error("Error update house, userId:" + userId, e);
			
			result.put("errno", "1");
			result.put("message", "更新房源信息失败");
		}
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
			result.put("errno", "0");
			return result;
			
		}catch(Exception e){
			logger.error("Error delete house, userId:" + id, e);
			result.put("errno", "1");
			result.put("message", "删除房源信息失败");
		}
		
		return result;
	}
}