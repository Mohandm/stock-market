package com.misys.stockmarket.mbeans;

public class FollowRequestFormBean {
	private String userId;
	
	private String leagurId;

	public String getLeagurId() {
		return leagurId;
	}

	public void setLeagurId(String leagurId) {
		this.leagurId = leagurId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
