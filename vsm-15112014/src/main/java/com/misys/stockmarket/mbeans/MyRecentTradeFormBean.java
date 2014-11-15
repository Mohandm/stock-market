package com.misys.stockmarket.mbeans;

import java.util.ArrayList;
import java.util.List;

public class MyRecentTradeFormBean {

	private List<OrderHistoryFormBean> recentTrades = new ArrayList<OrderHistoryFormBean>();

	public List<OrderHistoryFormBean> getRecentTrades() {
		return recentTrades;
	}

	public void setRecentTrades(List<OrderHistoryFormBean> recentTrades) {
		this.recentTrades = recentTrades;
	}

}
