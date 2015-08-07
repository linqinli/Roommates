package com.netease.roommates.API;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestRoomatesDetail {
  @Test
  public void f() {
	  HttpClient httpclient = new HttpClient();  
      PostMethod login = new PostMethod("http://223.252.223.13/Roommates/api/login");
      JSONObject jsonObj = new JSONObject();
	  jsonObj.put("email", "hztest@corp.netease.com");
	  jsonObj.put("password", "123456"); 	  
	try {
		RequestEntity entity;
		entity = new StringRequestEntity(jsonObj.toString(),"application/json","utf-8");
		login.setRequestEntity(entity);
	} catch (UnsupportedEncodingException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	  login.addRequestHeader("Content-Type", "application/json");
	  
      int status;
	try {
		//设置HttpClient接收Cookie，用与浏览器一样的策略
		httpclient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		//执行登陆
		status = httpclient.executeMethod(login);
		System.out.println(status); 
		//获得登陆后的Cookie
		Cookie[] cookies =  httpclient.getState().getCookies();
		StringBuffer tmpcookies = new StringBuffer();
		for(Cookie c:cookies){
			tmpcookies.append(c.toString()+";");
		
         String response;
		response = login.getResponseBodyAsString();
		System.out.println(response);
	    login.releaseConnection();
	
	//开始获取室友信息
	   String getURL="http://223.252.223.13/Roommates/api/people/detail/33";
	  GetMethod getMethod = new GetMethod(getURL);
	// 每次访问需授权的网址时需带上前面的 cookie 作为通行证  
    getMethod.setRequestHeader("cookie", tmpcookies.toString()); 
    httpclient.executeMethod(getMethod); 
    // 打印出返回数据，检验一下是否成功  
    String text = getMethod.getResponseBodyAsString();  
    System.out.println(text); 
    String errAll[]=text.split(",");
    String err[]=errAll[0].split(":");
    Assert.assertEquals(err[1], "0");
		}
	} catch (HttpException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
  }
}
