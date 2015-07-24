package com.netease.roommates.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.netease.user.service.IUserInfoService;
import com.netease.user.service.impl.UserInfoService;
import com.netease.utils.HashGeneratorUtils;
import com.netease.common.service.impl.CheckWord;
import com.netease.exception.HashGenerationException;
import com.netease.exception.ServiceException;
import com.netease.roommates.po.User;
import com.netease.utils.HashGeneratorUtils;

@Controller
public class LoginController {
	@Autowired
	private IUserInfoService userInfoService;

	@RequestMapping("/login/page")
	public String loginPage(){
		return "loginPage";
	}
	
	@RequestMapping("/logout")
	@ResponseBody
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response){
		ModelAndView info = new ModelAndView("loginResult");
		
		request.getSession().invalidate();
		info.addObject("loginInfo","zhuxiao");
		return info;
	}
	
	
	@RequestMapping("/login")
	@ResponseBody
	public ModelAndView loginCheck(HttpServletRequest request, HttpServletResponse response) throws HashGenerationException, ServiceException{
		ModelAndView info=new ModelAndView("loginResult");
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String p_name=request.getParameter("name");
		String p_password=request.getParameter("password");
		System.out.println(""+CheckWord.check(p_name)+CheckWord.check(p_password));
		if(CheckWord.check(p_name) || CheckWord.check(p_password)){
			//info.setViewName("loginResult");
			info.addObject("result",false);
			info.addObject("loginInfo","feifa");
			return info;
		}
		
		if(p_name==""){
			//info.setViewName("loginResult");
			info.addObject("result", false);
			info.addObject("loginInfo", "name is empty");
			return info;
		}
		if(p_password==""){
			//info.setViewName("loginResult");
			info.addObject("result", false);
			info.addObject("loginInfo", "password is empty");
			return info;
		}
		
		System.out.println(p_name+p_password);
		User user = userInfoService.getUserByName(p_name);
		
		if(user.getUserId()!=0){
			if(user.getPwdMD5Hash().equals(HashGeneratorUtils.generateSaltMD5(p_password))){
				info.addObject("result",true);
				info.addObject("loginInfo","login success");
				request.getSession(true);
				request.getSession().setAttribute("userId",user.getUserId());
								
				if(user.getCompanyEmail()!=null)
					request.getSession().setAttribute("isChecked",true);
				else
					request.getSession().setAttribute("isRegister", true);
				return info;
			}
			else{
				info.addObject("result",false);
				info.addObject("loginInfo","name and password is not fit");
				return info;
			}
		}
		else{
			info.addObject("result",false);
			info.addObject("loginInfo","no this name");
			return info;
		}
	}
}