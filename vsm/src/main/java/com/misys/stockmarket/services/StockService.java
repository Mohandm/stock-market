package com.misys.stockmarket.services;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.dao.StockDAO;
import com.misys.stockmarket.domain.entity.StockHistory;
import com.misys.stockmarket.domain.entity.StockMaster;
import com.misys.stockmarket.exception.DBRecordNotFoundException;
import com.misys.stockmarket.model.json.QuoteHistoryJSONModel;
import com.misys.stockmarket.utility.DateUtils;
import com.misys.stockmarket.utility.YQLUtil;

@Service("stockService")
@Repository
public class StockService {
	@Inject
	private StockDAO stockDAO;

	public List<String> listAllActiveStockSymbols() {
		return stockDAO.findAllActiveStockSymbols();
	}

	public List<StockMaster> listAllActiveStocks() {
		return stockDAO.findAllActiveStocks();
	}

	public List<StockMaster> listAllInActiveStocks() {
		return stockDAO.findAllInActiveStocks();
	}

	public List<StockMaster> listAllStocks() {
		return stockDAO.findAll(StockMaster.class);
	}

	public void saveStocks(List<StockMaster> newStocksList) {
		for (StockMaster stockMaster : newStocksList) {
			stockDAO.persist(stockMaster);
		}
	}

	public void saveStockHistory(
			List<QuoteHistoryJSONModel> quoteHistoryJSONModelList) {
		Map<String, StockMaster> stockMasterByTickerMap = new HashMap<String, StockMaster>();
		for (QuoteHistoryJSONModel quoteHistoryJSONModel : quoteHistoryJSONModelList) {
			StockHistory stockHistory = new StockHistory();
			stockHistory.setClose(new BigDecimal(quoteHistoryJSONModel.Close));
			stockHistory.setHigh(new BigDecimal(quoteHistoryJSONModel.High));
			stockHistory.setLow(new BigDecimal(quoteHistoryJSONModel.Low));
			stockHistory.setOpen(new BigDecimal(quoteHistoryJSONModel.Open));
			stockHistory.setStockDate(DateUtils.stringDateToDate(
					quoteHistoryJSONModel.Date, YQLUtil.YQL_DATE_FORMAT));
			stockHistory
					.setVolume(new BigDecimal(quoteHistoryJSONModel.Volume));
			if (stockMasterByTickerMap
					.containsKey(quoteHistoryJSONModel.Symbol)) {
				StockMaster stockMaster = stockMasterByTickerMap
						.get(quoteHistoryJSONModel.Symbol);
				stockHistory.setStockMaster(stockMaster);
			} else {
				StockMaster stockMaster = stockDAO
						.findByTickerSymbol(quoteHistoryJSONModel.Symbol);
				stockHistory.setStockMaster(stockMaster);
				stockMasterByTickerMap.put(quoteHistoryJSONModel.Symbol,
						stockMaster);
			}
			try {
				stockDAO.findStockHistoryByStockIdStockDate(stockHistory
						.getStockMaster().getStockId(), stockHistory
						.getStockDate());
			} catch (DBRecordNotFoundException e) {
				stockDAO.persist(stockHistory);
			}
		}
	}

	public void updateStocks(List<StockMaster> updateStocksList) {
		for (StockMaster stockMaster : updateStocksList) {
			stockDAO.update(stockMaster);
		}
	}

}
