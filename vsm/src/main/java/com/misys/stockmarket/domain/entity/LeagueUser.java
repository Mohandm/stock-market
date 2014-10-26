package com.misys.stockmarket.domain.entity;

import java.io.Serializable;
import java.math.BigDecimal;

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
 * The persistent class for the LEAGUE_USER database table.
 * 
 */
@Entity
@Table(name = "LEAGUE_USER")
@SequenceGenerator(name = "SEQ_LEAGUE_USER")
public class LeagueUser implements BaseEntity, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_LEAGUE_USER")
	@Column(name = "LEAGUE_USER_ID", unique = true, nullable = false)
	private long leagueUserId;

	// bi-directional many-to-one association to LeagueMaster
	@ManyToOne
	@JoinColumn(name = "LEAGUE_ID")
	private LeagueMaster leagueMaster;

	// bi-directional many-to-one association to UserMaster
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private UserMaster userMaster;

	@Column(name = "REMAINING_AMOUNT", precision = 10, scale = 2)
	private BigDecimal remainingAmount;

	public LeagueMaster getLeagueMaster() {
		return leagueMaster;
	}

	public void setLeagueMaster(LeagueMaster leagueMaster) {
		this.leagueMaster = leagueMaster;
	}

	public UserMaster getUserMaster() {
		return userMaster;
	}

	public void setUserMaster(UserMaster userMaster) {
		this.userMaster = userMaster;
	}

	public BigDecimal getRemainingAmount() {
		return remainingAmount;
	}

	public void setRemainingAmount(BigDecimal remainingAmount) {
		this.remainingAmount = remainingAmount;
	}

	public long getLeagueUserId() {
		return leagueUserId;
	}

	public void setLeagueUserId(long leagueUserId) {
		this.leagueUserId = leagueUserId;
	}

}
