package com.misys.stockmarket.mbeans;

public class LeaguePlayerFormBean {
	private String userId;
	private String leagueUserId;
	private String name;
	private String photo;
	private String totalValue;
	private String followerCount;
	private String rank;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLeagueUserId() {
		return leagueUserId;
	}

	public void setLeagueUserId(String leagueUserId) {
		this.leagueUserId = leagueUserId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(String totalValue) {
		this.totalValue = totalValue;
	}

	public String getFollowerCount() {
		return followerCount;
	}

	public void setFollowerCount(String followerCount) {
		this.followerCount = followerCount;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

}
