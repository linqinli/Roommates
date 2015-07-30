package com.netease.match.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.netease.exception.ServiceException;
import com.netease.match.service.IMatchDataService;

@Service
public class MatchDataService implements IMatchDataService {
	private Logger log = LoggerFactory.getLogger(MatchDataService.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List selectAllUsers() throws ServiceException {
		// TODO Auto-generated method stub
		log.debug("MatchDataService.selectAllUsers");
		List users = jdbcTemplate.queryForList("select * from sys_user");
		return users;
	}
	
	@Override
	public List<Integer> getUserIdListByCondition(int xb, int f, int gs, int cy, int cw,
			int zx, int ws, int xg, int fk){
		String selectSqlString = "";
		
		if(xb*f*gs*cy*cw*zx*ws*xg*fk == 1){
			selectSqlString = "select userId from sys_user";
		}
		else if(f*cy*cw*zx*ws*xg*fk == 1){
			selectSqlString = "select userId from sys_user where";
			switch(xb){
			case 2: selectSqlString += " gender=0 and"; break;
			case 3: selectSqlString += " gender=1 and"; break;
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
			selectSqlString = "select s.userId from sys_user s join user_personality p "
					+ "on s.userId = p.userId where";
			
			switch(xb){
			case 2: selectSqlString += " s.gender=0 and"; break;
			case 3: selectSqlString += " s.gender=1 and"; break;
			default: break;
			}
			
			switch(f){
			case 2: selectSqlString += " p.hasHouse=1 and"; break;
			case 3: selectSqlString += " p.hasHouse=0 and"; break;
			default: break;
			}
			
			switch(gs){
			case 2: selectSqlString += " s.company=2 and"; break;
			case 3: selectSqlString += " s.company=3 and"; break;
			case 4: selectSqlString += " s.company=4 and"; break;
			case 5: selectSqlString += " s.company=5 and"; break;
			case 6: selectSqlString += " s.company=6 and"; break;
			default: break;
			}
			switch(cy){
			case 2: selectSqlString += " p.smoking=1 and"; break;
			case 3: selectSqlString += " p.smoking=2 and"; break;
			case 4: selectSqlString += " p.smoking=3 and"; break;
			default: break;
			}

			switch(cw){
			case 2: selectSqlString += " p.pet=1 and"; break;
			case 3: selectSqlString += " p.pet=2 and"; break;
			case 4: selectSqlString += " p.pet=3 and"; break;
			default: break;
			}

			switch(zx){
			case 2: selectSqlString += " p.dailySchedule=1 and"; break;
			case 3: selectSqlString += " p.dailySchedule=2 and"; break;
			case 4: selectSqlString += " p.dailySchedule=3 and"; break;
			default: break;
			}
			switch(ws){
			case 2: selectSqlString += " p.cleanliness=1 and"; break;
			case 3: selectSqlString += " p.cleanliness=2 and"; break;
			case 4: selectSqlString += " p.cleanliness=3 and"; break;
			default: break;
			}
			switch(xg){
			case 2: selectSqlString += " p.personCharacter=1 and"; break;
			case 3: selectSqlString += " p.personCharacter=2 and"; break;
			case 4: selectSqlString += " p.personCharacter=3 and"; break;
			default: break;
			}
			switch(fk){
			case 2: selectSqlString += " p.visitor=1 and"; break;
			case 3: selectSqlString += " p.visitor=2 and"; break;
			case 4: selectSqlString += " p.visitor=3 and"; break;
			default: break;
			}
			
			String suffix = selectSqlString.substring(selectSqlString.length()-4, 
					selectSqlString.length());
			
			if(suffix.equals(" and")){
				selectSqlString = selectSqlString.substring(0, 
						selectSqlString.length()-4);
			}
		}
		List<Integer> userIdList = (List<Integer>)jdbcTemplate.queryForList(selectSqlString, Integer.class);;
		return userIdList;
	}
	
	

}
