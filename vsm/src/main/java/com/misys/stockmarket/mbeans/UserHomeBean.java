package com.misys.stockmarket.mbeans;

import java.util.List;

public class UserHomeBean {

	private String email;
	
	private String firstName;

	private String lastName;

	private String gender;

	private long coins;
	
	private long completedAchievements;
	
	private long pendingAchievements;
	
	private long totalAchievements;
	
	private long followers;

	private long following;

	private long acceptedInvites;
	
	private List<UserLeagueBean> leagues;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public long getCoins() {
		return coins;
	}

	public void setCoins(long coins) {
		this.coins = coins;
	}

	public long getCompletedAchievements() {
		return completedAchievements;
	}

	public void setCompletedAchievements(long completedAchievements) {
		this.completedAchievements = completedAchievements;
	}

	public long getPendingAchievements() {
		return pendingAchievements;
	}

	public void setPendingAchievements(long pendingAchievements) {
		this.pendingAchievements = pendingAchievements;
	}

	public long getTotalAchievements() {
		return totalAchievements;
	}

	public void setTotalAchievements(long totalAchievements) {
		this.totalAchievements = totalAchievements;
	}

	public long getFollowers() {
		return followers;
	}

	public void setFollowers(long followers) {
		this.followers = followers;
	}

	public long getFollowing() {
		return following;
	}

	public void setFollowing(long following) {
		this.following = following;
	}

	public long getAcceptedInvites() {
		return acceptedInvites;
	}

	public void setAcceptedInvites(long acceptedInvites) {
		this.acceptedInvites = acceptedInvites;
	}

	public List<UserLeagueBean> getLeagues() {
		return leagues;
	}

	public void setLeagues(List<UserLeagueBean> leagues) {
		this.leagues = leagues;
	}
}
