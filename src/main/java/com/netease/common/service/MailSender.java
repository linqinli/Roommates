package com.netease.common.service;

import java.io.File;

import javax.mail.MessagingException;

import com.netease.exception.ServiceException;

/**
 * A simple interface of mail sender.
 * @author hzlinqinli
 */
public interface MailSender {
    /**
     * Set the receiver address of the mail .
     * @param receiver the mail address of receiver
     */
	public void setReceiver(String receiver);
	
	/**
	 * Set the subject of the mail.
	 * @param subject the subject of mail
	 */
	public void setSubject(String subject);
	
	/**
	 * Set the content of mail.
	 * @param content the content of mail
	 */
	public void setContent(String content);
	
	/**
	 * Add a attachment to the mail.
	 * @param file the attachment to be sent
	 */
	public void attachfile(File file);
	
	/**
	 * Send the mail.
	 * @throws MessagingException 
	 */
	public void send() throws ServiceException;
}
