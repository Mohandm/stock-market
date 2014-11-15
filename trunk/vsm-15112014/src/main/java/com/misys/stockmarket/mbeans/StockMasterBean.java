package com.misys.stockmarket.mbeans;

public class StockMasterBean {

	private String tikerSymbol;

	private String active;

	private String name;
	
	private String stockId;

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
