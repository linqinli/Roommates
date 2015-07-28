package com.netease.roommates.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.netease.common.service.MailSender;
import com.netease.common.service.impl.CheckWord;
import com.netease.common.service.impl.DefaultMailSender;
import com.netease.roommates.po.User;
import com.netease.user.service.IUserInfoService;
import com.netease.utils.HashGeneratorUtils;
import com.netease.roommates.po.emailAddress;

@Controller
@RequestMapping("/api")
public class RegisterController {
	@Autowired
	private IUserInfoService userInfoService;
	
	
	@RequestMapping("/register/page")
	public String loginPage(){
		return "register";
	}
	
	@RequestMapping("/register/check")
	@ResponseBody
	public Map<String, Object> check(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> info = new HashMap<String, Object>();
		int p_userId = (int)request.getSession().getAttribute("userId");
		System.out.println(p_userId+"");
		if(p_userId==0){
			info.put("result", false);
			info.put("info", "id is not exist");
		}
		else{
			User user = userInfoService.getUserById(p_userId);
			if(user==null ||  user.getCompanyEmail()==null || user.getCompanyEmail().isEmpty()){
				info.put("result", false);
				info.put("info", "check fail");
			}
			else{
				info.put("result", true);
				info.put("info", "check success");
				request.getSession().setAttribute("isChecked",true);
			}
				
		}
		return info;	
	}
	
	
	@RequestMapping("/register/usercheck")
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
	
	
	@RequestMapping("/register")
	@ResponseBody
	public Map<String, Object> registercheck(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> info = new HashMap<String, Object>();
		request.setCharacterEncoding("utf-8");
		
		String p_name=request.getParameter("username");
		String p_password=request.getParameter("password");
		String p_email=request.getParameter("email");
		System.out.println(p_name+"+"+p_email);
		if(CheckWord.check(p_name) || CheckWord.check(p_password) || CheckWord.check(p_email)){		
			info.put("result", false);
			info.put("info", "word is illegal");
			return info;
		}
		
		if(p_name==null || p_name.isEmpty()){
			info.put("result", false);
			info.put("info", "name is empty");
			return info;
		}
		if(p_password==null || p_password.isEmpty()){
			info.put("result", false);
			info.put("info", "password is empty");
			return info;
		}
		if(p_email.isEmpty() || p_email==null){
			info.put("result", false);
			info.put("info", "email is empty");
			return info;
		}
		
		if(userInfoService.getUserByEmail(p_email)!= null){
			info.put("result", false);
			info.put("info", "user exist");
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
		
		User user =new User();
		user.setNickName(p_name);
		user.setPwdMD5Hash(HashGeneratorUtils.generateSaltMD5(p_password));
		
		userInfoService.insertUser(user);
		request.getSession(true);
		List<User> userList = userInfoService.getUserByName(p_name);
		User registerUser = null;
		for(User n_user : userList)
			if(HashGeneratorUtils.generateSaltMD5(p_password).equals(n_user.getPwdMD5Hash())){
				registerUser = n_user;
				break;
			}
		if(registerUser == null){
			info.put("result", false);
			info.put("info", "insert wrong");
			return info;
		}
		int userId = registerUser.getUserId();
		request.getSession(true);
		request.getSession().setAttribute("userId",userId);
		request.getSession().setAttribute("isRegister",true);
		info.put("result", true);
		info.put("info", "register success");
		info.put("userId", userId);
		
		
		String mailString="这是验证邮件，请访问如下网址：";
		mailString = mailString + "localhost:8080/Roommates/api/register/usercheck";
		mailString = mailString + "?checkid=" + (userId+"");
		mailString = mailString + "&checkemail=" + p_email;
		mailString = mailString + "&checkname=" + HashGeneratorUtils.generateSaltMD5(p_name);
		MailSender mailsender = new DefaultMailSender();
		mailsender.setReceiver(p_email);
		mailsender.setSubject("验证邮件");
		mailsender.setContent(mailString);
		mailsender.send();
		System.out.println("mail send");
		return info;
	}
}