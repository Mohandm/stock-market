package com.misys.stockmarket.mbeans;

import java.util.List;

public class LeaderBoardFormBean {

	private String leagueId;
	
	private String name;
	
	private String playersCount;
	
	private String stage;
	
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

	public List<LeaguePlayerFormBean> getPlayers() {
		return players;
	}

	public void setPlayers(List<LeaguePlayerFormBean> players) {
		this.players = players;
	}

	private List<LeaguePlayerFormBean> players;
	
}
