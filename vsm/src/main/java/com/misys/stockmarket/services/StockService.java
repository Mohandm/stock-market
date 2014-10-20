package com.misys.stockmarket.services;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.dao.StockDAO;
import com.misys.stockmarket.domain.entity.StockCurrentQuotes;
import com.misys.stockmarket.domain.entity.StockHistory;
import com.misys.stockmarket.domain.entity.StockMaster;
import com.misys.stockmarket.exception.DBRecordNotFoundException;
import com.misys.stockmarket.exception.FinancialServiceException;
import com.misys.stockmarket.model.json.QuoteCurrentJSONModel;
import com.misys.stockmarket.model.json.QuoteHistoryJSONModel;
import com.misys.stockmarket.utility.DateUtils;
import com.misys.stockmarket.utility.YQLUtil;

@Service("stockService")
@Repository
public class StockService {

	private static final Log LOG = LogFactory.getLog(StockService.class);
	@Inject
	private StockDAO stockDAO;

	@Inject
	IFinancialService financialService;

	public List<String> listAllActiveStockSymbols() {
		return stockDAO.findAllActiveStockSymbols();
	}
	
	public List<String> listAllStockSymbols() {
		return stockDAO.findAllStockSymbols();
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

	public List<StockHistory> listStockHistory(String tickerSymbol,
			Date startDate, Date endDate) {
		StockMaster stockMaster = stockDAO.findByTickerSymbol(tickerSymbol);
		return stockDAO.findStockHistory(stockMaster.getStockId(), startDate,
				endDate);
	}
	
	public List<StockHistory> listStockHistory(String tickerSymbol) {
		StockMaster stockMaster = stockDAO.findByTickerSymbol(tickerSymbol);
		return stockDAO.findStockHistoryByStockId(stockMaster.getStockId());
	}
	
	public StockCurrentQuotes getStockCurrentQuoteByStockSymbol(String tickerSymbol) {
		StockMaster stockMaster = stockDAO.findByAllTickerSymbol(tickerSymbol);
		return stockDAO.findStockCurrentQuoteByStockId(stockMaster.getStockId());
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

	public void updateStockCurrent(
			List<QuoteCurrentJSONModel> quoteCurrentJSONModelList) {
		Map<String, StockMaster> stockMasterByTickerMap = new HashMap<String, StockMaster>();
		
		Date date = new Date();
		for (QuoteCurrentJSONModel quoteCurrentJSONModel : quoteCurrentJSONModelList) {
			StockCurrentQuotes stockCurrent = new StockCurrentQuotes();

			LOG.debug("Symbol : " + quoteCurrentJSONModel.symbol);
			stockCurrent.setChange(quoteCurrentJSONModel.Change);
			stockCurrent
					.setChangeinPercent(quoteCurrentJSONModel.ChangeinPercent);
			stockCurrent.setCurrency(quoteCurrentJSONModel.Currency);
			stockCurrent.setDaysRange(quoteCurrentJSONModel.DaysRange);
			stockCurrent.setLastTradeDate(quoteCurrentJSONModel.LastTradeDate);
			stockCurrent.setLastTradePriceOnly(new BigDecimal(
					quoteCurrentJSONModel.LastTradePriceOnly));
			stockCurrent.setLastTradeTime(quoteCurrentJSONModel.LastTradeTime);
			stockCurrent.setOpen(new BigDecimal(quoteCurrentJSONModel.Open));
			stockCurrent.setPreviousClose(new BigDecimal(
					quoteCurrentJSONModel.PreviousClose));
			stockCurrent
					.setVolume(new BigDecimal(quoteCurrentJSONModel.Volume));
			stockCurrent.setYearRange(quoteCurrentJSONModel.YearRange);
			stockCurrent.setUpdatedTimeStamp(date);
			
			if (stockMasterByTickerMap
					.containsKey(quoteCurrentJSONModel.symbol)) {
				StockMaster stockMaster = stockMasterByTickerMap
						.get(quoteCurrentJSONModel.symbol);
				stockCurrent.setStockMaster(stockMaster);
			} else {
				StockMaster stockMaster = stockDAO
						.findByAllTickerSymbol(quoteCurrentJSONModel.symbol);
				stockCurrent.setStockMaster(stockMaster);
				stockMasterByTickerMap.put(quoteCurrentJSONModel.symbol,
						stockMaster);
			}
			stockDAO.updateCurrentStock(stockCurrent);
		}
	}

	public void updateStocks(List<StockMaster> updateStocksList) {
		for (StockMaster stockMaster : updateStocksList) {
			stockDAO.update(stockMaster);
		}
	}
	
	public void updateStockCurrentQuotes(List<String> stockList)
			throws FinancialServiceException {
		
		String responseJSONString = financialService.getStockCurrent(stockList);
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode rootNode = mapper.readValue(responseJSONString,
					JsonNode.class);
			JsonNode quoteArray = rootNode.findValue("quote");
			List<QuoteCurrentJSONModel> quoteCurrentJSONModels = mapper
					.readValue(
							quoteArray,
							mapper.getTypeFactory()
									.constructCollectionType(List.class,
											QuoteCurrentJSONModel.class));
			updateStockCurrent(quoteCurrentJSONModels);
		} catch (JsonParseException e) {
			throw new FinancialServiceException(e);
		} catch (JsonMappingException e) {
			throw new FinancialServiceException(e);
		} catch (IOException e) {
			throw new FinancialServiceException(e);
		}
	}
	
	public void updateStockHistory(StockMaster stockMaster)
			throws FinancialServiceException {
		Date maxStockDate = stockDAO.findMaxStockHistoryStockDate(stockMaster
				.getStockId());
		int differenceInDays = 0;
		if (maxStockDate != null) {
			LOG.info("Last updated stock date for "
					+ stockMaster.getTikerSymbol() + " " + maxStockDate);
			differenceInDays = DateUtils.differenceInDays(new Date(),
					maxStockDate);
			LOG.info("Last Updated Stock Date vs Current Date days difference for "
					+ stockMaster.getTikerSymbol() + " " + differenceInDays);
		} else {
			LOG.info("No History Found for ticker symbol "
					+ stockMaster.getTikerSymbol());
			differenceInDays = -1;
			LOG.info("Updating Last Six Months History for ticker symbol "
					+ stockMaster.getTikerSymbol());
		}
		Date pastStockDate = null;
		if (differenceInDays > 1) {
			pastStockDate = DateUtils
					.getPastDateFromCurrentDate(differenceInDays);
		} else if (differenceInDays == -1) {
			pastStockDate = DateUtils.getPastMonthFromCurrentDate(6);
		}
		if (differenceInDays > 1 || differenceInDays == -1) {
			String responseJSONString = financialService.getStockHistory(
					stockMaster.getTikerSymbol(), pastStockDate, new Date());
			ObjectMapper mapper = new ObjectMapper();
			try {
				JsonNode rootNode = mapper.readValue(responseJSONString,
						JsonNode.class);
				JsonNode quoteArray = rootNode.findValue("quote");
				List<QuoteHistoryJSONModel> quoteHistoryJSONModels;
				if(!quoteArray.isArray()) 
				{
					String quoteString = quoteArray.toString();
					ObjectMapper mapperTemp = new ObjectMapper();
				    JsonNode actualObj = mapperTemp.readTree("["+quoteString+"]");
					quoteHistoryJSONModels = mapper
							.readValue(
									actualObj,
									mapper.getTypeFactory()
											.constructCollectionType(List.class,
													QuoteHistoryJSONModel.class));
				}
				else
				{
					quoteHistoryJSONModels = mapper
							.readValue(
									quoteArray,
									mapper.getTypeFactory()
											.constructCollectionType(List.class,
													QuoteHistoryJSONModel.class));
				}
				
				saveStockHistory(quoteHistoryJSONModels);
			} catch (JsonParseException e) {
				throw new FinancialServiceException(e);
			} catch (JsonMappingException e) {
				throw new FinancialServiceException(e);
			} catch (IOException e) {
				throw new FinancialServiceException(e);
			}
		} else {
			LOG.info("No Further Update is required. The stock is up to date for Symbol: "
					+ stockMaster.getTikerSymbol());
		}
	}
}
