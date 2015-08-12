package com.netease.common.service.impl;

import java.io.File;


import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netease.common.service.MailSender;
import com.netease.exception.ServiceException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.multipart.FormDataMultiPart;

public class MailgunSender implements MailSender {
	
	private Logger logger = LoggerFactory.getLogger(DefaultMailSender.class);
	
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
		try{
		 Client client = Client.create();
		 client.addFilter(new HTTPBasicAuthFilter("api",
		             "key-5f651d12e3bee58d3c391fd8ca854710"));
		 WebResource webResource =
		         client.resource("https://api.mailgun.net/v3/mail.jchen.cc/" + "messages");
		 FormDataMultiPart form = new FormDataMultiPart();
		 form.field("from", "<roommates123@sina.com>");
		 form.field("to", receiver);
		 form.field("subject", subject);
		 form.field("html", content);
		 webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).post(ClientResponse.class, form);
		}
		catch (Exception mex) {
			logger.error("Can not send mail to target user:" + receiver);
			throw new ServiceException("Can not send mail to target user.", mex);
		} 
		finally{
			clearMailStatus();
		}
	}
	private void clearMailStatus() {
		receiver = "";
		subject = "";
		content = "";
	}


}
