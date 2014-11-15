package com.misys.stockmarket.exception.service;

import com.misys.stockmarket.exception.ServiceException;

public class PortfolioServiceException extends ServiceException {

	/*private STOCK_ERR_CODES errorCode;

	public PortfolioServiceException(STOCK_ERR_CODES errorCode) {
		this.errorCode = errorCode;
	}

	public PortfolioServiceException(STOCK_ERR_CODES errorCode, Exception e) {
		super(e);
		this.errorCode = errorCode;
	}*/

	public PortfolioServiceException(Exception e) {
		super(e);
	}

	public PortfolioServiceException() {
		super();
	}

	/*public STOCK_ERR_CODES getErrorCode() {
		return errorCode;
	}*/
}
