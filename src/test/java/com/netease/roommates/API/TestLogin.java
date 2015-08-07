package com.netease.roommates.API;

import org.testng.annotations.Test;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.apache.http.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair; 

import java.io.IOException;

public class TestLogin {
  @Test
  public void test1() throws UnsupportedEncodingException { 
	  CloseableHttpClient httpclient = HttpClients.createDefault();  
      // 创建httppost    
      HttpPost httppost = new HttpPost("http://223.252.223.13/Roommates/api/login");
       
      // 创建参数队列    
      JSONObject jsonObj = new JSONObject();
	  jsonObj.put("email", "hztest@corp.netease.com");
	  jsonObj.put("password", "123456");
	  StringEntity entity = new StringEntity(jsonObj.toString());
	  entity.setContentType("application/json");
	  httppost.setEntity(entity);
      httppost.setHeader("Content-Type", "application/json");
      try {   
          CloseableHttpResponse response = httpclient.execute(httppost);  
          try {  
              HttpEntity entityAfter = response.getEntity();  
              if (entityAfter != null) {  
                  System.out.println("--------------------------------------");  
                  System.out.println("Response content: " + EntityUtils.toString(entityAfter, "UTF-8"));  
                  System.out.println("--------------------------------------");  
              }  
          } finally {  
              response.close();  
          }  
      } catch (ClientProtocolException e) {  
          e.printStackTrace();  
      } catch (UnsupportedEncodingException e1) {  
          e1.printStackTrace();  
      } catch (IOException e) {  
          e.printStackTrace();  
      } finally {  
          // 关闭连接,释放资源    
          try {  
              httpclient.close();  
          } catch (IOException e) {  
              e.printStackTrace();  
          }  
      }  
  }
}
