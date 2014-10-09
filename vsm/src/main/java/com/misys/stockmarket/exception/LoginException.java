package com.misys.stockmarket.exception;

import com.misys.stockmarket.enums.LOGIN_ERR_CODES;

public class LoginException extends Exception {

	private LOGIN_ERR_CODES errorCode;

	public LoginException(LOGIN_ERR_CODES errorCode) {
		this.errorCode = errorCode;
	}

}
