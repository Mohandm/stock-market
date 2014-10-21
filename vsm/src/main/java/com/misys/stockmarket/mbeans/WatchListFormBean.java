package com.misys.stockmarket.mbeans;

import java.math.BigDecimal;

public class WatchListFormBean {
	private String tikerSymbol;
	
	private String name;

	private BigDecimal limit;
	
	private BigDecimal lowerLimit;
	
	public BigDecimal getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(BigDecimal lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	private BigDecimal lastTradePrice;

	public String getTikerSymbol() {
		return tikerSymbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getLastTradePrice() {
		return lastTradePrice;
	}

	public void setLastTradePrice(BigDecimal lastTradePrice) {
		this.lastTradePrice = lastTradePrice;
	}

	public void setTikerSymbol(String tikerSymbol) {
		this.tikerSymbol = tikerSymbol;
	}

	public BigDecimal getLimit() {
		return limit;
	}

	public void setLimit(BigDecimal limit) {
		this.limit = limit;
	}
}
