package com.netease.match.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.netease.exception.ServiceException;
import com.netease.match.service.IMatchDataService;
import com.netease.match.service.IMatchSqlService;
import com.netease.roommates.vo.MatchScoreAndMessage;
import com.netease.roommates.po.Personality;
import com.netease.roommates.po.User;
import com.netease.roommates.vo.MatchUserSimpleInfo;
import com.netease.user.service.IUserHouseService;
import com.netease.user.service.IUserInfoService;

@Service
public class MatchDataService implements IMatchDataService {
	private Logger log = LoggerFactory.getLogger(MatchDataService.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private IMatchSqlService matchSqlService;
	@Autowired
	private IUserHouseService userHouseService;
	
	@Override
	public List<MatchUserSimpleInfo> getMatchUserSimpleInfoByPara(int id, int p, int xb, int f, int gs, int cy, int cw,
			int zx, int ws, int xg, int fk) throws ServiceException{
		List<Integer> userIdList = getUserIdListByCondition(id, xb, f, gs, cy, cw, zx, ws, xg, fk);
		List<MatchUserSimpleInfo>  matchUserSimpleInfo = matchResultSimpleInfo(id, userIdList);
		
		List<MatchUserSimpleInfo> resultUserSimpleInfo = new ArrayList<MatchUserSimpleInfo>();
		for(int i=(p-1)*20; i<p*20 && i<matchUserSimpleInfo.size(); ++i ){
			resultUserSimpleInfo.add(matchUserSimpleInfo.get(i));
		}
		
		return resultUserSimpleInfo;
	}
	
	@Override
	public List<Integer> getUserIdListByCondition(int id, int xb, int f, int gs, int cy, int cw,
			int zx, int ws, int xg, int fk){
		String selectSqlString = matchSqlService.generateSqlStrByCondition(id, xb, f, gs, cy, cw, zx, ws, xg, fk);
		String orderSqlString = "select * from ( " + selectSqlString + " ) res order by res.userId desc";
		List<Integer> userIdList = (List<Integer>)jdbcTemplate.queryForList(orderSqlString, Integer.class);
		return userIdList;
	}
	
	@Override
	public List<MatchUserSimpleInfo> matchResultSimpleInfo(int curUserId, List<Integer> userIdList) throws ServiceException{
		User curUser = userInfoService.getUserById(curUserId);
		List<MatchUserSimpleInfo> matchUserInfo = new ArrayList<MatchUserSimpleInfo>();
		if(curUser == null) return matchUserInfo;
		for(int i=0; i<userIdList.size(); ++i){
			User user = userInfoService.getUserById(userIdList.get(i));
			// 如果还在找房，则显示出来
			if(user.getStatus()==0){
				MatchScoreAndMessage matchScoreAndMessage = new MatchScoreAndMessage();
				MatchUserSimpleInfo userTmpInfo = userInfoToMatchUserSimpleInfo(user);
				userTmpInfo.setHasHouse(false);
				if(curUser.getPersonality()!=null && user.getPersonality()!=null){
					
					matchScoreAndMessage = this.getVectorSimilarityBetweenTwoPersonality(curUser.getPersonality(), user.getPersonality());
					matchScoreAndMessage.setMatchMessage(setDisplayMatchMessage(curUser, user, matchScoreAndMessage.getMatchScore()));
				}
				if(userHouseService.getUserHouseById(user.getUserId())!=null){
					userTmpInfo.setHasHouse(true);
				}
				
				userTmpInfo.setMatchScore(matchScoreAndMessage.getMatchScore());
				userTmpInfo.setMatchMessage(setDisplayMatchMessage(curUser, user, matchScoreAndMessage.getMatchScore()));
				matchUserInfo.add(userTmpInfo);
			}
		}
		// 如果有填过问卷，则按分数高低进行排序
		if(curUser.getPersonality()!=null){
			Collections.sort(matchUserInfo,new Comparator<MatchUserSimpleInfo>(){
				@Override
				public int compare(MatchUserSimpleInfo matchUserInfo1, MatchUserSimpleInfo matchUserInfo2){
					return matchUserInfo2.getMatchScore()-matchUserInfo1.getMatchScore();
				}
			});
		}
		return matchUserInfo;
	}
	
	// 通过问卷选项，计算两用户行为偏好的匹配得分
	@Override
	public MatchScoreAndMessage getSimilarityBetweenTwoPersonality(Personality per1, Personality per2){
		MatchScoreAndMessage matchScoreAndMessage = new MatchScoreAndMessage();
		int matchScore = 0;

		// 权重 分配
		int zxWeight = 1; // 作息选项权重
		int cyWeight = 1; // 抽烟选项权重
		int cwWeight = 1; // 宠物选项权重
		int fkWeight = 1; // 访客选项权重
		int grwsWeight = 1; // 个人卫生选项权重
		int xgWeight = 1; // 性格选项权重

		// 作息选项匹配得分计算
		int user1DailySchedule = per1.getDailySchedule();
		int user2DailySchedule = per2.getDailySchedule();
		if(user1DailySchedule == user2DailySchedule){
			matchScore += zxWeight*1;
		}
		else{ matchScore += zxWeight*(-1);}

		// 抽烟选项匹配得分计算
		int user1Smoking = per1.getSmoking();
		int user2Smoking = per2.getSmoking();
		user1Smoking -= 2;
		user2Smoking -= 2;
		if(user1Smoking == user2Smoking){
			matchScore += cyWeight*1;
		}
		else{ matchScore += cyWeight*(user1Smoking*user2Smoking);}

		// 宠物选项匹配得分计算
		int user1Pet = per1.getPet();
		int user2Pet = per2.getPet();
		user1Pet -= 2;
		user2Pet -= 2;
		if(user1Pet == user2Pet){
			matchScore += cwWeight*1;
		}
		else{ matchScore += cwWeight*(user1Pet*user2Pet);}

		// 访客选项匹配得分计算
		int user1Visitor = per1.getVisitor();
		int user2Visitor = per2.getVisitor();
		user1Visitor -= 2;
		user2Visitor -= 2;
		if(user1Visitor == user2Visitor){
			matchScore += fkWeight*1;
		}
		else{ matchScore += fkWeight*(user1Visitor*user2Visitor);}

		// 个人卫生选项匹配得分计算
		int user1Sanitation = per1.getCleanliness();
		int user2Sanitation = per2.getCleanliness();
		user1Sanitation -= 2;
		user2Sanitation -= 2;
		if(user1Sanitation == user2Sanitation){
			matchScore += grwsWeight*1;
		}
		else matchScore += grwsWeight*(user1Sanitation*user2Sanitation);

		// 性格选项匹配得分计算
		int user1Character = per1.getPersonCharacter();
		int user2Character = per2.getPersonCharacter();
		user1Character -= 2;
		user2Character -= 2;
		if(user1Character == user2Character){
			matchScore += xgWeight*1;
		}
		else matchScore += xgWeight*(user1Character*user2Character);

		// 归一化处理
		int totalWeight = 2*zxWeight+2*cyWeight+2*cwWeight+2*fkWeight+2*grwsWeight+2*xgWeight;
		matchScore = matchScore+zxWeight+cyWeight+cwWeight+fkWeight+grwsWeight+xgWeight;
		matchScore = (int)(100*(double)matchScore/(double)totalWeight);

		matchScoreAndMessage.setMatchScore(matchScore);
		matchScoreAndMessage.setMatchMessage("");

		return matchScoreAndMessage;
	}

	@Override
	public MatchScoreAndMessage getVectorSimilarityBetweenTwoPersonality(Personality per1, Personality per2){
		MatchScoreAndMessage matchScoreAndMessage = new MatchScoreAndMessage();

		ArrayList<Integer> vec1 = new ArrayList<Integer>();
		ArrayList<Integer> vec2 = new ArrayList<Integer>();

		int zxWeight=1, cyWeight=1, cwWeight=1, fkWeight=1, grwsWeight=1, xgWeight=1;
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

		matchScoreAndMessage.setMatchScore(cosinAngleOfTwoVector(vec1, vec2));

		return matchScoreAndMessage;
	}

	// 查询星座
	@Override
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

	@Override
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

	// 将需要显示再前端的用户信息保存在一个MatchUserInfo结构体中
	@Override
	public MatchUserSimpleInfo userInfoToMatchUserSimpleInfo(User user){
		MatchUserSimpleInfo matchUserInfo = new MatchUserSimpleInfo();
		if(user.getCompany()!=null) matchUserInfo.setCompany(user.getCompany());
		if(user.getGender()!=null) matchUserInfo.setGender(user.getGender());
		if(user.getNickName()!=null) matchUserInfo.setNickName(user.getNickName());

		// matchUserInfo.setPhotoId(user.getUserId(), 0);
		if(user.getHasPhoto()) matchUserInfo.setPhotoId(user.getUserId(), 0);
		else matchUserInfo.setPhotoId(123, 1);
		matchUserInfo.setUserId(user.getUserId());
		if(user.getBirthday()!=null) matchUserInfo.setAge(dateToAge(user.getBirthday()));
		return matchUserInfo;
	}

	public String dateToAge(Date date){
		String age = "";
		if(date==null) return age;
		Date curDate = new Date();
		int curYear = curDate.getYear();
		int birYear = date.getYear();
		age = (curYear-birYear)+"岁";
		return age;
	}

	@Override
	public String setDisplayMatchMessage(User curUser, User user, int matchScore) throws ServiceException {
		Personality curPer = curUser.getPersonality();
		Personality per = user.getPersonality();
		if(curPer==null || curUser.getPhoneNumber()==null || per==null) return "请完善个人信息哟";
		
		// 公司，年龄，问卷
		if(curPer.getCleanliness()==per.getCleanliness() && curPer.getPersonCharacter()==per.getPersonCharacter()
				&& curPer.getDailySchedule()==per.getDailySchedule() && curPer.getPet()==per.getPet() 
				&& curPer.getSmoking()==per.getSmoking() && curPer.getVisitor()==per.getVisitor()){
			return "你们真是天生一对～";
		}
		
		if(curPer.getDailySchedule()==per.getDailySchedule()) return "你们俩作息相同";
		if(curPer.getPet()==per.getPet()) return "你们是爱宠人士";
		if(curPer.getSmoking()==per.getSmoking()) return "你们是抽烟人士";
		if(curUser.getCompany()==user.getCompany()) return "你们是同事";
		if(curUser.getBirthday()!=null && user.getBirthday()!=null &&
				Math.abs(curUser.getBirthday().getYear()-user.getBirthday().getYear())<=1) return "你们年龄相仿";
		if(matchScore > 50) return "你们俩个性相投";
		else return "你们都是对方的新世界";
	}

	@Override
	public List<MatchUserSimpleInfo> searchUserSimpleInfoByPara(String keyWords, int id, int p, int xb, int f, int gs,
			int cy, int cw, int zx, int ws, int xg, int fk) throws ServiceException {
		
		String selectSqlString = matchSqlService.generateSqlStrByCondition(id, xb, f, gs, cy, cw, zx, ws, xg, fk);
		keyWords="%"+keyWords+"%";
		String nickSqlString = "select su.userId from sys_user su join ( " + selectSqlString + " ) res on su.userId=res.userId where "
				+ "su.nickName like ?";
		List<Integer> nickUserIdList = (List<Integer>)jdbcTemplate.queryForList(nickSqlString, new Object[]{keyWords}, Integer.class);
		if(nickUserIdList.size() != 0){
			List<MatchUserSimpleInfo>  matchUserSimpleInfo = matchResultSimpleInfo(id, nickUserIdList);
			List<MatchUserSimpleInfo> resultUserSimpleInfo = new ArrayList<MatchUserSimpleInfo>();
			for(int i=(p-1)*20; i<p*20 && i<matchUserSimpleInfo.size(); ++i ){
				resultUserSimpleInfo.add(matchUserSimpleInfo.get(i));
			}
			return resultUserSimpleInfo;
		}
		String addrSqlString = "select fh.userId from fn_house fh join ( " + selectSqlString + " ) res on fh.userId=res.userId where "
				+ "fh.community like ?";
		List<Integer> addrUserIdList = (List<Integer>)jdbcTemplate.queryForList(addrSqlString, new Object[]{keyWords}, Integer.class);
		if(addrUserIdList.size() != 0){
			List<MatchUserSimpleInfo>  matchUserSimpleInfo = matchResultSimpleInfo(id, addrUserIdList);
			List<MatchUserSimpleInfo> resultUserSimpleInfo = new ArrayList<MatchUserSimpleInfo>();
			for(int i=(p-1)*20; i<p*20 && i<matchUserSimpleInfo.size(); ++i ){
				resultUserSimpleInfo.add(matchUserSimpleInfo.get(i));
			}
			return resultUserSimpleInfo;
		}
		return null;
	}
}
