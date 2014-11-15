package com.misys.stockmarket.security;

import org.codehaus.jackson.map.annotate.JsonSerialize;

public class LoginResponse {

	@JsonSerialize(using = JsonHtmlXssSerializer.class)
    private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
