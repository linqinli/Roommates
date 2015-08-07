package com.netease.roommates.API;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.apache.commons.httpclient.*;
import org.json.*;
import java.io.IOException;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.commons.httpclient.methods.PostMethod;
public class TestLoginController {
	@DataProvider
	 public Object[][] dataOK() {
		 return new Object[][]{
		  {"hztest@corp.netease.com","123456"},
		  {"hzsuixiang@corp.netease.com","123456"},
		 };
	 }
	@DataProvider
	public Object[][] dataWrongPassword() {
		 return new Object[][]{
		  {"hzsuixiang@corp.netease.com","123456789"},
		 };
	 }
	@DataProvider
	public Object[][] dataWrongEmail() {
		 return new Object[][]{
		  {"hzsuixiang","123456789"}
		 };
	 }
   @Test(dataProvider = "dataOK")
   public  void TestLoginOK(String email,String password) throws Exception {  
          HttpClient httpclient = new HttpClient();  
          PostMethod login = new PostMethod("http://223.252.223.13/Roommates/api/login");
          JSONObject jsonObj = new JSONObject();
    	  jsonObj.put("email", email);
    	  jsonObj.put("password", password); 
    	  RequestEntity entity = new StringRequestEntity(jsonObj.toString(),"application/json","utf-8");
    	  login.addRequestHeader("Content-Type", "application/json");
    	  login.setRequestEntity(entity);
          int status = httpclient.executeMethod(login);
          String response = login.getResponseBodyAsString();
          System.out.println(response);
          login.releaseConnection();
          System.out.println(status);
          Assert.assertEquals(status, 200);
      }  
   @Test(dataProvider = "dataWrongPassword")
   public  void TestLoginWrongPass(String email,String password) throws Exception {  
       HttpClient httpclient = new HttpClient();  
       PostMethod login = new PostMethod("http://223.252.223.13/Roommates/api/login");
       JSONObject jsonObj = new JSONObject();
 	  jsonObj.put("email", email);
 	  jsonObj.put("password", password); 
 	  RequestEntity entity = new StringRequestEntity(jsonObj.toString(),"application/json","utf-8");
 	  login.addRequestHeader("Content-Type", "application/json");
 	  login.setRequestEntity(entity);
       int status = httpclient.executeMethod(login);
       String response = login.getResponseBodyAsString();
       System.out.println(response);
       String resultAll[] = response.split(",");
       String result[] = resultAll[0].split(":");
       Assert.assertEquals(result[1], "0");
       String info[] = resultAll[1].split(":");
       Assert.assertEquals(info[1], "\"密码输入不正确，请重新输入\"}");
       login.releaseConnection();
       System.out.println(status);
       Assert.assertEquals(status, 200);
   }  
   @Test(dataProvider = "dataWrongEmail")
   public  void TestLoginWrongEmail(String email,String password) throws Exception {  
       HttpClient httpclient = new HttpClient();  
       PostMethod login = new PostMethod("http://223.252.223.13/Roommates/api/login");
       JSONObject jsonObj = new JSONObject();
 	  jsonObj.put("email", email);
 	  jsonObj.put("password", password); 
 	  RequestEntity entity = new StringRequestEntity(jsonObj.toString(),"application/json","utf-8");
 	  login.addRequestHeader("Content-Type", "application/json");
 	  login.setRequestEntity(entity);
       int status = httpclient.executeMethod(login);
       String response = login.getResponseBodyAsString();
       System.out.println(response);
       String resultAll[] = response.split(",");
       String result[] = resultAll[0].split(":");
       Assert.assertEquals(result[1], "0");
       String info[] = resultAll[1].split(":");
       Assert.assertEquals(info[1], "\"请输入您的企业邮箱\"}");
       login.releaseConnection();
       System.out.println(status);
       Assert.assertEquals(status, 200);
   }  
   
}
