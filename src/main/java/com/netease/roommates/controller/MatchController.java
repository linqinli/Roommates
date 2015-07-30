package com.netease.roommates.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.exception.HashGenerationException;
import com.netease.exception.ServiceException;
import com.netease.match.service.IMatchDataService;
import com.netease.match.service.IMatchDetailService;
import com.netease.roommates.po.User;
import com.netease.roommates.vo.MatchUserDetailInfo;
import com.netease.roommates.vo.MatchUserSimpleInfo;
import com.netease.user.service.IUserInfoService;
import com.netease.utils.HashGeneratorUtils;

@Controller
@RequestMapping("/api")
public class MatchController {
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private IMatchDataService matchDataService;
	@Autowired
	private IMatchDetailService matchDetailService;
	private final static String USER_ID = "userId";
	
	@RequestMapping(value = "/people/list")
	@ResponseBody
	public Map matchPeopleList(@RequestParam("id")int id,
			@RequestParam(value="p", defaultValue="1")int p,
			@RequestParam(value="xb", defaultValue="1")int xb,
			@RequestParam(value="f", defaultValue="1")int f,
			@RequestParam(value="gs", defaultValue="1")int gs,
			@RequestParam(value="cy", defaultValue="1")int cy,
			@RequestParam(value="cw", defaultValue="1")int cw, 
			@RequestParam(value="zx", defaultValue="1")int zx,
			@RequestParam(value="ws", defaultValue="1")int ws,
			@RequestParam(value="xg", defaultValue="1")int xg,
			@RequestParam(value="fk", defaultValue="1")int fk) throws ServiceException {
		
		Map resultMap = new HashMap<String, Object >();
		
		List<MatchUserSimpleInfo> resultUserInfo = new ArrayList<MatchUserSimpleInfo>();
		
		resultUserInfo = matchDataService.getMatchUserSimpleInfoByPara(id, p, xb, f, gs, cy, cw, zx, ws, xg, fk);
		
		resultMap.put("data", resultUserInfo);
		resultMap.put("errno", 0);
		return resultMap;//matchPernality.matchResultTest();
	}
	
	@RequestMapping(value = "/people/allus")
	@ResponseBody
	public List showAllPeople() throws ServiceException, HashGenerationException {
		
//		String[] strsNames = {"陈松","郭喻","李晓","方梓","潘娅","王伟","游光","钱懿","杨艺","金宇",
//				"关荟","沈书","韩传","郝米","王进","李月","郝艳","刘青","龙建","史景","潘李","石艺","金熙",
//				"赵蕴","张锦","姚智","陈嘉","刘卫","李静","周思","蒋佩","梅雅","郝胜","全思","李珊","杜文",
//				"汪飞","汪俊"};
//		String[] strsCompany={"网易","阿里", "大华", "UC斯达康","海康威视"};
//		String[] strsPositions = {"程序员", "设计师", "产品经理", "运营", "测试", "策划"};
//		
//		for( int i=0; i<1; i++){
//			User user = new User();
//			
//			user.setUserName("李光玉");
//			user.setNickName("liguangyu");
//			user.setCompany("大华");
//			user.setGender((byte)0);
//			user.setPosition("设计师");
//			user.setPwdMD5Hash(HashGeneratorUtils.generateMD5("12345"));
//			user.setPhoneNumber("120666666666");
//			Date date = new Date();
//			date.setYear(92);
//			user.setBirthday(date);
//			user.setStatus((byte)0);
//		}
//		userInfoService.insertUser(user);
		
		return matchDataService.selectAllUsers();//matchPernality.matchResultTest();
	}
	
	@RequestMapping(value = "/people/allps")
	@ResponseBody
	public List showAllPersonality() throws ServiceException, HashGenerationException {
		
		return matchDataService.selectAllPersonalitys();//matchPernality.matchResultTest();
	}
	
	@RequestMapping(value = "/people/detail/{id}")
	@ResponseBody
	public Map matchPeopleList(HttpSession session, @PathVariable int id) throws ServiceException {
		// int curUserId = (Integer) session.getAttribute(USER_ID);
		
		MatchUserDetailInfo matchUserDetailInfo =  matchDetailService.getDetailByUser(id);
		Map matchDetailMap = new HashMap<String, Object>();
		matchDetailMap.put("data", matchUserDetailInfo);
		matchDetailMap.put("errno", 0);
		
		return matchDetailMap;//matchPernality.matchResultTest();
	}

	
}
