package com.misys.stockmarket.exception.service;

import com.misys.stockmarket.exception.ServiceException;

public class StockServiceException extends ServiceException {

	public StockServiceException(Exception e) {
		super(e);
	}

	public StockServiceException() {
		super();
	}
}