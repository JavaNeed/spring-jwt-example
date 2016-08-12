package com.nibado.example.jwtangspr;

public class LoginResponse {
	public String token;

	public LoginResponse(final String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
