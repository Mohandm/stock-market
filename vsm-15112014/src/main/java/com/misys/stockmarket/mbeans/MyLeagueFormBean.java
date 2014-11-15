package com.misys.stockmarket.mbeans;

public class MyLeagueFormBean {
	private String leagueId;
	private String name;
	private String locked;
	private String stage;
	private String playersCount;

	public String getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(String leagueId) {
		this.leagueId = leagueId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocked() {
		return locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	public String getPlayersCount() {
		return playersCount;
	}

	public void setPlayersCount(String playersCount) {
		this.playersCount = playersCount;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

}
