package com.misys.stockmarket.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the STOCK_CURRENT_QUOTES database table.
 * 
 */
@Entity
@Table(name = "FOLLOWER_MASTER")
@SequenceGenerator(name = "SEQ_FOLLOWER_MASTER")
public class FollowerMaster implements BaseEntity, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_FOLLOWER_MASTER")
	@Column(name = "FOLLOWER_MASTER_ID")
	private long followerMasterId;
	
	// bi-directional many-to-one association to LeagueMaster
	@ManyToOne
	@JoinColumn(name = "LEAGUE_ID")
	private LeagueMaster leagueMaster;
		
	// bi-directional many-to-one association to UserMaster
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private UserMaster followerUserMaster;
	
	@Column(name = "PLAYER_USER_ID")
	private long playerUserId;
	
	public LeagueMaster getLeagueMaster() {
		return leagueMaster;
	}

	public void setLeagueMaster(LeagueMaster leagueMaster) {
		this.leagueMaster = leagueMaster;
	}

	@Column(name = "STATUS", length = 2)
	private String status;
	
	public FollowerMaster() {
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "WatchStock [followerMasterId="+ followerMasterId +", playerUserId=" + playerUserId + ", status=" + status + "]";
	}

	public long getFollowerMasterId() {
		return followerMasterId;
	}

	public void setFollowerMasterId(long followerMasterId) {
		this.followerMasterId = followerMasterId;
	}

	public UserMaster getFollowerUserMaster() {
		return followerUserMaster;
	}

	public void setFollowerUserMaster(UserMaster followerUserMaster) {
		this.followerUserMaster = followerUserMaster;
	}

	public long getPlayerUserId() {
		return playerUserId;
	}

	public void setPlayerUserId(long playerUserId) {
		this.playerUserId = playerUserId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
