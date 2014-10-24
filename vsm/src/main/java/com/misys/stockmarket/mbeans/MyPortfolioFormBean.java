package com.misys.stockmarket.mbeans;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sam Sundar K
 * 
 */
public class MyPortfolioFormBean {
	private String portfolioValue;
	private String remainingBalance;
	private String totalValue;
	private List<StockHoldingFormBean> stockHoldings = new ArrayList<StockHoldingFormBean>();

	public String getPortfolioValue() {
		return portfolioValue;
	}

	public void setPortfolioValue(String portfolioValue) {
		this.portfolioValue = portfolioValue;
	}

	public String getRemainingBalance() {
		return remainingBalance;
	}

	public void setRemainingBalance(String remainingBalance) {
		this.remainingBalance = remainingBalance;
	}

	public String getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(String totalValue) {
		this.totalValue = totalValue;
	}

	public List<StockHoldingFormBean> getStockHoldings() {
		return stockHoldings;
	}

	public void setStockHoldings(List<StockHoldingFormBean> stockHoldings) {
		this.stockHoldings = stockHoldings;
	}

}
