package com.misys.stockmarket.mbeans;

public class UserLeagueBean {

	private long leagueId;

	private String name;

	private long rank;

	private long totalPlayers;

	private String portfolioValue;

	private String remainingBalance;

	private String totalValue;

	public String getName() {
		return name;
	}

	public long getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(long leagueId) {
		this.leagueId = leagueId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getRank() {
		return rank;
	}

	public void setRank(long rank) {
		this.rank = rank;
	}

	public long getTotalPlayers() {
		return totalPlayers;
	}

	public void setTotalPlayers(long totalPlayers) {
		this.totalPlayers = totalPlayers;
	}

	public String getPortfolioValue() {
		return portfolioValue;
	}

	public void setPortfolioValue(String portfolioValue) {
		this.portfolioValue = portfolioValue;
	}

	public String getRemainingBalance() {
		return remainingBalance;
	}

	public void setRemainingBalance(String remainingBalance) {
		this.remainingBalance = remainingBalance;
	}

	public String getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(String totalValue) {
		this.totalValue = totalValue;
	}
}
