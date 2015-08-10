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
	
	// 根据条件从数据库中选出符合条件的userId
	public List<Integer> getUserIdListByCondition(int id, int xb, int f, int gs, int cy, int cw,
			int zx, int ws, int xg, int fk) throws ServiceException;
	// 提供当前用户id与userId列表，生成室友匹配列表
	public List<MatchUserSimpleInfo> matchResultSimpleInfo(int curUserId, List<Integer> userIdList) throws ServiceException;
	
	// 通过加权计算两个personality的加权得分
	public MatchScoreAndMessage getSimilarityBetweenTwoPersonality(Personality per1, Personality per2) throws ServiceException;
	// 通过计算向量夹角的方式计算两个personality的得分
	public MatchScoreAndMessage getVectorSimilarityBetweenTwoPersonality(Personality per1, Personality per2) throws ServiceException;
	
	// 查询星座象限，根据网上，分为四象限
	public int queryConstellation(String constellation) throws ServiceException;
	
	// 计算两个高维向量的夹角
	public int cosinAngleOfTwoVector(ArrayList<Integer> v1, ArrayList<Integer> v2) throws ServiceException;
	
	// 将数据库中选出的用户字段信息，转化为前端需要显示的字段信息
	public MatchUserSimpleInfo userInfoToMatchUserSimpleInfo(User user) throws ServiceException;
	
	// 转换显示的年龄
	public String dateToAge(Date date) throws ServiceException;
	
	public List<MatchUserSimpleInfo> getMatchUserSimpleInfoByPara(int id, int p, int xb, int f, int gs, int cy, int cw,
			int zx, int ws, int xg, int fk) throws ServiceException;
	
	public List<MatchUserSimpleInfo> searchUserSimpleInfoByPara(String keyWords, int id, int p, int xb, int f, int gs, int cy, int cw,
			int zx, int ws, int xg, int fk) throws ServiceException;
	
	public String setDisplayMatchMessage(User curUser, User user, int matchScore) throws ServiceException; 
}
