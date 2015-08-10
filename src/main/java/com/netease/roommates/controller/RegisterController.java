package com.netease.roommates.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.common.service.MailSender;
import com.netease.common.service.impl.CheckWord;
import com.netease.common.service.impl.DefaultMailSender;
import com.netease.common.service.impl.emailAddress;
import com.netease.exception.ServiceException;
import com.netease.roommates.po.User;
import com.netease.roommates.vo.LoginAndRegisterUserVO;
import com.netease.user.service.IUserInfoService;
import com.netease.utils.HashGeneratorUtils;

@Controller
@RequestMapping("/api")
public class RegisterController {
	private Logger logger = LoggerFactory.getLogger(RegisterController.class);
	
	@Autowired
	private IUserInfoService userInfoService;
	
	@RequestMapping(value="/register/check")
	@ResponseBody
	public Map<String, Object> check(HttpServletRequest request) throws Exception{
		String userId = request.getParameter("userId");
		Map<String, Object> info = new HashMap<String, Object>();
		HttpSession session= request.getSession();
		if(userId==null){
			info.put("result", 0);
			info.put("info", "传入参数错误");
			return info;
		}

		User user = userInfoService.getUserById(Integer.parseInt(userId));
		if(user==null ||  user.getCompanyEmail()==null || user.getCompanyEmail().isEmpty()){
			info.put("result", 0);
			info.put("info", "邮箱未验证成功");
			return info;
		}
		else{
			session.setAttribute("isChecked",true);
			String sessionId = session.getId();
			info.put("result", 1);
			info.put("info", "验证成功");
			info.put("access_token", sessionId);
			info.put("userId", userId);
			return info;
		}

	}
	
	
	@RequestMapping(value="/register/usercheck")
	@ResponseBody
	public Map<String, Object> userCheck(HttpServletRequest request) throws Exception{
		Map<String, Object> info = new HashMap<String,Object>();
		request.setCharacterEncoding("utf-8"); 
		String p_userId = request.getParameter("checkid");
		String p_email = request.getParameter("checkemail");
		String p_name = request.getParameter("checkname");
		if(p_userId != null && p_email != null && p_email != null){
			User user = userInfoService.getUserById(Integer.parseInt(p_userId));
			if(user!=null && p_name.equals(HashGeneratorUtils.generateSaltMD5(user.getNickName()))){
				user.setCompanyEmail(p_userId+"@corp.netease.com");
				userInfoService.updateUserBasicInfo(user);
				info.put("验证结果","邮箱验证成功");
				return info;
			}
		}
		info.put("验证结果", "邮箱验证失败");
		return info;		
	}

	
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> register(HttpServletRequest request, @RequestBody LoginAndRegisterUserVO g_user) throws Exception{
		Map<String, Object> info = new HashMap<String, Object>();
		request.setCharacterEncoding("utf-8");
		String p_name = g_user.getNickname();
		String p_email = g_user.getEmail();
		String p_password = g_user.getPassword();
		if(p_name==null || p_name.isEmpty()){
			info.put("result", 0);
			info.put("info", "请输入昵称");
			return info;
		}
		if(p_password==null || p_password.isEmpty()){
			info.put("result", 0);
			info.put("info", "请输入密码");
			return info;
		}
		if(p_email==null || p_email.isEmpty()){
			info.put("result", 0);
			info.put("info", "请输入企业邮箱");
			return info;
		}
		if(CheckWord.check(p_name) || CheckWord.check(p_password) || CheckWord.check(p_email)){		
			info.put("result", 0);
			info.put("info", "包含非法字符");
			return info;
		}
		if(!emailAddress.emailCheck(p_email)){
			info.put("result", 0);
			info.put("info", "请输入您的企业邮箱");
			return info;
		}
		if(userInfoService.getUserByEmail(p_email)!= null){
			info.put("result", 0);
			info.put("info", "该邮箱已被注册");
			return info;
		}
		
		User user =new User();
		user.setNickName(p_name);
		user.setPwdMD5Hash(HashGeneratorUtils.generateSaltMD5(p_password));
		user.setCompany(emailAddress.getCompany(p_email));
			
		List<User> existList = userInfoService.getUserByName(p_name);
		User registerUser = null;
		for(User n_user : existList)
			if(HashGeneratorUtils.generateSaltMD5(p_password).equals(n_user.getPwdMD5Hash()) && emailAddress.getCompany(p_email).equals(n_user.getCompany()) && n_user.getCompanyEmail()==null){
				registerUser = n_user;
				break;
			}
		if(registerUser == null){
			userInfoService.insertUser(user);
			List<User> userList = userInfoService.getUserByName(p_name);
			for(User n_user : userList)
				if(HashGeneratorUtils.generateSaltMD5(p_password).equals(n_user.getPwdMD5Hash()) && emailAddress.getCompany(p_email).equals(n_user.getCompany()) && n_user.getCompanyEmail()==null){
					registerUser = n_user;
					break;
				}
		}
		if(registerUser == null){
			info.put("result", 0);
			info.put("info", "注册失败");
			return info;
		}
		
		int userId = registerUser.getUserId();
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("userId",userId);
		dataMap.put("avatar", "http://223.252.223.13/Roommates/photo/photo_default.jpg");
		dataMap.put("company", emailAddress.getCompany(p_email));
		
		
		info.put("result", 1);
		info.put("info", "注册成功");
		info.put("data", dataMap);
//		HttpSession session = request.getSession();
//		session.setAttribute("userId",userId);
//		String sessionId = session.getId();
//		info.put("access_token", sessionId);
		
		
		StringBuffer mailstring = new StringBuffer("这是验证邮件，请访问如下网址：<br/><a href=");
		StringBuffer stringbuffer = new StringBuffer("http://223.252.223.13/Roommates/api/register/usercheck");
		stringbuffer.append("?checkid=" + userId);
		stringbuffer.append("&checkemail=" + p_email);
		stringbuffer.append("&checkname=" + HashGeneratorUtils.generateSaltMD5(p_name));
		mailstring.append(stringbuffer+">"+stringbuffer+"</a>");
		String mailString = mailstring.toString();
		
		final MailSender mailsender = new DefaultMailSender();
		mailsender.setReceiver(p_email);
		mailsender.setSubject("验证邮件");
		mailsender.setContent(mailString);
		
		new Thread() {
			public void run() {
				try {
					mailsender.send();
					logger.info("Mail has been sent.");
				} catch (ServiceException e) {
					logger.error("Error sending email.", e);
				}
			}
		}.start();

		return info;
	}
}