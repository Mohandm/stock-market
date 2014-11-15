package com.misys.stockmarket.exception.service;

import com.misys.stockmarket.exception.ServiceException;

public class UserServiceException extends ServiceException {

	public UserServiceException(Exception e) {
		super(e);
	}

	public UserServiceException() {
		super();
	}
}