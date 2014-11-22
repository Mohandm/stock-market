package com.misys.stockmarket.exception.service;

import com.misys.stockmarket.exception.ServiceException;

public class AchievementExecutionServiceException extends ServiceException {

	public AchievementExecutionServiceException(Exception e) {
		super(e);
	}

	public AchievementExecutionServiceException() {
		super();
	}
}