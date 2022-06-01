package com.tqs.chateauduvin.model;

// This code was retrieved from the guide https://www.javainuse.com/spring/boot-jwt
// All credits go to the author

import java.io.Serializable;

public class JWTRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;
	
	private String username;
	private String password;
	
	//need default constructor for JSON Parsing
	public JWTRequest()
	{
		
	}

	public JWTRequest(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}