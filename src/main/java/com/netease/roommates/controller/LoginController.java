package com.netease.roommates.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.netease.user.service.IUserInfoService;
import com.netease.utils.HashGeneratorUtils;
import com.netease.common.service.impl.CheckWord;
import com.netease.exception.HashGenerationException;
import com.netease.exception.ServiceException;
import com.netease.roommates.po.User;
import com.netease.roommates.po.emailAddress;

@Controller
@RequestMapping("/api")
public class LoginController {
	@Autowired
	private IUserInfoService userInfoService;

	@RequestMapping("/login/page")
	public String loginPage(){
		return "loginPage";
	}
	
	@RequestMapping("/logout")
	@ResponseBody
	public Map<String, Object> logout(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> info = new HashMap<String, Object>();		
		request.getSession().invalidate();
		info.put("result",true);
		return info;
	}
	
	
	@RequestMapping("/login")
	@ResponseBody
	public Map<String, Object> loginCheck(HttpServletRequest request, HttpServletResponse response) throws HashGenerationException, ServiceException{
		Map<String, Object> info=new HashMap<String, Object>();
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String p_email=request.getParameter("email");
		String p_password=request.getParameter("password");
		if(CheckWord.check(p_email) || CheckWord.check(p_password)){
			info.put("result", false);
			info.put("info", "word is illegal");
			return info;
		}
		
		if(p_email==null || p_email.isEmpty()){
			info.put("result", false);
			info.put("info","email is empty");
			return info;
		}
		if(p_password==null || p_password.isEmpty()){
			info.put("result", false);
			info.put("info", "password is empty");
			return info;
		}
		
		boolean flag = false;
		for(Pattern pattern : emailAddress.emailPattern){
			Matcher matcher = pattern.matcher(p_email);
			if(matcher.matches()){
				flag=true;
				break;
			}
		}
		
		if(!flag){
			info.put("result", false);
			info.put("info", "email tyep is wrong");
			return info;
		}
		
		User user = userInfoService.getUserByEmail(p_email);
		
		if(user!=null){
			if(user.getPwdMD5Hash().equals(HashGeneratorUtils.generateSaltMD5(p_password))){
				info.put("result", true);
				info.put("info", "login success");
				info.put("userId", user.getUserId());
				request.getSession(true);
				request.getSession().setAttribute("userId",user.getUserId());
				request.getSession().setAttribute("isChecked",true);

				return info;
			}
			else{
				info.put("result", false);
				info.put("info", "not fit");
				return info;
			}
		}
		else{
			info.put("result",false);
			info.put("info", "no this email");
			return info;
		}
	}
}