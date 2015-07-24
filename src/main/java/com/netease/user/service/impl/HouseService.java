package com.netease.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netease.roommates.mapper.HouseMapper;
import com.netease.roommates.po.House;
import com.netease.roommates.po.HouseDetail;
import com.netease.roommates.po.HouseQuery;
import com.netease.user.service.IHouseService;

@Service
public class HouseService implements IHouseService {
	@Autowired
	private HouseMapper houseMapper;
	
	public HouseDetail getHouseDetailById(String id) {
		return houseMapper.getHouseDetailById(id);
	}
	
	public List<House> getHousesInfoByQuery(HouseQuery houseQuery){
		return houseMapper.getHousesInfoByQuery(houseQuery);
	}
}
