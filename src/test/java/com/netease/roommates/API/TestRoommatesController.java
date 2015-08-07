package com.netease.roommates.API;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.cookie.CookiePolicy;
//import org.apache.http.cookie.*;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
//import org.apache.http.cookie.Cookie;
import org.apache.commons.httpclient.Cookie;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestRoommatesController {
	@DataProvider
  public Object[][] dataOneParams() {
		 return new Object[][]{
		  {"p","1"},
		  {"p","2"},
		  {"p","3"},
		  {"xb","1"},
		  {"xb","2"},
		  {"xb","3"},{"f","1"},{"f","2"},{"f","3"},
		  {"gs","1"},{"gs","2"},{"gs","3"},{"gs","4"},{"gs","5"},{"gs","6"},
		  {"cy","1"},{"cy","2"},{"cy","3"},{"cy","4"},
		  {"cw","1"},{"cw","2"},{"cw","3"},{"cw","4"},
		  {"zx","1"},{"zx","2"},{"zx","3"},
		  {"ws","1"},{"ws","2"},{"ws","3"},{"ws","4"},
		  {"xg","1"},{"xg","2"},{"xg","3"},{"xg","4"},
		  {"fk","1"},{"fk","2"},{"fk","3"},{"fk","4"},
		 };
	 }
  @Test(dataProvider = "dataOneParams")
  public void Roommates(String xingge,String num) throws UnsupportedEncodingException {
	  HttpClient httpclient = new HttpClient();  
      PostMethod login = new PostMethod("http://223.252.223.13/Roommates/api/login");
      JSONObject jsonObj = new JSONObject();
	  jsonObj.put("email", "hztest@corp.netease.com");
	  jsonObj.put("password", "123456"); 
	  RequestEntity entity = new StringRequestEntity(jsonObj.toString(),"application/json","utf-8");
	  login.addRequestHeader("Content-Type", "application/json");
	  login.setRequestEntity(entity);
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
	   String getURL="http://223.252.223.13/Roommates/api/people/list?id=29&"+xingge+"="+num;
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
