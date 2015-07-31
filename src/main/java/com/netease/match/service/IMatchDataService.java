package com.netease.match.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.netease.exception.ServiceException;
import com.netease.roommates.po.Personality;
import com.netease.roommates.po.User;
import com.netease.roommates.vo.MatchUserSimpleInfo;
import com.netease.roommates.vo.MatchScoreAndMessage;

public interface IMatchDataService {
	
	public List selectAllUsers() throws ServiceException;
	public List selectAllPersonalitys() throws ServiceException;
	
	public List<Integer> getUserIdListByCondition(int id, int xb, int f, int gs, int cy, int cw,
			int zx, int ws, int xg, int fk) throws ServiceException;
	
	public List<MatchUserSimpleInfo> matchResultSimpleInfo(int curUserId, List<Integer> userIdList) throws ServiceException;
	
	public MatchScoreAndMessage getSimilarityBetweenTwoPersonality(Personality per1, Personality per2) throws ServiceException;
	
	public MatchScoreAndMessage getVectorSimilarityBetweenTwoPersonality(Personality per1, Personality per2) throws ServiceException;
	
	public int queryConstellation(String constellation) throws ServiceException;
	
	public int cosinAngleOfTwoVector(ArrayList<Integer> v1, ArrayList<Integer> v2) throws ServiceException;
	
	public MatchUserSimpleInfo userInfoToMatchUserSimpleInfo(User user) throws ServiceException;
	
	public String dateToAge(Date date) throws ServiceException;
	
	public List<MatchUserSimpleInfo> getMatchUserSimpleInfoByPara(int id, int p, int xb, int f, int gs, int cy, int cw,
			int zx, int ws, int xg, int fk) throws ServiceException;
	
	public String setDisplayMatchMessage(User curUser, User user) throws ServiceException; 
}
