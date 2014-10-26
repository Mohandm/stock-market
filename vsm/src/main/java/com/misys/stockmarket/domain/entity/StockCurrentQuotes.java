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
 * The persistent class for the STOCK_CURRENT_QUOTES database table.
 * 
 */
@Entity
@Table(name = "STOCK_CURRENT_QUOTES", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"STOCK_ID"}) })
@SequenceGenerator(name = "SEQ_STOCK_CURRENT_QUOTES")
public class StockCurrentQuotes implements BaseEntity, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_STOCK_CURRENT_QUOTES")
	@Column(name = "CURRENT_QUOTE_ID")
	private long currentQuoteId;

	public long getCurrentQuoteId() {
		return currentQuoteId;
	}

	public void setCurrentQuoteId(long currentQuoteId) {
		this.currentQuoteId = currentQuoteId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_TIMESTAMP")
	private Date updatedTimeStamp;
	
	public Date getUpdatedTimeStamp() {
		return updatedTimeStamp;
	}

	public void setUpdatedTimeStamp(Date updatedTimeStamp) {
		this.updatedTimeStamp = updatedTimeStamp;
	}

	@Column(name = "CURRENCY", length = 3)
	private String currency;
	
	@Column(name = "DAYS_RANGE", length = 30)
	private String daysRange;
	
	@Column(name = "YEAR_RANGE", length = 30)
	private String yearRange;
	
	@Column(name = "LAST_TRADE_PRICE_ONLY", precision = 8, scale = 2)
	private BigDecimal lastTradePriceOnly;
	
	@Column(name = "OPEN_PRICE", precision = 8, scale = 2)
	private BigDecimal open;
	
	@Column(name = "PREVIOUS_CLOSE", precision = 8, scale = 2)
	private BigDecimal previousClose;
	
	@Column(name = "CHANGE", length = 30)
	private String change;
	
	@Column(name = "CHANGE_IN_PERCENT", length = 30)
	private String changeinPercent;
	
	@Column(name = "LAST_TRADE_TIME", length = 30)
	private String lastTradeTime;
	
	@Column(name = "LAST_TRADE_DATE", length = 30)
	private String lastTradeDate;
	
	@Column(name = "VOLUME")
	private BigDecimal volume;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDaysRange() {
		return daysRange;
	}

	public void setDaysRange(String daysRange) {
		this.daysRange = daysRange;
	}

	public String getYearRange() {
		return yearRange;
	}

	public void setYearRange(String yearRange) {
		this.yearRange = yearRange;
	}

	public BigDecimal getLastTradePriceOnly() {
		return lastTradePriceOnly;
	}

	public void setLastTradePriceOnly(BigDecimal lastTradePriceOnly) {
		this.lastTradePriceOnly = lastTradePriceOnly;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	public BigDecimal getPreviousClose() {
		return previousClose;
	}

	public void setPreviousClose(BigDecimal previousClose) {
		this.previousClose = previousClose;
	}

	public String getChange() {
		return change;
	}

	public void setChange(String change) {
		this.change = change;
	}

	public String getChangeinPercent() {
		return changeinPercent;
	}

	public void setChangeinPercent(String changeinPercent) {
		this.changeinPercent = changeinPercent;
	}

	public String getLastTradeTime() {
		return lastTradeTime;
	}

	public void setLastTradeTime(String lastTradeTime) {
		this.lastTradeTime = lastTradeTime;
	}

	public String getLastTradeDate() {
		return lastTradeDate;
	}

	public void setLastTradeDate(String lastTradeDate) {
		this.lastTradeDate = lastTradeDate;
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

	@ManyToOne
	@JoinColumn(name = "STOCK_ID")
	private StockMaster stockMaster;

	public StockCurrentQuotes() {
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "StockCurrentQuotes [currentQuoteId="+ currentQuoteId +", currency=" + currency + ", updatedTimeStamp=" + updatedTimeStamp +
				", daysRange=" + daysRange + ", yearRange=" + yearRange + ", lastTradePriceOnly=" + lastTradePriceOnly + 
				", open=" + open + ", previousClose=" + previousClose + 
				", change=" + change + ", changeinPercent=" + changeinPercent + 
				", lastTradeTime=" + lastTradeTime + ", lastTradeDate=" + lastTradeDate + 
				", volume=" + volume + "]";
	}
}
