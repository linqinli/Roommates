package com.netease.common.service;

public interface AutoLoginRedisService {
	public String getUserIdBySessionId(String sessionId);
	
	public void putSessionIdAndUserId(String sessionId, String userId);

	public void deleteSessionIdAndUserId(String sessionId);
}
