package com.netease.match.service.impl;

import java.util.*;

import com.netease.exception.ServiceException;
import com.netease.roommates.po.User;
import com.netease.roommates.vo.MatchUserSimpleInfo;
import com.netease.roommates.po.Personality;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class MatchPersonality {
	private User curUser;
	
	public MatchPersonality(User user){
		setCurUser(user);
	}
	
	static class MatchScoreAndMessage{
		int matchScore;
		String matchMessage;
	}
	
	public List<MatchUserSimpleInfo> matchResultSimpleInfo(List<User> users) throws ServiceException{
		List<MatchUserSimpleInfo> matchUserInfo = new ArrayList<MatchUserSimpleInfo>();
		
		for(int i=0; i<users.size(); ++i){
			User user = users.get(i);
			MatchScoreAndMessage matchScoreAndMessage = new MatchScoreAndMessage();
			if(this.curUser.getPersonality()!=null && user.getPersonality()!=null){
				matchScoreAndMessage = getSimilarityBetweenTwoPersonality(
						this.curUser.getPersonality(), user.getPersonality());
			}
			MatchUserSimpleInfo userTmpInfo = userInfoToMatchUserSimpleInfo(user);
			userTmpInfo.setMatchScore(matchScoreAndMessage.matchScore);
			userTmpInfo.setMatchMessage(matchScoreAndMessage.matchMessage);
			matchUserInfo.add(userTmpInfo);
		}
		// 按分数高低进行排序
		Collections.sort(matchUserInfo,new Comparator<MatchUserSimpleInfo>(){
			@Override
			public int compare(MatchUserSimpleInfo matchUserInfo1, MatchUserSimpleInfo matchUserInfo2){
				return matchUserInfo2.getMatchScore()-matchUserInfo1.getMatchScore();
			}
		});
		return matchUserInfo;
	}

	public User getCurUser() {
		return curUser;
	}

	public void setCurUser(User curUser) {
		this.curUser = curUser;
	}
	
	public List<Integer> selectUserIdByCondition(int xb, int f, int gs, int cy, int cw,
			int zx, int ws, int xg, int fk){
		String selectSqlString = "";
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://223.252.223.13:3306/roommates");
		dataSource.setUsername("hznetease");
		dataSource.setPassword("hz12345");
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);
		if(xb*f*gs*cy*cw*zx*ws*xg*fk == 1){
			selectSqlString = "select userId from sys_user";
		}
		else if(cy*cw*zx*ws*xg*fk == 1){
			selectSqlString = "select userId from sys_user where";
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
			selectSqlString = "select s.userId from sys_user s join user_personality p "
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
			case 2: selectSqlString += " smoking=1 and"; break;
			case 3: selectSqlString += " smoking=2 and"; break;
			case 4: selectSqlString += " smoking=3 and"; break;
			default: break;
			}

			switch(cw){
			case 2: selectSqlString += " pet=1 and"; break;
			case 3: selectSqlString += " pet=2 and"; break;
			case 4: selectSqlString += " pet=3 and"; break;
			default: break;
			}

			switch(zx){
			case 2: selectSqlString += " dailySchedule=1 and"; break;
			case 3: selectSqlString += " dailySchedule=2 and"; break;
			case 4: selectSqlString += " dailySchedule=3 and"; break;
			default: break;
			}
			switch(ws){
			case 2: selectSqlString += " cleanliness=1 and"; break;
			case 3: selectSqlString += " cleanliness=2 and"; break;
			case 4: selectSqlString += " cleanliness=3 and"; break;
			default: break;
			}
			switch(xg){
			case 2: selectSqlString += " personCharacter=1 and"; break;
			case 3: selectSqlString += " personCharacter=2 and"; break;
			case 4: selectSqlString += " personCharacter=3 and"; break;
			default: break;
			}
			switch(fk){
			case 2: selectSqlString += " visitor=1 and"; break;
			case 3: selectSqlString += " visitor=2 and"; break;
			case 4: selectSqlString += " visitor=3 and"; break;
			default: break;
			}
			
			String suffix = selectSqlString.substring(selectSqlString.length()-4, 
					selectSqlString.length());
			
			if(suffix.equals(" and")){
				selectSqlString = selectSqlString.substring(0, 
						selectSqlString.length()-4);
			}
		}
		
		// userList = (List<User>)jdbcTemplate.queryForList(selectSqlString,User.class);
		
		List<Integer> userIdList= (List<Integer>)jdbcTemplate.queryForList(selectSqlString, Integer.class);
		
		
		return userIdList;
	}
	
	// 通过问卷选项，计算两用户行为偏好的匹配得分
	public MatchScoreAndMessage getSimilarityBetweenTwoPersonality(Personality per1, Personality per2){
		MatchScoreAndMessage matchScoreAndMessage = new MatchScoreAndMessage();
		int matchScore = 0;
		String matchMessage = "TA";
		
		// 权重 分配
		int zxWeight = 1; // 作息选项权重
		int cyWeight = 1; // 抽烟选项权重
		int cwWeight = 1; // 宠物选项权重
		int fkWeight = 1; // 访客选项权重
		int grwsWeight = 1; // 个人卫生选项权重
		int xgWeight = 1; // 性格选项权重
		int xzWeight = 1; // 星座选项权重

		// 作息选项匹配得分计算
		int user1DailySchedule = per1.getDailySchedule();
		int user2DailySchedule = per2.getDailySchedule();
		if(user1DailySchedule == user2DailySchedule){
			matchScore += zxWeight*1;
			matchMessage = matchMessage + " 作息 ";
		}
		else matchScore += zxWeight*(-1);

		// 抽烟选项匹配得分计算
		int user1Smoking = per1.getSmoking();
		int user2Smoking = per2.getSmoking();
		user1Smoking -= 2;
		user2Smoking -= 2;
		if(user1Smoking == user2Smoking){
			matchScore += cyWeight*1;
			matchMessage = matchMessage + " 抽烟 ";
		}
		else matchScore += cyWeight*(user1Smoking*user2Smoking);

		// 宠物选项匹配得分计算
		int user1Pet = per1.getPet();
		int user2Pet = per2.getPet();
		user1Pet -= 2;
		user2Pet -= 2;
		if(user1Pet == user2Pet){
			matchScore += cwWeight*1;
			matchMessage = matchMessage + " 宠物 ";
		}
		else matchScore += cwWeight*(user1Pet*user2Pet);

		// 访客选项匹配得分计算
		int user1Visitor = per1.getVisitor();
		int user2Visitor = per2.getVisitor();
		user1Visitor -= 2;
		user2Visitor -= 2;
		if(user1Visitor == user2Visitor){
			matchScore += fkWeight*1;
			matchMessage = matchMessage + " 访客 ";
		}
		else matchScore += fkWeight*(user1Visitor*user2Visitor);

		// 个人卫生选项匹配得分计算
		int user1Sanitation = per1.getCleanliness();
		int user2Sanitation = per2.getCleanliness();
		user1Sanitation -= 2;
		user2Sanitation -= 2;
		if(user1Sanitation == user2Sanitation){
			matchScore += grwsWeight*1;
			matchMessage = matchMessage + " 个人卫生 ";
		}
		else matchScore += grwsWeight*(user1Sanitation*user2Sanitation);

		// 性格选项匹配得分计算
		int user1Character = per1.getPersonCharacter();
		int user2Character = per2.getPersonCharacter();
		user1Character -= 2;
		user2Character -= 2;
		if(user1Character == user2Character){
			matchScore += xgWeight*1;
			matchMessage = matchMessage + " 性格 ";
		}
		else matchScore += xgWeight*(user1Character*user2Character);

		// 星座选项匹配得分计算
		int user1Constellation = this.queryConstellation(per1.getConstellation());
		int user2Constellation = this.queryConstellation(per2.getConstellation());
		if(user1Constellation == user2Constellation){
			matchScore += xzWeight*1;
			matchMessage = matchMessage + " 星座 ";
		}

		// 归一化处理
		int totalWeight = 2*zxWeight+2*cyWeight+2*cwWeight+2*fkWeight+2*grwsWeight+2*xgWeight+1*xzWeight;
		matchScore = matchScore+zxWeight+cyWeight+cwWeight+fkWeight+grwsWeight+xgWeight;
		matchScore = (int)(100*(double)matchScore/(double)totalWeight);
		
		if(matchMessage == "TA") matchMessage = " ";
		else matchMessage += "与您相同！";
		
		matchScoreAndMessage.matchScore = matchScore;
		matchScoreAndMessage.matchMessage = matchMessage;
		
		return matchScoreAndMessage;
	}
	
	public MatchScoreAndMessage getVectorSimilarityBetweenTwoPersonality(Personality per1, Personality per2){
		MatchScoreAndMessage matchScoreAndMessage = new MatchScoreAndMessage();
		
		ArrayList<Integer> vec1 = new ArrayList<Integer>();
		ArrayList<Integer> vec2 = new ArrayList<Integer>();
		
		/*int zxWeight = 1; // 作息选项权重
		int cyWeight = 1; // 抽烟选项权重
		int cwWeight = 1; // 宠物选项权重
		int fkWeight = 1; // 访客选项权重
		int grwsWeight = 1; // 个人卫生选项权重
		int xgWeight = 1; // 性格选项权重
		int xzWeight = 1; // 星座选项权重*/
		int zxWeight=1, cyWeight=1, cwWeight=1, fkWeight=1, grwsWeight=1, xgWeight=1, xzWeight=1;
		
		
		setEvaluateWeight(zxWeight, cyWeight, cwWeight, fkWeight, grwsWeight, xgWeight, xzWeight);
		
		// 作息
		if(per1.getDailySchedule() == 1) vec1.add(-1*zxWeight); else vec1.add(1*zxWeight);
		if(per2.getDailySchedule() == 1) vec2.add(-1*zxWeight); else vec2.add(1*zxWeight);
		
		// 抽烟
		vec1.add((per1.getSmoking()-2)*cyWeight);
		vec2.add((per2.getSmoking()-2)*cyWeight);
		
		//  宠物
		vec1.add((per1.getPet()-2)*cwWeight);
		vec2.add((per2.getPet()-2)*cwWeight);
		
		// 访客
		vec1.add((per1.getVisitor()-2)*fkWeight);
		vec2.add((per2.getVisitor()-2)*fkWeight);
		
		// 个人卫生
		vec1.add((per1.getCleanliness()-2)*grwsWeight);
		vec2.add((per2.getCleanliness()-2)*grwsWeight);
		
		// 性格
		vec1.add((per1.getPersonCharacter()-2)*xgWeight);
		vec2.add((per2.getPersonCharacter()-2)*xgWeight);
		
		matchScoreAndMessage.matchScore = this.cosinAngleOfTwoVector(vec1, vec2);
		
		return matchScoreAndMessage;
	}
	
	// 查询星座
	public int queryConstellation(String constellation){
		Map<String, Integer> constellationMap = new HashMap<String, Integer>();
		// 1，火象星座；2，土象星座；3，风象星座；4，水象星座
		constellationMap.put("白羊座", 1);
		constellationMap.put("金牛座", 2);
		constellationMap.put("双子座", 3);
		constellationMap.put("巨蟹座", 4);
		constellationMap.put("狮子座", 1);
		constellationMap.put("处女座", 2);
		constellationMap.put("天秤座", 3);
		constellationMap.put("天蝎座", 4);
		constellationMap.put("射手座", 1);
		constellationMap.put("摩羯座", 2);
		constellationMap.put("水瓶座", 3);
		constellationMap.put("双鱼座", 4);
		return constellationMap.get(constellation);
	}
	
	public int cosinAngleOfTwoVector(ArrayList<Integer> v1, ArrayList<Integer> v2){
		int length = v1.size();
		// 计算两个向量的叉积
		double crossProduct = 0;
		// 计算两个向量的模长
		double m1Length = 0, m2Length = 0;
		
		for(int i=0; i<length; i++){
			crossProduct += v1.get(i)*v2.get(i);
			m1Length += v1.get(i)*v1.get(i);
			m2Length += v2.get(i)*v2.get(i);
		}
		
		m1Length = Math.sqrt(m1Length);
		m2Length = Math.sqrt(m2Length);
		double cosinValue = crossProduct/(m1Length*m2Length);
		if(cosinValue > 1.0000) cosinValue = 1.0000;
		if(cosinValue < -1.0000) cosinValue = -1.0000;
		double vectorAngle = Math.acos(cosinValue);
		int Simlarity = (int)(100*(1-vectorAngle/Math.PI));
		
		return Simlarity;
	}
	
	// 设置各个选项的权重值
	public void setEvaluateWeight(Integer zxWeight, Integer cyWeight, Integer cwWeight,
			Integer fkWeight, Integer grwsWeight, Integer xgWeight, Integer xzWeight){
		zxWeight = 2; // 作息选项权重
		cyWeight = 2; // 抽烟选项权重
		cwWeight = 1; // 宠物选项权重
		fkWeight = 1; // 访客选项权重
		grwsWeight = 1; // 个人卫生选项权重
		xgWeight = 1; // 性格选项权重
		xzWeight = 1; // 星座选项权重
	}
	
	// 将需要显示再前端的用户信息保存在一个MatchUserInfo结构体中
	public MatchUserSimpleInfo userInfoToMatchUserSimpleInfo(User user){
		MatchUserSimpleInfo matchUserInfo = new MatchUserSimpleInfo();
		matchUserInfo.setJob(user.getCompany()+user.getPosition());
		matchUserInfo.setGender(user.getGender());
		matchUserInfo.setNickName(user.getNickName());
		matchUserInfo.setPhotoId(user.getUserId(), 0);
		matchUserInfo.setUserId(user.getUserId());
		matchUserInfo.setAge(dateToAge(user.getBirthday()));
		return matchUserInfo;
	}
	
	public int dateToAge(Date date){
		int age = 0;
		if(date==null) return age;
		Date curDate = new Date();
		int curYear = curDate.getYear();
		int birYear = date.getYear();
		age = curYear-birYear;
		return age;
	}
	
}
