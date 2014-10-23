package com.misys.stockmarket.exception.service;

import com.misys.stockmarket.exception.ServiceException;

public class OrderServiceException extends ServiceException {

	public OrderServiceException(Exception e) {
		super(e);
	}

	public OrderServiceException() {
		super();
	}
}