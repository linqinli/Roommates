package com.netease.common.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
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
import com.netease.roommates.vo.EmailAccountVO;

@Service
public class DefaultMailSender implements MailSender {
	private Logger logger = LoggerFactory.getLogger(DefaultMailSender.class);
	static Map<Integer, EmailAccountVO> email = new HashMap<Integer, EmailAccountVO>();
	int num;
	static{
		EmailAccountVO emailSina = new EmailAccountVO("roommates123@sina.com", "smtp.sina.com", "roommates123", "12345qwert");
		EmailAccountVO emailTom1 = new EmailAccountVO("roommates1@tom.com", "smtp.tom.com", "roommates1", "12345qwert");
		EmailAccountVO emailTom12 = new EmailAccountVO("roommates12@tom.com", "smtp.tom.com", "roommates12", "12345qwert");
		EmailAccountVO emailTom123 = new EmailAccountVO("roommates123@tom.com", "smtp.tom.com", "roommates123", "123qwe");
		EmailAccountVO emailTom1234 = new EmailAccountVO("roommates1234@tom.com", "smtp.tom.com", "roommates1234", "12345qwert");
		EmailAccountVO emailTom12345 = new EmailAccountVO("roommates12345@tom.com", "smtp.tom.com", "roommates12345", "12345qwert");
		EmailAccountVO email126 = new EmailAccountVO("roommates123@126.com", "smtp.126.com", "roommates123", "12345qwert");
		email.put(0, emailSina);
		email.put(1, emailTom1);
		email.put(2, emailTom12);
		email.put(3, emailTom123);
		email.put(4, emailTom1234);
		email.put(5, emailTom12345);
//		email.put(6, email126);
	}
	
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
			Random rand = new Random();
			num = Math.abs(rand.nextInt());
			num = num % 6;
			logger.info("email send from"+email.get(num).getEmailAddress());
					
			final String USERNAME = email.get(num).getUsername();
			final String PASSWORD = email.get(num).getPassword();
			Properties props = new Properties();
			props.put("mail.smtp.host", email.get(num).getSmtpAddress());
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
			message.setFrom(new InternetAddress(email.get(num).getEmailAddress()));
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
