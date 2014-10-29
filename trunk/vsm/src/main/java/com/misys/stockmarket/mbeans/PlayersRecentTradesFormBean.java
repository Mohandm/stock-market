package com.misys.stockmarket.mbeans;

public class PlayersRecentTradesFormBean {
	private String tikerSymbol;
	
	private String dateTime;
	
	private String quantity;
	
	
	public String getTikerSymbol() {
		return tikerSymbol;
	}


	public void setTikerSymbol(String tikerSymbol) {
		this.tikerSymbol = tikerSymbol;
	}


	public String getDateTime() {
		return dateTime;
	}


	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}


	public String getQuantity() {
		return quantity;
	}


	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}


	public String getOrderType() {
		return orderType;
	}


	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}


	private String orderType;
}
