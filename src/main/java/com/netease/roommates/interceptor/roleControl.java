package com.netease.roommates.interceptor;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.netease.roommates.po.role;


public class roleControl extends HandlerInterceptorAdapter {
	
	private List<String> userAllow(role user){
		List<String> checked_list = new ArrayList<String>();
		List<String> register_list = new ArrayList<String>();
		List<String> out_list = new ArrayList<String>();
		List<String> vistor_list = new ArrayList<String>();
		
		register_list.add("/api/login");
		out_list.add("/api/login");
		vistor_list.add("/api/login");
		register_list.add("/api/register");
		out_list.add("/api/register");
		vistor_list.add("/api/register");
		
		switch(user){
			case checked: return checked_list;
			case register: return register_list;
			case out_user: return out_list;
			default: return vistor_list;
		}
	}
	
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {  
	  System.out.println("HandlerInterceptor1 preHandle");
	  
	  request.setCharacterEncoding("utf-8");  
	  role user;
	  if(request.getSession().getAttribute("isChecked")!=null && (boolean)request.getSession().getAttribute("isChecked"))
		  user = role.checked;
	  else if(request.getSession().getAttribute("isRegister")!=null && (boolean) request.getSession().getAttribute("isRegister"))
		  user = role.register;
	  else if(request.getSession().getAttribute("isLogin")!=null && (boolean) request.getSession().getAttribute("isLogin"))
		  user = role.out_user;
	  else
		  user = role.vistor;
	  
	  System.out.println("role is "+user);
	  /*
	  List<String> allowAddress = userAllow(user);
	  boolean flag = false;
	  if(user == role.checked)
		  flag = true;
	  for(String address : allowAddress){
		  if(request.getServletPath().startsWith(address)){
			  	flag = true;
			  	break;
		  }
	  }
	  
	  if(!flag){
		  System.out.println("to  "+request.getContextPath()+"/api/login/page");
		  response.sendRedirect(request.getContextPath()+"/api/login/page");
		  System.out.println("HandlerInterceptor1 preHandle");
		  return false;
	  }*/
      return true;
  }
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
      System.out.println("HandlerInterceptor1 postHandle");
  }
  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
      System.out.println("HandlerInterceptor1 afterCompletion");
  }
}