package com.netease.messagequeue.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.netease.messagequeue.service.MessageService;
import com.netease.roommates.vo.Message;
import com.netease.utils.MessageUtils;

@Service
public class DefaultMessageService implements MessageService {
	@Autowired
	private RedisTemplate<String, Message> redisTemplate;

	@Override
	public List<Message> getMessage(int userId) {
		String key = "user_message_" + userId;
		List<Message> messages = redisTemplate.opsForList().range(key, 0, -1);
		redisTemplate.delete(key);
		return messages;
	}

	@Override
	public void addMessage(Message message) {
		message.setTimestamp(System.currentTimeMillis());
		message.setTypeName(getTypeName(message.getType()));
		redisTemplate.opsForList().rightPush("user_message_" + message.getReceiver(), message);
	}

	private String getTypeName(int type) {
		switch (type) {
		case 1:
			return MessageUtils.getMessage("INVAITATION");
		case 2:
			return MessageUtils.getMessage("INVAITATION_REPLAY");
		case 3:
			return MessageUtils.getMessage("RECOMMENDED_HOUSE");
		default:
			return MessageUtils.getMessage("PERSONAL_LETTER");
		}
	}
}
