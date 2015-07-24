package com.netease.roommates.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.messagequeue.service.MessageService;
import com.netease.roommates.vo.Message;

@Controller
@RequestMapping("/api/message")
public class MessageController {
	@Autowired
	MessageService messageService;
	
	@RequestMapping(value = "/addMessage", method = RequestMethod.POST)
	public void addMessage(@RequestBody Message message) {
		messageService.addMessage(message);
	}
	
	@RequestMapping(value = "/getMessages")
	@ResponseBody
	public List<Message> getMessageList(int userId) {
		return messageService.getMessage(userId);
	}
}
