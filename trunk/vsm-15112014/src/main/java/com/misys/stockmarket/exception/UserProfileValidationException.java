package com.misys.stockmarket.exception;

import com.misys.stockmarket.enums.USER_PROFILE_ERR_CODES;

/**
 * @author sam sundar k
 * 
 */
public class UserProfileValidationException extends BaseException {

	private USER_PROFILE_ERR_CODES errorCode;

	public UserProfileValidationException(USER_PROFILE_ERR_CODES errorCode) {
		this.errorCode = errorCode;
	}
}
