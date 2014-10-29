package com.misys.stockmarket.mbeans;

import java.math.BigDecimal;

public class OrderFormBean {


	/*private String intraday;*/

	private BigDecimal orderPrice;

	private String priceType;

	private BigDecimal volume;

	private String symbol;
	
	private String type;
	
	private long leagueUserId;
	
	private String stockHoldingVolume;


	public String getStockHoldingVolume() {
		return stockHoldingVolume;
	}

	public void setStockHoldingVolume(String stockHoldingVolume) {
		this.stockHoldingVolume = stockHoldingVolume;
	}

	public long getLeagueUserId() {
		return leagueUserId;
	}

	public void setLeagueUserId(long leagueUserId) {
		this.leagueUserId = leagueUserId;
	}

	/*public String getIntraday() {
		return intraday;
	}

	public void setIntraday(String intraday) {
		this.intraday = intraday;
	}*/

	public BigDecimal getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	

}
