package com.netease.roommates.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.netease.exception.ServiceException;
import com.netease.roommates.po.UserHouse;
import com.netease.user.service.IUserHouseService;

@Controller
@RequestMapping("/api/userhouse")
public class UserHouseController {
	
	private Logger logger = LoggerFactory.getLogger(PhotoController.class);
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private IUserHouseService userHouseService;
	
	@RequestMapping("/{id}")
	@ResponseBody
	public HashMap<String,Object> getUserHouseById(@PathVariable String id) throws ServiceException {
		
		HashMap<String,Object> result = new HashMap<String,Object>();
		
		if(!"".equals(id)){
			UserHouse uHouse = userHouseService.getUserHouseById(Integer.parseInt(id));
//			System.out.println(uHouse);
			
			result.put("errno", "0");
			result.put("data", uHouse);
			
		}else{
			result.put("errno", "1");
			result.put("data", new HashMap());
		}
		return result;
	}

	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> insertUserHouse(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		
		Map<String, String> result = new HashMap<String, String>();
		
		String userId = request.getParameter("userId");
		if("".equals(userId)||userId==null){
			result.put("state", "failure");
			return result;
		}
		
		UserHouse uhouse = new UserHouse();
		uhouse.setUserId(Integer.parseInt(userId));
		uhouse.setPictures(request.getParameter("images"));
		uhouse.setTitle(request.getParameter("title"));
		uhouse.setPrice(request.getParameter("price"));
		uhouse.setArea(request.getParameter("community"));
		uhouse.setCommunity(request.getParameter("area"));
		uhouse.setDescription(request.getParameter("description"));
		uhouse.setUpdateTime(sdf.format(new Date()));
		
		try{
			userHouseService.insertUserHouse(uhouse);
			result.put("state", "success");
			return result;
			
		}catch(Exception e){
			logger.error("Error insert house, userId:" + request.getParameter("userId"), e);
		}
		result.put("state", "failure");
		
		return result;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> updateUserHouse(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		
		Map<String, String> result = new HashMap<String, String>();
		
		String userId = request.getParameter("userId");
		if("".equals(userId)||userId==null){
			result.put("state", "failure");
			return result;
		}
		
		UserHouse uhouse = new UserHouse();
		uhouse.setUserId(Integer.parseInt(userId));
		uhouse.setPictures(request.getParameter("images"));
		uhouse.setTitle(request.getParameter("title"));
		uhouse.setPrice(request.getParameter("price"));
		uhouse.setArea(request.getParameter("area"));
		uhouse.setCommunity(request.getParameter("community"));
		uhouse.setDescription(request.getParameter("description"));
		uhouse.setUpdateTime(sdf.format(new Date()));
		
		try{
			userHouseService.updateUserHouseInfo(uhouse);
			result.put("state", "success");
			return result;
			
		}catch(Exception e){
			logger.error("Error insert house, userId:" + request.getParameter("userId"), e);
		}
		result.put("state", "failure");
		
		return result;
	}
}