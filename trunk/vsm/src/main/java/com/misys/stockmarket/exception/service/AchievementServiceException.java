package com.misys.stockmarket.exception.service;

import com.misys.stockmarket.exception.ServiceException;

public class AchievementServiceException extends ServiceException {

	public AchievementServiceException(Exception e) {
		super(e);
	}

	public AchievementServiceException() {
		super();
	}
}