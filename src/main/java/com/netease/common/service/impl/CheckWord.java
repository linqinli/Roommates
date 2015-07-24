package com.netease.common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;


@Service
public class CheckWord{
	public static boolean check(String parms) 
	{ 
		if (parms == null) 
			return false; 		
		List<String> pattern = new ArrayList();
		pattern.add("sp_");
		pattern.add("create");
		pattern.add("'");
		pattern.add("drop");
		pattern.add("select");
		pattern.add("\"");
		pattern.add("exec");
		pattern.add("xp_");
		pattern.add("insert");
		pattern.add("update");
		pattern.remove("update");
		
		for(String keyWord : pattern)
			if(parms.indexOf(keyWord)!=-1)
				return true;
		return false;
		
	}

}
