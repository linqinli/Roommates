package com.netease.common.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class emailAddress {
	private static Map<Pattern, String> emailPattern = new HashMap<Pattern, String>();
	static{
		Pattern neteaseEmail = Pattern.compile(".*@corp.netease.com");
		Pattern alibabaEmail = Pattern.compile(".*@alibaba-inc.com");
		Pattern hikvisEmail = Pattern.compile(".*@hikvision.com");
		Pattern dahuaEmail = Pattern.compile(".*@dahuatech.com");
		Pattern utEmail = Pattern.compile(".*@utstar.com");
		emailPattern.put(neteaseEmail, "网易");
		emailPattern.put(alibabaEmail, "阿里巴巴");
		emailPattern.put(hikvisEmail, "海康威视");
		emailPattern.put(dahuaEmail, "大华");
		emailPattern.put(utEmail, "UT斯达康");
	}
	
	public static boolean emailCheck(String email){
		boolean flag = false;
		for(Pattern pattern : emailPattern.keySet()){
			Matcher matcher = pattern.matcher(email);
			if(matcher.matches()){
				flag=true;
				break;
			}
		}
		
		return flag;
	}
	
	public static String getCompany(String email){
		for(Pattern pattern : emailPattern.keySet()){
			Matcher matcher = pattern.matcher(email);
			if(matcher.matches()){
				return emailPattern.get(pattern);
			}
		}
		
		return null;
	}
}
