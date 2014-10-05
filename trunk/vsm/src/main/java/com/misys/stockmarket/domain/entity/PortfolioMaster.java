package com.misys.stockmarket.domain.entity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the PORTFOLIO_MASTER database table.
 * 
 */
@Entity
@Table(name = "PORTFOLIO_MASTER")
public class PortfolioMaster implements BaseEntity, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PORTFOLIO_ID", unique = true, nullable = false)
	private long portfolioId;

	@ManyToOne
	@JoinColumn(name = "STOCK_ID")
	private StockMaster stockMaster;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private UserMaster userMaster;

	public PortfolioMaster() {
	}

	public long getPortfolioId() {
		return this.portfolioId;
	}

	public void setPortfolioId(long portfolioId) {
		this.portfolioId = portfolioId;
	}

	public UserMaster getUserMaster() {
		return userMaster;
	}

	public void setUserMaster(UserMaster userMaster) {
		this.userMaster = userMaster;
	}

}