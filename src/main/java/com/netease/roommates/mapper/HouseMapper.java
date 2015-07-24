package com.netease.roommates.mapper;

import java.util.List;

import com.netease.roommates.po.House;
import com.netease.roommates.po.HouseDetail;
import com.netease.roommates.po.HouseQuery;

public interface HouseMapper {
	
	public HouseDetail getHouseDetailById(String id);
	
	public List<House> getHousesInfoByQuery(HouseQuery houseQuery);
}
