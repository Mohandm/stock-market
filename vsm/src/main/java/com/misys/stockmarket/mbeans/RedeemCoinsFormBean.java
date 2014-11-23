package com.misys.stockmarket.mbeans;

public class RedeemCoinsFormBean {

	private long coins;
	
	private long leagueId;

	private long totalCoins;
	
	public long getCoins() {
		return coins;
	}

	public void setCoins(long coins) {
		this.coins = coins;
	}

	public long getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(long leagueId) {
		this.leagueId = leagueId;
	}
	
	public long getTotalCoins() {
		return totalCoins;
	}

	public void setTotalCoins(long totalCoins) {
		this.totalCoins = totalCoins;
	}	
}
