package com.misys.stockmarket.exception.service;

import com.misys.stockmarket.exception.ServiceException;

public class AlertsServiceException extends ServiceException {

	public AlertsServiceException(Exception e) {
		super(e);
	}

	public AlertsServiceException() {
		super();
	}
}
