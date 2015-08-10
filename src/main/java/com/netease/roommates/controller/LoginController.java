package com.netease.roommates.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.common.service.impl.CheckWord;
import com.netease.common.service.impl.emailAddress;
import com.netease.roommates.po.Personality;
import com.netease.roommates.po.User;
import com.netease.roommates.vo.LoginAndRegisterUserVO;
import com.netease.user.service.IUserInfoService;
import com.netease.utils.HashGeneratorUtils;
import com.netease.roommates.vo.TagVO;
import com.netease.user.service.IUserHouseService;


@Controller
@RequestMapping("/api")
public class LoginController {
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private IUserHouseService userHouseService;
	
	@RequestMapping(value="/logout")
	@ResponseBody
	public Map<String, Object> logout(HttpServletRequest request) throws Exception{
		request.getSession().invalidate();
		Map<String, Object> info = new HashMap<String, Object>();
		info.put("result",1);
		return info;
	}
	
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loginCheck(HttpServletRequest request, @RequestBody LoginAndRegisterUserVO g_user) throws Exception{	
		Map<String, Object> info=new HashMap<String, Object>();
		request.setCharacterEncoding("utf-8");
		String p_email=g_user.getEmail();
		String p_password=g_user.getPassword();
		if(p_email==null || p_email.isEmpty()){
			info.put("result", 0);
			info.put("info","请输入企业邮箱");
			return info;
		}
		if(p_password==null || p_password.isEmpty()){
			info.put("result", 0);
			info.put("info", "请输入登录密码");
			return info;
		}
		if(!emailAddress.emailCheck(p_email)){
			info.put("result", 0);
			info.put("info", "请输入您的企业邮箱");
			return info;
		}
		if(CheckWord.check(p_email) || CheckWord.check(p_password)){
			info.put("result", 0);
			info.put("info", "包含非法字符");
			return info;
		}
		
		User user = userInfoService.getUserByEmail(p_email);

		if(user!=null){
			if(user.getPwdMD5Hash().equals(HashGeneratorUtils.generateSaltMD5(p_password))){
				HttpSession session = request.getSession();
				String sessionId = session.getId();
				session.setAttribute("userId",user.getUserId());
				session.setAttribute("isChecked",true);
								
				boolean isInfoAll = (user.getNickName()!=null&&user.getGender()!=null&user.getPhoneNumber()!=null&&user.getBirthday()!=null&& user.getPosition()!=null);
				int isQuestionnaireAll = userInfoService.isQuestionnaireAll(user.getUserId());
				String credit = "一般信用";
				String headImgUrl = "http://223.252.223.13/Roommates/photo/photo_" + user.getUserId() + ".jpg";
				String defaultImgUrl = "http://223.252.223.13/Roommates/photo/photo_default.jpg";
				Personality personality = userInfoService.getUserPersonalityById(user.getUserId());
				
				
				Map<String, Object> auth= new HashMap<String, Object>();
				auth.put("email", true);
				auth.put("tel", false);
				auth.put("id", false);
				
				Map<String, Object> tags = null;
				if(isQuestionnaireAll==1){
					TagVO tag = new TagVO(personality);
					tags= new HashMap<String, Object>();
					tags.put("zx", tag.getZx());
					tags.put("cy", tag.getCy());
					tags.put("cw", tag.getCw());
					tags.put("ws", tag.getWs());
					tags.put("fk", tag.getFk());
					tags.put("xg", tag.getXg());
				}
				
				
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("userId", user.getUserId());
				dataMap.put("nickName", user.getNickName());
				if(user.getHasPhoto())
					dataMap.put("avatar", headImgUrl);
				else
					dataMap.put("avatar", defaultImgUrl);
				dataMap.put("lookStatus",user.getStatus());
				dataMap.put("credit", credit);
				dataMap.put("auth", auth);
				dataMap.put("hasHouse", (userHouseService.getUserHouseById(user.getUserId()) == null) ? false : true);
				if(user.getGender() != null)
					dataMap.put("gender", (user.getGender() == 0) ? "男" : "女");	
				if(user.getBirthday() != null)
					dataMap.put("birthday", user.getBirthday());
				if(user.getConstellation() != null)
					dataMap.put("constellation", user.getConstellation());
				dataMap.put("company", user.getCompany());
				if(user.getPosition() != null)
					dataMap.put("job", user.getPosition());
				if(user.getPhoneNumber() != null)
					dataMap.put("phone", user.getPhoneNumber());
				if(tags!=null)
					dataMap.put("tags", tags);				
				dataMap.put("completeInfo", isInfoAll);
				
								
				info.put("result", 1);
				info.put("info", "登录成功");
				info.put("access_token", sessionId);
				info.put("data", dataMap);
			
				return info;
			}
			else{
				info.put("result", 0);
				info.put("info", "密码输入不正确，请重新输入");
				return info;
			}
		}
		else{
			info.put("result",0);
			info.put("info", "该邮箱尚未注册");
			return info;
		}
	}
}