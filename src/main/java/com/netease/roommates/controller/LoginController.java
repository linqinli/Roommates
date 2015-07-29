package com.netease.roommates.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.common.service.impl.CheckWord;
import com.netease.common.service.impl.emailAddress;
import com.netease.roommates.po.User;
import com.netease.roommates.vo.LoginAndRegisterUserVO;
import com.netease.user.service.IUserInfoService;
import com.netease.utils.HashGeneratorUtils;




@Controller
@RequestMapping("/api")
public class LoginController {
	@Autowired
	private IUserInfoService userInfoService;
	
	@RequestMapping("/login/page")
	public String loginPage() throws MessagingException{
		return "loginPage";
	}
	
	@RequestMapping(value="/logout")
	@ResponseBody
	public Map<String, Object> logout(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> info = new HashMap<String, Object>();		
		request.getSession().invalidate();
		info.put("result",1);
		return info;
	}
	
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loginCheck(HttpServletRequest request, @RequestBody LoginAndRegisterUserVO g_user) throws Exception{
	
		Map<String, Object> info=new HashMap<String, Object>();
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String p_email=g_user.getEmail();
		String p_password=g_user.getPassword();
		if(CheckWord.check(p_email) || CheckWord.check(p_password)){
			info.put("result", 0);
			info.put("info", "包含非法字符");
			return info;
		}
		
		if(p_email==null || p_email.isEmpty()){
			info.put("result", 0);
			info.put("info","邮箱为空");
			return info;
		}
		if(p_password==null || p_password.isEmpty()){
			info.put("result", 0);
			info.put("info", "密码为空");
			return info;
		}
		
		
		if(!emailAddress.emailCheck(p_email)){
			info.put("result", 0);
			info.put("info", "邮箱格式错误");
			return info;
		}
		
		User user = userInfoService.getUserByEmail(p_email);
		
		if(user!=null){
			if(user.getPwdMD5Hash().equals(HashGeneratorUtils.generateSaltMD5(p_password))){
				info.put("result", 1);
				info.put("info", "登录成功");
				info.put("userId", user.getUserId());
				request.getSession(true);
				request.getSession().setAttribute("userId",user.getUserId());
				request.getSession().setAttribute("isChecked",true);
				boolean isInfoAll = (user.getUserName()!=null&&user.getPhoneNumber()!=null&&user.getBirthday()!=null&&user.getAddress()!=null&&user.getPosition()!=null);
				if(isInfoAll)
					info.put("isInfoAll", 1);
				else
					info.put("isInfoAll", 0);
				int isQuestionnaireAll = userInfoService.isQuestionnaireAll(user.getUserId());
				String credit = "低等信用";
				String headImgUrl = "http://223.252.223.13/Roommates/photo/photo_" + user.getUserId() + "_small.jpg";
				info.put("isQuestionnaireAll", isQuestionnaireAll);
				info.put("credit", credit);
				info.put("headImgUrl", headImgUrl);
				System.out.println(user);
				
				Map<String, String> dataMap = new HashMap<String, String>();
				dataMap.put("lookStatus", "");
			
				return info;
			}
			else{
				info.put("result", 0);
				info.put("info", "邮箱与密码不匹配");
				return info;
			}
		}
		else{
			info.put("result",0);
			info.put("info", "该邮箱未注册");
			return info;
		}
	}
}