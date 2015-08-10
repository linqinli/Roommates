package com.netease.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.netease.common.service.ActiveMQMailSender;
import com.netease.common.service.MailSender;
import com.netease.roommates.vo.MailVO;

@Service
public class DefaultActiveMQMailSender implements ActiveMQMailSender {
	@Autowired
	private MailSender mailSender;
	
	@Override
	//@JmsListener(destination = "mailbox-destination", containerFactory = "jmsListenerContainerFactory")
	public void send(MailVO mail) {
		System.out.println("Mail subject: " +mail.getSubject());

	}

}
