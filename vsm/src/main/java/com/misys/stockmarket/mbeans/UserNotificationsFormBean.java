package com.misys.stockmarket.mbeans;

import java.util.ArrayList;
import java.util.List;

public class UserNotificationsFormBean {

	private List<UserAlertsBean> alertsList = new ArrayList<UserAlertsBean>();
	
	private List<UserAlertsBean> notificationsList = new ArrayList<UserAlertsBean>();

	public List<UserAlertsBean> getAlertsList() {
		return alertsList;
	}

	public void setAlertsList(List<UserAlertsBean> alertsList) {
		this.alertsList = alertsList;
	}

	public List<UserAlertsBean> getNotificationsList() {
		return notificationsList;
	}

	public void setNotificationsList(List<UserAlertsBean> notificationsList) {
		this.notificationsList = notificationsList;
	}
}
