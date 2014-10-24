package com.misys.stockmarket.mbeans;

import java.math.BigDecimal;

public class StockHoldingFormBean {
	private String tikerSymbol;
	private String name;
	private String volume;
	private String pricePaid;
	private String marketPrice;
	private String marketCalculatedValue;
	private String lifeTimeReturn;

	public String getTikerSymbol() {
		return tikerSymbol;
	}

	public void setTikerSymbol(String tikerSymbol) {
		this.tikerSymbol = tikerSymbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getPricePaid() {
		return pricePaid;
	}

	public void setPricePaid(String pricePaid) {
		this.pricePaid = pricePaid;
	}

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getMarketCalculatedValue() {
		return marketCalculatedValue;
	}

	public void setMarketCalculatedValue(String marketCalculatedValue) {
		this.marketCalculatedValue = marketCalculatedValue;
	}

	public String getLifeTimeReturn() {
		return lifeTimeReturn;
	}

	public void setLifeTimeReturn(String lifeTimeReturn) {
		this.lifeTimeReturn = lifeTimeReturn;
	}

	public void addVolume(BigDecimal unitsTraded) {
		BigDecimal unitsTradedCurrentValue = this.volume == null ? new BigDecimal(
				"0.00") : new BigDecimal(this.volume);
		unitsTradedCurrentValue = unitsTradedCurrentValue.add(unitsTraded);
		this.volume = unitsTradedCurrentValue.toPlainString();
	}

	public void addPricePaid(BigDecimal executionPrice) {
		BigDecimal pricePaidCurrentValue = this.pricePaid == null ? new BigDecimal(
				"0.00") : new BigDecimal(this.pricePaid);
		pricePaidCurrentValue = pricePaidCurrentValue.add(executionPrice);
		this.pricePaid = pricePaidCurrentValue.toPlainString();
	}
}
