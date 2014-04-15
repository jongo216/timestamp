package com.example.timestamp.model;

public class User {

	private String mailAdress;
	private String userName;
	private String userPassword;


	public User(){
		setMailAdress("default@default.default");
	}
	
	public User(String mail){
		setMailAdress(mail);
	}
	
	public String getMailAdress() {
		return mailAdress;
	}

	public void setMailAdress(String mailAdress) {
		this.mailAdress = mailAdress;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	

	
	
}
