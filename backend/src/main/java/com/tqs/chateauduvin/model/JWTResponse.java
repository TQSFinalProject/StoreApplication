package com.tqs.chateauduvin.model;

// This code was retrieved from the guide https://www.javainuse.com/spring/boot-jwt
// All credits go to the author

import java.io.Serializable;

public class JWTResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;

	public JWTResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getToken() {
		return this.jwttoken;
	}
}