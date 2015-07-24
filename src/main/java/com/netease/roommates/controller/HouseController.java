package com.netease.roommates.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.roommates.po.House;
import com.netease.roommates.po.HouseConstProperty;
import com.netease.roommates.po.HouseDetail;
import com.netease.roommates.po.HouseQuery;
import com.netease.user.service.IHouseService;

@Controller
public class HouseController {
	
	private int pageSize = 10;
//	private String defaultImgUrl="http://placehold.it/200/200";
//	private String defaultjjrImgUrl="http://placehold.it/50/50";
	
	@Autowired
	private IHouseService houseService;

	@RequestMapping("/house/{id}")
	@ResponseBody
	public HashMap<String,Object> getHouseDetailById(@PathVariable String id) {
		
		HashMap<String,Object> result = new HashMap<String,Object>();
		
		HouseDetail houseDetail;
		try {
			houseDetail = houseService.getHouseDetailById(id);
			
			String[] images = {};
			String imgUrls = houseDetail.getPicUrls();//
			if(!"".equals(imgUrls) && imgUrls!=null){
				images = imgUrls.split("\n");
			}
			houseDetail.setImages(images);
			houseDetail.setPicUrls("");
			
			result.put("errno", "0");
			result.put("data", houseDetail);
			
		} catch (Exception e) {
//			e.printStackTrace();
			result.put("errno", "1");
			result.put("data", new HashMap());
		}
		return result;
	}

	@RequestMapping("/house/list")
	@ResponseBody
	public HashMap<String,Object> getHousesInfoByQuery(@RequestParam("q") String kw, @RequestParam("area") String area,
			@RequestParam("price") String price, @RequestParam("room") String room,
			@RequestParam("p") String page) {
		
		HashMap<String,Object> result = new HashMap<String,Object>();
		
		area = "";//
		
		int minPrice = 0;
		int maxPrice = 0;
		List<House> houseLst;
		try {
			String roomType = "";
			if(!"".equals(price)&&!"1".equals(price)){
				String priceRange = HouseConstProperty.priceMap.get(Integer.parseInt(price));
				String[] arr = priceRange.split("-",2);
				minPrice = Integer.parseInt(arr[0]);
				maxPrice = Integer.parseInt(arr[1]);
				
			}
			if(!"".equals(room)&& !"1".equals(room)){
				roomType = HouseConstProperty.roomTypeMap.get(Integer.parseInt(room));
			}
			
			
			int curPage = Integer.parseInt(page);
			int startRow = (curPage-1)*pageSize;
			
			houseLst = houseService.getHousesInfoByQuery(
					new HouseQuery(kw,area,minPrice,maxPrice,roomType,startRow,pageSize));
			for(House house:houseLst){
				String imgUrls = house.getImgUrl();
				if(!"".equals(imgUrls) && imgUrls!=null){
					house.setImgUrl(imgUrls.split("\n",2)[0]);
				}
			}
			
			result.put("errno", "0");
			result.put("data", houseLst);
			
		} catch (NumberFormatException e) {
//			e.printStackTrace();
			
			result.put("errno", "1");
			result.put("data", new ArrayList<String>());
			
		}
		return result;
	}
	
	class EmptyObject implements Serializable{

		private static final long serialVersionUID = 1628542856918881025L;
		
	}
}