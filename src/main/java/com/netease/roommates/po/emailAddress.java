package com.netease.roommates.po;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class emailAddress {
	public static List<Pattern> emailPattern = new ArrayList<Pattern>();
	static{
		Pattern neteaseEmail = Pattern.compile(".*@corp.netease.com");
		Pattern alibabaEmail = Pattern.compile(".*@alibaba-inc.com");
		emailPattern.add(neteaseEmail);
		emailPattern.add(alibabaEmail);
	
	}
}
