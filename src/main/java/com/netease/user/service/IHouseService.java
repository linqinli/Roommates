package com.netease.user.service;

import java.util.List;

import com.netease.roommates.po.House;
import com.netease.roommates.po.HouseDetail;
import com.netease.roommates.po.HouseQuery;

public interface IHouseService {
	
	public HouseDetail getHouseDetailById(String id);
	
	public List<House> getHousesInfoByQuery(HouseQuery houseQuery);
	
}
