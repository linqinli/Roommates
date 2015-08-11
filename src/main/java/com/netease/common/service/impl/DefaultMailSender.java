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
		EmailAccountVO emailTom123 = new EmailAccountVO("roommates123@tom.com", "smtp.tom.com", "roommates123", "123qwe");
		EmailAccountVO email126 = new EmailAccountVO("roommates123@126.com", "smtp.126.com", "roommates123", "oyblyjwrqjkjyehc");
		EmailAccountVO emailSina1 = new EmailAccountVO("roommate121@sina.com", "smtp.sina.com", "roommate121", "123456a");
		EmailAccountVO emailSina2 = new EmailAccountVO("roommate12321@sina.com", "smtp.sina.com", "roommate12321", "123456a");
		EmailAccountVO emailSina3 = new EmailAccountVO("wuyanxia6666@sina.com", "smtp.sina.com", "wuyanxia6666", "wuyanxia");
		EmailAccountVO emailSina4 = new EmailAccountVO("wuyanxiahz@sina.com", "smtp.sina.com", "wuyanxiahz", "wuyanxia");
		EmailAccountVO emailSina5 = new EmailAccountVO("roommate32123@sina.com", "smtp.sina.com", "roommate32123", "123456a");
		EmailAccountVO emailSina6 = new EmailAccountVO("wuyanxia_welcome@sina.cn", "smtp.sina.cn", "wuyanxia_welcome", "wuyanxia");
		EmailAccountVO emailSina7 = new EmailAccountVO("wuyanxia_001@sina.com", "smtp.sina.com", "wuyanxia_001", "wuyanxia");
		
		email.put(0, emailSina);
		email.put(1, email126);
		email.put(2, emailTom123);
		email.put(3, emailSina1);
		email.put(4, emailSina2);
		email.put(5, emailSina3);
		email.put(6, emailSina4);
		email.put(7, emailSina5);
		email.put(8, emailSina6);
		email.put(9, emailSina7);
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
		int count = 10;
		for(int i=2; i<count; i++){
			try {
				if (StringUtils.isEmpty(receiver)) {
					throw new MessagingException("Receiver should not be empty.");
				}
				num = i;
				logger.info(num+"email send from"+email.get(num).getEmailAddress());
				System.out.println(num+"email send from"+email.get(num).getEmailAddress());
				
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
				break;
			} catch (Exception mex) {
				logger.error("Can not send mail to target user:" + receiver);
				System.out.println("Can not send mail to target user:" + receiver);
				//send();
				//throw new ServiceException("Can not send mail to target user.", mex);
			} finally {
				clearMailStatus();
			}
		}
	}

	private void clearMailStatus() {
		receiver = "";
		subject = "";
		content = "";
	}

}
