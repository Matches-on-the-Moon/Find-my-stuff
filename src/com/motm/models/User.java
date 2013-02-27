package com.motm.models;


import java.io.Serializable;

public class User implements Serializable{
	
	String userid;
	String password;
	String name;
	String email;

	public User( String userid, String password, String name, String email ){
		
		this.userid = userid;
		this.password = password;
		this.name = name;
		this.email = email;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
