package com.misys.stockmarket.services;

import java.util.Date;
import java.util.List;

public interface IFinancialService {

	public String getStockHistory(List<String> stockList, Date startDate,
			Date endDate);

}
