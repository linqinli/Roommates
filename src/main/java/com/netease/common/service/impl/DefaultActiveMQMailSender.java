package com.netease.common.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.netease.common.service.ActiveMQMailSender;
import com.netease.common.service.MailSender;
import com.netease.exception.ServiceException;
import com.netease.roommates.vo.MailVO;

@Service
public class DefaultActiveMQMailSender implements ActiveMQMailSender {
	private Logger logger = LoggerFactory.getLogger(DefaultActiveMQMailSender.class);
	
	@Autowired
	private MailSender mailSender;
	
	@Override
	@JmsListener(destination = "mailbox-destination", containerFactory = "jmsListenerContainerFactory")
	public void send(MailVO mail) {
		try {
			mailSender.setReceiver(mail.getReceiver());
			mailSender.setSubject(mail.getSubject());
			mailSender.setContent(mail.getContent());
			mailSender.send();
			logger.info("Mail has been sent.");
		} catch (ServiceException e) {
			logger.error("Error sending email.", e);
		}
	}

}
