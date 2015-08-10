package com.netease.roommates.controller;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.roommates.vo.MailVO;
import com.netease.utils.JsonBuilder;

@Controller
public class TestController {
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@ResponseBody
	@RequestMapping("/api/login/messagequeue")
	public String foo() {
		final MailVO mail = new MailVO();
		mail.setReceiver("linqinlicn@163.com");
		mail.setSubject("Jms testing.");
		mail.setContent("Just for testing.");
		
		jmsTemplate.send("mailbox-destination", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createObjectMessage(mail);
            }
        });
		
		return new JsonBuilder().append("errno", 0).build();
	}
}
