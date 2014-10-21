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
import javax.persistence.Table;

/**
 * The persistent class for the WATCH_STOCK database table.
 * 
 */
@Entity
@Table(name = "WATCH_STOCK")
public class WatchStock implements BaseEntity, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "WATCH_STOCK_ID")
	private long watchStockId;
	
	// bi-directional many-to-one association to StockMaster
	@ManyToOne
	@JoinColumn(name = "STOCK_ID")
	private StockMaster stockMaster;
	
	// bi-directional many-to-one association to UserMaster
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private UserMaster userMaster;
	
	public long getWatchStockId() {
		return watchStockId;
	}

	public void setWatchStockId(long watchStockId) {
		this.watchStockId = watchStockId;
	}

	public StockMaster getStockMaster() {
		return stockMaster;
	}

	public void setStockMaster(StockMaster stockMaster) {
		this.stockMaster = stockMaster;
	}

	public UserMaster getUserMaster() {
		return userMaster;
	}

	public void setUserMaster(UserMaster userMaster) {
		this.userMaster = userMaster;
	}

	public BigDecimal getLimit() {
		return limit;
	}

	public void setLimit(BigDecimal limit) {
		this.limit = limit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "LIMIT", precision = 8, scale = 2)
	private BigDecimal limit;
	
	@Column(name = "LOWER_LIMIT", precision = 8, scale = 2)
	private BigDecimal lowerLimit;
	
	@Column(name = "STATUS", length = 2)
	private String status;
	
	public WatchStock() {
	}

	public BigDecimal getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(BigDecimal lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "WatchStock [watchStockId="+ watchStockId +", limit=" + limit + ", status=" + status + ", lowerLimit=" + lowerLimit + "]";
	}
}
