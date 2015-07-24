package com.netease.messagequeue.service;

import java.util.List;

import com.netease.roommates.vo.Message;

public interface MessageService {
	public List<Message> getMessage(int userId);
	
	public void addMessage(Message message);
}
