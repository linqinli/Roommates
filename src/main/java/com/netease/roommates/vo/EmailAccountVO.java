package com.netease.roommates.vo;

public class EmailAccountVO {
	private String emailAddress;
	private String smtpAddress;
	private String username;
	private String password;
	
	public EmailAccountVO(){}
	public EmailAccountVO(String emailAddress, String smtpAddress, String username, String password){
		this.emailAddress = emailAddress;
		this.smtpAddress = smtpAddress;
		this.username = username;
		this.password = password;
	}
	
	public void setEmailAddress(String emailAddress){
		this.emailAddress = emailAddress;
	}
	public String getEmailAddress(){
		return emailAddress;
	}
	public void setSmtpAddress(String smtpAddress){
		this.smtpAddress = smtpAddress;
	}
	public String getSmtpAddress(){
		return smtpAddress;
	}
	public void setUsername(String username){
		this.username = username;
	}
	public String getUsername(){
		return username;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public String getPassword(){
		return password;
	}
	
	
}
