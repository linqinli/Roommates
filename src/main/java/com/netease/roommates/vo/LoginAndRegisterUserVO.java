package com.netease.roommates.vo;

public class LoginAndRegisterUserVO {
	private String nickname;
	private String email;
	private String password;
	private int userId;
	//private String sessionId;
	
	public LoginAndRegisterUserVO(){}
	public String getNickname(){
		return nickname;
	}
	public void setNickname(String nickname){
		this.nickname = nickname;
	}
	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public String getPassword(){
		return password;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public int getUserId(){
		return userId;
	}
	public void setUserId(int userId){
		this.userId = userId;
	}
//	public String getSessionId(){
//		return sessionId;
//	}
//	public void setSessionId(String sessionId){
//		this.sessionId = sessionId;
//	}
	
}
