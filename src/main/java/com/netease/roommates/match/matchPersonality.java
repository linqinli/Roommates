package com.netease.roommates.match;

import java.util.*;
import java.util.ArrayList;
import com.netease.exception.StorageException;
import com.netease.roommates.po.User;
import com.netease.roommates.po.Personality;


public class matchPersonality {
	private User curUser;
	
	public matchPersonality(User user){
		setCurUser(user);
	}
	
	Map<User, Integer> matchResult(){
		
		return null;
	}

	public User getCurUser() {
		return curUser;
	}

	public void setCurUser(User curUser) {
		this.curUser = curUser;
	}
	
	
	public int getSimilarityBetweenTwoPersonality(Personality per1, Personality per2){
		int matchScore = 0;
		
		// 权重 分配
		int zxWeight = 3; // 作息选项权重
		int cyWeight = 3; // 抽烟选项权重
		int cwWeight = 2; // 宠物选项权重
		int fkWeight = 2; // 访客选项权重
		int grwsWeight = 2; // 个人卫生选项权重
		int xgWeight = 1; // 性格选项权重
		int xzWeight = 1; // 星座选项权重

		// 作息选项匹配得分计算
		int user1DailySchedule = per1.getDailySchedule();
		int user2DailySchedule = per2.getDailySchedule();
		if(user1DailySchedule == user2DailySchedule){
			matchScore += zxWeight*1;
		}
		else matchScore += zxWeight*(-1);

		// 抽烟选项匹配得分计算
		int user1Smoking = per1.getSmoking();
		int user2Smoking = per2.getSmoking();
		user1Smoking -= 2;
		user2Smoking -= 2;
		if(user1Smoking == user2Smoking){
			matchScore += cyWeight*1;
		}
		else matchScore += cyWeight*(user1Smoking*user2Smoking);

		// 宠物选项匹配得分计算
		int user1Pet = per1.getPet();
		int user2Pet = per2.getPet();
		user1Pet -= 2;
		user2Pet -= 2;
		if(user1Pet == user2Pet){
			matchScore += cwWeight*1;
		}
		else matchScore += cwWeight*(user1Pet*user2Pet);

		// 访客选项匹配得分计算
		int user1Visitor = per1.getVisitor();
		int user2Visitor = per2.getVisitor();
		user1Visitor -= 2;
		user2Visitor -= 2;
		if(user1Visitor == user2Visitor){
			matchScore += fkWeight*1;
		}
		else matchScore += fkWeight*(user1Visitor*user2Visitor);

		// 个人卫生选项匹配得分计算
		int user1Sanitation = per1.getSanitation();
		int user2Sanitation = per2.getSanitation();
		user1Sanitation -= 2;
		user2Sanitation -= 2;
		if(user1Sanitation == user2Sanitation){
			matchScore += grwsWeight*1;
		}
		else matchScore += grwsWeight*(user1Sanitation*user2Sanitation);

		// 性格选项匹配得分计算
		int user1Character = per1.getCharacter();
		int user2Character = per2.getCharacter();
		user1Character -= 2;
		user2Character -= 2;
		if(user1Character == user2Character){
			matchScore += xgWeight*1;
		}
		else matchScore += xgWeight*(user1Character*user2Character);

		// 星座选项匹配得分计算
		Map<String, Integer> constellationMap = new HashMap<String, Integer>();
		constellationMap.put("白羊座", 1);
		constellationMap.put("金牛座", 2);
		constellationMap.put("双子座", 3);
		constellationMap.put("巨蟹座", 4);
		constellationMap.put("狮子座", 5);
		constellationMap.put("处女座", 6);
		constellationMap.put("天秤座", 7);
		constellationMap.put("天蝎座", 8);
		constellationMap.put("射手座", 9);
		constellationMap.put("摩羯座", 10);
		constellationMap.put("水瓶座", 11);
		constellationMap.put("双鱼座", 12);
		int user1Constellation = constellationMap.get(per1.getConstellation());
		int user2Constellation = constellationMap.remove(per2.getConstellation());
		if(user1Constellation == 1 || user1Constellation == 2 || user1Constellation == 3) user1Constellation=1;
		else if(user1Constellation == 4 || user1Constellation == 5 || user1Constellation == 6) user1Constellation=2;
		else if(user1Constellation == 7 || user1Constellation == 8 || user1Constellation == 9) user1Constellation=3;
		else if(user1Constellation == 10 || user1Constellation == 11 || user1Constellation == 12) user1Constellation=4;
		if(user2Constellation == 1 || user2Constellation == 2 || user2Constellation == 3) user2Constellation=1;
		else if(user2Constellation == 4 || user2Constellation == 5 || user2Constellation == 6) user2Constellation=2;
		else if(user2Constellation == 7 || user2Constellation == 8 || user2Constellation == 9) user2Constellation=3;
		else if(user2Constellation == 10 || user2Constellation == 11 || user2Constellation == 12) user2Constellation=4;

		if(user1Constellation == user2Constellation) matchScore += xzWeight*1;

		// 归一化处理
		int totalWeight = 2*zxWeight+2*cyWeight+2*cwWeight+2*fkWeight+2*grwsWeight+2*xgWeight+1*xzWeight;
		matchScore = matchScore+zxWeight+cyWeight+cwWeight+fkWeight+grwsWeight+xgWeight;
		matchScore = (int)(100*(double)matchScore/(double)totalWeight);
		
		return matchScore;
	}
}

