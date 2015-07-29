package com.netease.roommates.vo;

public class LoginAndRegisterUserVO {
	private String nickname;
	private String email;
	private String password;
	
	public LoginAndRegisterUserVO(){}
	public String getNickname(){
		return nickname;
	}
	public void setNickname(String nickname){
		this.nickname=nickname;
	}
	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		this.email=email;
	}
	public String getPassword(){
		return password;
	}
	public void setPassword(String password){
		this.password=password;
	}
	

}
