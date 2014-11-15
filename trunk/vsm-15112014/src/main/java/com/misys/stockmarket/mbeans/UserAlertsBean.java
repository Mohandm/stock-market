package com.misys.stockmarket.mbeans;


public class UserAlertsBean {

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNotifiedDate() {
		return notifiedDate;
	}

	public void setNotifiedDate(String notifiedDate) {
		this.notifiedDate = notifiedDate;
	}

	private String message;
	
	private String notifiedDate;
}
