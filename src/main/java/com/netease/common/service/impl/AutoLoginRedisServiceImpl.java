package com.netease.common.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.netease.common.service.AutoLoginRedisService;

@Service
public class AutoLoginRedisServiceImpl implements AutoLoginRedisService {
	private final static String USER_SESSION ="user_session_hash_map";
	@Autowired
	private RedisTemplate<String, Map<String, String>> redisTemplate;
	
	@Override
	public String getUserIdBySessionId(String sessionId) {
		return (String) redisTemplate.opsForHash().get(USER_SESSION, sessionId);
	}

	@Override
	public void putSessionIdAndUserId(String sessionId, String userId ) {
		 redisTemplate.opsForHash().put(USER_SESSION, sessionId, userId);
	}
	
	@Override
	public void deleteSessionIdAndUserId(String sessionId) {
		redisTemplate.opsForHash().delete(USER_SESSION, sessionId);
	}
}
