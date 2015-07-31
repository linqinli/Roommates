package com.netease.user.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netease.exception.ServiceException;
import com.netease.exception.StorageException;
import com.netease.roommates.mapper.UserMapper;
import com.netease.roommates.po.Personality;
import com.netease.roommates.po.User;
import com.netease.user.service.IUserInfoService;

@Service
public class UserInfoService implements IUserInfoService {
	private Logger log = LoggerFactory.getLogger(UserInfoService.class);
	@Autowired
	private UserMapper userMapper;
	private final static int[] dayArr = new int[] { 20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22 };
	private final static String[] constellationArr = new String[] { "摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座",
			"狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座" };

	// @Cacheable(value = "userCache", key = "'User' + #id") //Need to be
	// removed since the query sql is same.
	public User getUserById(int id) throws ServiceException {
		try {
			log.debug("UserInfoService.getUserById");
			return userMapper.getUserById(id);
		} catch (StorageException se) {
			log.error("error getting target user by id: " + id, se);
			throw new ServiceException(se);
		}
	}

	@Override
	// @CacheEvict(value = "userCache", key = "'User' + #user.getUserId()")
	public void updateUserBasicInfo(User user) throws ServiceException {
		try {
			user.setConstellation(getConstellation(user.getBirthday()));
			userMapper.updateUserBasicInfo(user);
		} catch (StorageException se) {
			log.error("error updatting target user: " + user, se);
			throw new ServiceException(se);
		}
	}

	@SuppressWarnings("deprecation")
	private String getConstellation(Date birth) {
		int month = birth.getMonth();
		int day = birth.getDay();
		return day < dayArr[month - 1] ? constellationArr[month - 1] : constellationArr[month];
	}

	@Override
	public void insertUser(User user) throws ServiceException {
		try {
			userMapper.insertUser(user);
		} catch (StorageException se) {
			log.error("error updatting target user: " + user, se);
			throw new ServiceException(se);
		}
	}

	@Override
	public void insertUserPersonality(Personality personality) throws ServiceException {
		try {
			userMapper.insertUserPersonality(personality);
		} catch (StorageException se) {
			log.error("error updatting target user personality: " + personality, se);
			throw new ServiceException(se);
		}
	}

	@Override
	// @CacheEvict(value = "userCache", key = "'personality' +
	// #personality.getId()")
	public void updateUserPersonality(Personality personality) throws ServiceException {
		try {
			userMapper.updateUserPersonality(personality);
		} catch (StorageException se) {
			log.error("error updatting target user personality: " + personality, se);
			throw new ServiceException(se);
		}
	}

	@Override
	public List<User> getUserByName(String name) throws ServiceException {
		try {
			return userMapper.getUserByName(name);
		} catch (StorageException se) {
			log.error("error get user by name:" + name, se);
			throw new ServiceException(se);
		}
	}

	@Override
	public User getUserByEmail(String email) throws ServiceException {
		try {
			return userMapper.getUserByEmail(email);
		} catch (StorageException se) {
			log.error("error get user by email:" + email, se);
			throw new ServiceException(se);
		}
	}

	@Override
	public int isQuestionnaireAll(int userId) throws ServiceException {
		try {
			return userMapper.isQuestionnaireAll(userId);
		} catch (StorageException se) {
			log.error("error check Questionnaire is complete:" + userId, se);
			throw new ServiceException(se);
		}
	}

	public Personality getUserPersonalityById(int userId) throws ServiceException {
		try {
			return userMapper.getUserPersonality(userId);
		} catch (StorageException se) {
			log.error("error getting target user personality by userId: " + userId, se);
			throw new ServiceException(se);
		}
	}

}
