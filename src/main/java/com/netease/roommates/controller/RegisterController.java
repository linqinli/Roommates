package com.netease.roommates.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.netease.roommates.po.User;
import com.netease.roommates.vo.LoginAndRegisterUserVO;
import com.netease.user.service.IUserInfoService;
import com.netease.utils.HashGeneratorUtils;




@Controller
@RequestMapping("/api")
public class RegisterController {
	@Autowired
	private IUserInfoService userInfoService;
	
	
	@RequestMapping("/register/page")
	public String loginPage(){
		return "register";
	}
	
	@RequestMapping(value="/register/check")
	@ResponseBody
	public Map<String, Object> check(HttpServletRequest request, HttpServletResponse response) throws Exception{		
		Map<String, Object> info = new HashMap<String, Object>();
		HttpSession session= request.getSession();
		if(session==null){
			info.put("result", 0);
			info.put("info", "超时");
			return info;
		}
		else{
			int p_userId = (Integer)session.getAttribute("userId");
			System.out.println(p_userId+"");
			User user = userInfoService.getUserById(p_userId);
			if(user==null ||  user.getCompanyEmail()==null || user.getCompanyEmail().isEmpty()){
				info.put("result", 0);
				info.put("info", "邮箱未验证成功");
			}
			else{
				info.put("result", 1);
				info.put("info", "验证成功");
				info.put("userId", p_userId);
				request.getSession().setAttribute("isChecked",true);
			}
				
		}
		return info;	
	}
	
	
	@RequestMapping(value="/register/usercheck")
	public void userCheck(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8"); 
		String p_userId = request.getParameter("checkid");
		String p_email = request.getParameter("checkemail");
		String p_name = request.getParameter("checkname");
		System.out.println(Integer.parseInt(p_userId)+"+"+p_email);
		User user = userInfoService.getUserById(Integer.parseInt(p_userId));
		System.out.println(user);
		if(user!=null && p_name.equals(HashGeneratorUtils.generateSaltMD5(user.getNickName()))){
			user.setCompanyEmail(p_email);
			userInfoService.updateUserBasicInfo(user);
		}
	}
	
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> registercheck(HttpServletRequest request, @RequestBody LoginAndRegisterUserVO g_user) throws Exception{
		Map<String, Object> info = new HashMap<String, Object>();
		request.setCharacterEncoding("utf-8");
		
		String p_name = g_user.getNickname();
		String p_email = g_user.getEmail();
		String p_password = g_user.getPassword();
		System.out.println(p_name+"+"+p_email);
		if(CheckWord.check(p_name) || CheckWord.check(p_password) || CheckWord.check(p_email)){		
			info.put("result", 0);
			info.put("info", "包含非法字符");
			return info;
		}
		
		if(p_name==null || p_name.isEmpty()){
			info.put("result", 0);
			info.put("info", "昵称为空");
			return info;
		}
		if(p_password==null || p_password.isEmpty()){
			info.put("result", 0);
			info.put("info", "密码为空");
			return info;
		}
		if(p_email==null || p_email.isEmpty()){
			info.put("result", 0);
			info.put("info", "邮箱为空");
			return info;
		}
		
		if(userInfoService.getUserByEmail(p_email)!= null){
			info.put("result", 0);
			info.put("info", "该邮箱已被注册");
			return info;
		}
		
		if(!emailAddress.emailCheck(p_email)){
			info.put("result", 0);
			info.put("info", "邮箱格式错误");
			return info;
		}
		
		User user =new User();
		user.setNickName(p_name);
		user.setPwdMD5Hash(HashGeneratorUtils.generateSaltMD5(p_password));
		user.setCompany(emailAddress.getCompany(p_email));
		
		
		userInfoService.insertUser(user);
		request.getSession(true);
		List<User> userList = userInfoService.getUserByName(p_name);
		User registerUser = null;
		for(User n_user : userList)
			if(HashGeneratorUtils.generateSaltMD5(p_password).equals(n_user.getPwdMD5Hash()) && emailAddress.getCompany(p_email).equals(n_user.getCompany())){
				registerUser = n_user;
				break;
			}
		if(registerUser == null){
			info.put("result", 0);
			info.put("info", "注册失败");
			return info;
		}
		int userId = registerUser.getUserId();
		request.getSession(true);
		request.getSession().setAttribute("userId",userId);
		request.getSession().setAttribute("isRegister",true);
		info.put("result", 1);
		info.put("info", "注册成功");
		info.put("userId", userId);
		
		
		StringBuffer mailstring = new StringBuffer("这是验证邮件，请访问如下网址：<br/><a href=");
		StringBuffer stringbuffer = new StringBuffer("http://223.252.223.13/Roommates/api/register/usercheck");
		stringbuffer.append("?checkid=" + userId);
		stringbuffer.append("&checkemail=" + p_email);
		stringbuffer.append("&checkname=" + HashGeneratorUtils.generateSaltMD5(p_name));
		mailstring.append(stringbuffer+">"+stringbuffer+"</a>");
		String mailString = mailstring.toString();
		
		MailSender mailsender = new DefaultMailSender();
		mailsender.setReceiver(p_email);
		mailsender.setSubject("验证邮件");
		mailsender.setContent(mailString);
		mailsender.send();
		System.out.println("mail send");
		return info;
	}
}