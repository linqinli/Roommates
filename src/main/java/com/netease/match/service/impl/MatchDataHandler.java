package com.netease.match.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.netease.roommates.po.User;

@Service
public class MatchDataHandler {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<User> selectUserByCondition(int xb, int f, int gs, int cy, int cw,
			int zx, int ws, int xg, int fk){
		List<User> userList = new ArrayList<User>();
		String selectSqlString = "";
		
		if(xb*f*gs*cy*cw*zx*ws*xg*fk == 1){
			selectSqlString = "select * from sys_user";
		}
		else if(cy*cw*zx*ws*xg*fk == 1){
			selectSqlString = "select * from sys_user where ";
			switch(xb){
			case 2: selectSqlString += " gender=0 and"; break;
			case 3: selectSqlString += " gender=1 and"; break;
			default: break;
			}
			
			switch(f){
			case 2: selectSqlString += " house=2 and"; break;
			case 3: selectSqlString += " house=3 and"; break;
			default: break;
			}
			
			switch(gs){
			case 2: selectSqlString += " company=2 and"; break;
			case 3: selectSqlString += " company=3 and"; break;
			case 4: selectSqlString += " company=4 and"; break;
			case 5: selectSqlString += " company=5 and"; break;
			case 6: selectSqlString += " company=6 and"; break;
			default: break;
			}
			
			String suffix = selectSqlString.substring(selectSqlString.length()-4, 
					selectSqlString.length());
			
			if(suffix.equals(" and")){
				selectSqlString = selectSqlString.substring(0, 
						selectSqlString.length()-4);
			}
			
		}
		else{
			selectSqlString = "select * from sys_user s join user_personality p "
					+ "on s.userId = p.userId where";
			
			switch(xb){
			case 2: selectSqlString += " gender=0 and"; break;
			case 3: selectSqlString += " gender=1 and"; break;
			default: break;
			}
			
			switch(f){
			case 2: selectSqlString += " house=2 and"; break;
			case 3: selectSqlString += " house=3 and"; break;
			default: break;
			}
			
			switch(gs){
			case 2: selectSqlString += " company=2 and"; break;
			case 3: selectSqlString += " company=3 and"; break;
			case 4: selectSqlString += " company=4 and"; break;
			case 5: selectSqlString += " company=5 and"; break;
			case 6: selectSqlString += " company=6 and"; break;
			default: break;
			}
			switch(cy){
			case 2: selectSqlString += " cy=2 and"; break;
			case 3: selectSqlString += " cy=3 and"; break;
			default: break;
			}

			switch(cw){
			case 2: selectSqlString += " cw=2 and"; break;
			case 3: selectSqlString += " cw=3 and"; break;
			default: break;
			}

			switch(zx){
			case 2: selectSqlString += " zx=2 and"; break;
			case 3: selectSqlString += " zx=3 and"; break;
			default: break;
			}
			switch(ws){
			case 2: selectSqlString += " ws=2 and"; break;
			case 3: selectSqlString += " ws=3 and"; break;
			default: break;
			}
			switch(xg){
			case 2: selectSqlString += " xg=2 and"; break;
			case 3: selectSqlString += " xg=3 and"; break;
			default: break;
			}
			switch(fk){
			case 2: selectSqlString += " fk=2 and"; break;
			case 3: selectSqlString += " fk=3 and"; break;
			default: break;
			}
			
			String suffix = selectSqlString.substring(selectSqlString.length()-4, 
					selectSqlString.length());
			
			if(suffix.equals(" and")){
				selectSqlString = selectSqlString.substring(0, 
						selectSqlString.length()-4);
			}
		}
		
		userList = (List<User>)jdbcTemplate.queryForList(selectSqlString,User.class);
		
		return userList;
	}

}
