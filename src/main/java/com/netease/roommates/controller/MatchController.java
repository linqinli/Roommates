package com.netease.roommates.controller;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.exception.ServiceException;
import com.netease.match.service.impl.MatchPersonality;
import com.netease.roommates.po.User;
import com.netease.roommates.vo.MatchUserSimpleInfo;
import com.netease.user.service.IUserInfoService;

@Controller
@RequestMapping("/api")
public class MatchController {
	@Autowired
	private IUserInfoService userInfoService;
	
	@RequestMapping(value = "/people/list")
	@ResponseBody
	public List<MatchUserSimpleInfo> matchPeopleList(@RequestParam("id")int id,
			@RequestParam(value="p", defaultValue="1")int page,
			@RequestParam(value="xb", defaultValue="1")int xb,
			@RequestParam(value="f", defaultValue="1")int f,
			@RequestParam(value="gs", defaultValue="1")int gs,
			@RequestParam(value="cy", defaultValue="1")int cy,
			@RequestParam(value="cw", defaultValue="1")int cw, 
			@RequestParam(value="zx", defaultValue="1")int zx,
			@RequestParam(value="ws", defaultValue="1")int ws,
			@RequestParam(value="xg", defaultValue="1")int xg,
			@RequestParam(value="fk", defaultValue="1")int fk) throws ServiceException {
		User user = userInfoService.getUserById(id);
		MatchPersonality matchPersonality = new MatchPersonality(user);
		// matchPernality.selectUserByCondition(xb, f, gs, cy, cw, zx, ws, xg, fk);
		List<Integer> userIdList = matchPersonality.selectUserIdByCondition(xb, f, gs, cy, cw, zx, ws, xg, fk);
		List<User> users = new ArrayList<User>();
		for( int i=0; i<userIdList.size(); ++i){
			User tempUser = userInfoService.getUserById(userIdList.get(i));
			users.add(tempUser);
		}
		List<MatchUserSimpleInfo> userMatchList = matchPersonality.matchResultSimpleInfo(users);
		
		// matchDataHandler = new MatchDataHandler();
		// matchDataHandler.selectUserByCondition(xb, f, gs, cy, cw, zx, ws, xg, fk);
		
		return userMatchList;//matchPernality.matchResultTest();
	}
	
	@RequestMapping(value = "/people/all")
	@ResponseBody
	public List showAllPeople() throws ServiceException {
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://223.252.223.13:3306/roommates");
		dataSource.setUsername("hznetease");
		dataSource.setPassword("hz12345");
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);
		List userList = jdbcTemplate.queryForList("select * from sys_user");
		return userList;//matchPernality.matchResultTest();
	}
	
}
