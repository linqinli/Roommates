package com.netease.common.service.impl;

import java.io.File;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.netease.common.service.MailSender;
import com.netease.exception.ServiceException;

@Service
public class DefaultMailSender implements MailSender {
	private Logger logger = LoggerFactory.getLogger(DefaultMailSender.class);
//	private final static String USERNAME = "roommates123";
//	private final static String PASSWORD = "12345qwert";
	private final static String USERNAME = "roommates123";
	private final static String PASSWORD = "123qwe";
	private String receiver;
	private String subject;
	private String content;

	@Override
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	@Override
	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public void attachfile(File file) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public void send() throws ServiceException {
		try {
			if (StringUtils.isEmpty(receiver)) {
				throw new MessagingException("Receiver should not be empty.");
			}
			
			Properties props = new Properties();
//			props.put("mail.smtp.host", "smtp.sina.com");
			props.put("mail.smtp.host", "smtp.tom.com");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "25");
			Session session = Session.getDefaultInstance(props, new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(USERNAME, PASSWORD);
				}
			});

			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

//			message.setFrom(new InternetAddress("roommates123@sina.com"));
			message.setFrom(new InternetAddress("roommates123@tom.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));

			message.setSubject(subject);
			message.setContent(content, "text/html;charset=UTF-8");
			Transport.send(message);
			logger.info("Sent message to {} successfully....", receiver);
		} catch (MessagingException mex) {
			logger.error("Can not send mail to target user:" + receiver);
			throw new ServiceException("Can not send mail to target user.", mex);
		} finally {
			clearMailStatus();
		}
	}

	private void clearMailStatus() {
		receiver = "";
		subject = "";
		content = "";
	}

}
