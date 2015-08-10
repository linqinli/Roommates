package com.netease.roommates.controller;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.netease.roommates.vo.MailVO;

@Controller
public class TestController {
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@RequestMapping("/api/login/messaagequeue")
	public void foo() {
		final MailVO mail = new MailVO();
		mail.setSubject("Jms testing.");
		
		jmsTemplate.send("mailbox-destination", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createObjectMessage(mail);
            }
        });
	}
}
