package com.misys.stockmarket.domain.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the STOCK_MASTER database table.
 * 
 */
@Entity
@Table(name = "STOCK_MASTER")
@NamedQuery(name = "StockMaster.findAll", query = "SELECT s FROM StockMaster s")
public class StockMaster implements BaseEntity, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "STOCK_ID")
	private long stockId;

	@Column(name = "TIKER_SYMBOL", length = 20)
	private String tikerSymbol;

	// bi-directional many-to-one association to OrderMaster
	/*
	 * @OneToMany
	 * 
	 * @JoinColumn(name = "ORDER_ID") private List<OrderMaster> orderMasters;
	 * 
	 * // bi-directional many-to-one association to PortfolioMaster
	 * 
	 * @OneToMany
	 * 
	 * @JoinColumn(name = "PORTFOLIO_ID") private List<PortfolioMaster>
	 * portfolioMasters;
	 */
	public StockMaster() {
	}

	public long getStockId() {
		return stockId;
	}

	public void setStockId(long stockId) {
		this.stockId = stockId;
	}

	public String getTikerSymbol() {
		return tikerSymbol;
	}

	public void setTikerSymbol(String tikerSymbol) {
		this.tikerSymbol = tikerSymbol;
	}
}