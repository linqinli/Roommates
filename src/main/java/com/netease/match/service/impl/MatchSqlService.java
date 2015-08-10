package com.netease.match.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.netease.match.service.IMatchSqlService;
import com.netease.roommates.controller.UserController;

@Service
public class MatchSqlService implements IMatchSqlService {
	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@Override
	public String generateSqlStrByCondition(int id, int xb, int f, int gs, int cy, int cw, int zx, int ws, int xg,
			int fk) {
		String selectSqlString = "";
		
		if(f==1){
			if(cy*cw*zx*ws*xg*fk == 1){
				selectSqlString = "select distinct s.userId from sys_user s join user_personality p on s.userId=p.userId where s.userId!="+id + 
						" and s.phoneNumber is not null and";
				selectSqlString = selectSqlString + generateXbSql(xb) + generateGsSql(gs);
			}
			else{
				selectSqlString = "select distinct s.userId from sys_user s join user_personality p "
						+ "on s.userId = p.userId where s.userId!="+id+" and s.phoneNumber is not null and";
				selectSqlString = selectSqlString + this.generateXbSql(xb) + this.generateGsSql(gs)
				+this.generateCySql(cy) + this.generateCwSql(cw) + this.generateZxSql(zx)
				+this.generateWsSql(ws)+this.generateXgSql(xg) + this.generateFkSql(fk);
			}
		}
		else {
			// 有房
			if(f==2){
				if(cy*cw*zx*ws*xg*fk == 1){
					selectSqlString = "select distinct s.userId from sys_user s "
							+ "join fn_house f on s.userId=f.userId join user_personality p on s.userId=p.userId "
							+ "where s.phoneNumber is not null and s.userId!="+id + " and";
					selectSqlString = selectSqlString+this.generateXbSql(xb)+this.generateGsSql(gs);
				}
				else{
					selectSqlString = "select distinct s.userId from sys_user s "
							+ "join user_personality p "
							+ "on s.userId = p.userId join fn_house f on s.userId=f.userId "
							+ "where s.phoneNumber is not null and s.userId!="+id+" and";
					selectSqlString = selectSqlString + this.generateXbSql(xb) + this.generateGsSql(gs)
					+this.generateCySql(cy) + this.generateCwSql(cw) + this.generateZxSql(zx)
					+this.generateWsSql(ws)+this.generateXgSql(xg) + this.generateFkSql(fk);
				}
				
			}
			else if(f==3){
				if(cy*cw*zx*ws*xg*fk == 1){
					selectSqlString = "select distinct s.userId from sys_user s "
							+ "left join fn_house f on s.userId=f.userId"
							+ " join user_personality p on s.userId=p.userId where f.userId is null and s.phoneNumber is not null "
							+ "and s.userId!="+id + " and";
					selectSqlString = selectSqlString+this.generateXbSql(xb)+this.generateGsSql(gs);
				}
				else{
					selectSqlString = "select distinct s.userId from sys_user s "
							+ "left join fn_house f on s.userId=f.userId"
							+ " join user_personality p on s.userId = p.userId  "
							+ "where f.userId is null and s.phoneNumber is not null and s.userId!="+id+" and";
					
					selectSqlString = selectSqlString + this.generateXbSql(xb) + this.generateGsSql(gs)
					+this.generateCySql(cy) + this.generateCwSql(cw) + this.generateZxSql(zx)
					+this.generateWsSql(ws)+this.generateXgSql(xg) + this.generateFkSql(fk);
				}
			}
		}
		
		selectSqlString = selectSqlString + " s.userId not in (select r.hate from roommates_hate r where r.userId="+id+")";
		
		String suffix = selectSqlString.substring(selectSqlString.length()-4, 
				selectSqlString.length());
		
		if(suffix.equals(" and")){
			selectSqlString = selectSqlString.substring(0, 
					selectSqlString.length()-4);
		}
		logger.info("Execute Sql String : " + selectSqlString);
		return selectSqlString;
	}

	@Override
	public String generateXbSql(int xb) {
		String sqlString="";
		switch(xb){
		case 2: sqlString += " s.gender=0 and"; break;
		case 3: sqlString += " s.gender=1 and"; break;
		default: break;
		}
		return sqlString;
	}

	@Override
	public String generateGsSql(int gs) {
		String sqlString="";
		switch(gs){
		case 2: sqlString += " s.company='网易' and"; break;
		case 3: sqlString += " s.company='阿里巴巴' and"; break;
		case 4: sqlString += " s.company='大华' and"; break;
		case 5: sqlString += " s.company='UT斯达康' and"; break;
		case 6: sqlString += " s.company='海康威视' and"; break;
		default: break;
		}
		return sqlString;
	}

	@Override
	public String generateCySql(int cy) {
		String sqlString="";
		switch(cy){
		case 2: sqlString += " p.smoking=2 and"; break;
		case 3: sqlString += " p.smoking=3 and"; break;
		case 4: sqlString += " p.smoking=4 and"; break;
		default: break;
		}
		return sqlString;
	}

	@Override
	public String generateZxSql(int zx) {
		String sqlString="";
		switch(zx){
		case 2: sqlString += " p.dailySchedule=2 and"; break;
		case 3: sqlString += " p.dailySchedule=3 and"; break;
		default: break;
		}
		return sqlString;
	}

	@Override
	public String generateWsSql(int ws) {
		String sqlString="";
		switch(ws){
		case 2: sqlString += " p.cleanliness=2 and"; break;
		case 3: sqlString += " p.cleanliness=3 and"; break;
		case 4: sqlString += " p.cleanliness=4 and"; break;
		default: break;
		}
		return sqlString;
	}

	@Override
	public String generateXgSql(int xg) {
		String sqlString="";
		switch(xg){
		case 2: sqlString += " p.personCharacter=2 and"; break;
		case 3: sqlString += " p.personCharacter=3 and"; break;
		case 4: sqlString += " p.personCharacter=4 and"; break;
		default: break;
		}
		return sqlString;
	}

	@Override
	public String generateFkSql(int fk) {
		String sqlString="";
		switch(fk){
		case 2: sqlString += " p.visitor=2 and"; break;
		case 3: sqlString += " p.visitor=3 and"; break;
		case 4: sqlString += " p.visitor=4 and"; break;
		default: break;
		}
		return sqlString;
	}

	@Override
	public String generateCwSql(int cw) {
		String sqlString="";
		switch(cw){
		case 2: sqlString += " p.pet=2 and"; break;
		case 3: sqlString += " p.pet=3 and"; break;
		case 4: sqlString += " p.pet=4 and"; break;
		default: break;
		}
		return sqlString;
	}

}
