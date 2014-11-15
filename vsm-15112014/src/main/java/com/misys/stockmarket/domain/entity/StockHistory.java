package com.misys.stockmarket.domain.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * The persistent class for the STOCK_HISTORY database table.
 * 
 */
@Entity
@Table(name = "STOCK_HISTORY", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"STOCK_ID", "STOCK_DATE" }) })
@SequenceGenerator(name = "SEQ_STOCK_HISTORY")
public class StockHistory extends AuditableEntity implements BaseEntity, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_STOCK_HISTORY")
	@Column(name = "HISTORY_ID")
	private long historyId;

	@Temporal(TemporalType.DATE)
	@Column(name = "STOCK_DATE")
	private Date stockDate;

	@Column(name = "OPEN_PRICE", precision = 8, scale = 2)
	private BigDecimal open;

	@Column(name = "HIGH", precision = 8, scale = 2)
	private BigDecimal high;

	@Column(name = "LOW", precision = 8, scale = 2)
	private BigDecimal low;

	@Column(name = "CLOSE_PRICE", precision = 8, scale = 2)
	private BigDecimal close;

	@Column(name = "VOLUME")
	private BigDecimal volume;

	@ManyToOne
	@JoinColumn(name = "STOCK_ID")
	private StockMaster stockMaster;

	public StockHistory() {
	}

	public long getHistoryId() {
		return historyId;
	}

	public void setHistoryId(long historyId) {
		this.historyId = historyId;
	}

	public Date getStockDate() {
		return stockDate;
	}

	public void setStockDate(Date stockDate) {
		this.stockDate = stockDate;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getLow() {
		return low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}

	public BigDecimal getClose() {
		return close;
	}

	public void setClose(BigDecimal close) {
		this.close = close;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public StockMaster getStockMaster() {
		return stockMaster;
	}

	public void setStockMaster(StockMaster stockMaster) {
		this.stockMaster = stockMaster;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "StockHistory [historyId=" + historyId + ", stockDate="
				+ stockDate + ", open=" + open + ", high=" + high + ", low="
				+ low + ", close=" + close + ", volume=" + volume + "]";
	}

}