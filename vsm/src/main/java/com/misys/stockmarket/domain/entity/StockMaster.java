package com.misys.stockmarket.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the STOCK_MASTER database table.
 * 
 */
@Entity
@Table(name = "STOCK_MASTER")
@SequenceGenerator(name = "SEQ_STOCK_MASTER")
public class StockMaster implements BaseEntity, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STOCK_MASTER")
	@Column(name = "STOCK_ID")
	private long stockId;

	@Column(name = "TIKER_SYMBOL", length = 20)
	private String tikerSymbol;

	@Column(name = "ACTIVE", length = 1)
	private String active;

	@Column(name = "NAME", length = 50)
	private String name;

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

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "StockMaster [stockId=" + stockId + ", tikerSymbol="
				+ tikerSymbol + ", name=" + name + ", active=" + active + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((tikerSymbol == null) ? 0 : tikerSymbol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof StockMaster)) {
			return false;
		}
		StockMaster other = (StockMaster) obj;
		if (tikerSymbol == null) {
			if (other.tikerSymbol != null) {
				return false;
			}
		} else if (!tikerSymbol.equals(other.tikerSymbol)) {
			return false;
		}
		return true;
	}

}