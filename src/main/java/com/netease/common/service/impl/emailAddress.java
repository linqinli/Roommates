package com.netease.common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class emailAddress {
	private static List<Pattern> emailPattern = new ArrayList<Pattern>();
	static{
		Pattern neteaseEmail = Pattern.compile(".*@corp.netease.com");
		Pattern alibabaEmail = Pattern.compile(".*@alibaba-inc.com");
		emailPattern.add(neteaseEmail);
		emailPattern.add(alibabaEmail);
	}
	
	public static boolean emailCheck(String email){
		boolean flag = false;
		for(Pattern pattern : emailPattern){
			Matcher matcher = pattern.matcher(email);
			if(matcher.matches()){
				flag=true;
				break;
			}
		}
		
		return flag;
	}
}
