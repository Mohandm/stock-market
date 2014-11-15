package com.misys.stockmarket.mbeans;

public class FollowRequestFormBean {
	private String userId;
	
	private String leagueId;

	
	public String getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(String leagueId) {
		this.leagueId = leagueId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
