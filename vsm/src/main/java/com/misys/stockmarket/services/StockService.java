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
import org.springframework.transaction.annotation.Transactional;

import com.misys.stockmarket.dao.StockDAO;
import com.misys.stockmarket.domain.entity.StockCurrentQuotes;
import com.misys.stockmarket.domain.entity.StockHistory;
import com.misys.stockmarket.domain.entity.StockMaster;
import com.misys.stockmarket.enums.STOCK_ERR_CODES;
import com.misys.stockmarket.exception.DAOException;
import com.misys.stockmarket.exception.DBRecordNotFoundException;
import com.misys.stockmarket.exception.FinancialServiceException;
import com.misys.stockmarket.exception.ServiceException;
import com.misys.stockmarket.exception.service.StockServiceException;
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

	public List<String> listAllActiveStockSymbols()
			throws StockServiceException {
		try {
			return stockDAO.findAllActiveStockSymbols();
		} catch (DAOException e) {
			throw new StockServiceException(e);
		}
	}

	public List<String> listAllStockSymbols() {
		return stockDAO.findAllStockSymbols();
	}

	public List<StockMaster> listAllActiveStocks() throws StockServiceException {
		try {
			return stockDAO.findAllActiveStocks();
		} catch (DAOException e) {
			throw new StockServiceException(e);
		}
	}

	public List<StockMaster> listAllInActiveStocks()
			throws StockServiceException {
		try {
			return stockDAO.findAllInActiveStocks();
		} catch (DAOException e) {
			throw new StockServiceException(e);
		}
	}

	public List<StockMaster> listAllStocks() throws ServiceException {
		try {
			return stockDAO.findAll(StockMaster.class);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<StockCurrentQuotes> listAllCurrentStockQuotes()
			throws ServiceException {
		try {
			return stockDAO.findAll(StockCurrentQuotes.class);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public void saveStocks(List<StockMaster> newStocksList) {
		for (StockMaster stockMaster : newStocksList) {
			try {
				stockDAO.persist(stockMaster);
			} catch (DAOException e) {
				// TODO: SHOULD I CONTINUE WITH EXCEPTION CHECK LATER?
				LOG.error(e);
			}
		}
	}

	public List<StockHistory> listStockHistory(String tickerSymbol,
			Date startDate, Date endDate) throws StockServiceException {
		try {
			StockMaster stockMaster = stockDAO.findByTickerSymbol(tickerSymbol);
			return stockDAO.findStockHistory(stockMaster.getStockId(),
					startDate, endDate);
		} catch (DAOException e) {
			throw new StockServiceException(e);
		}
	}

	public List<StockHistory> listStockHistory(String tickerSymbol)
			throws StockServiceException {
		try {
			StockMaster stockMaster = stockDAO.findByTickerSymbol(tickerSymbol);
			return stockDAO.findStockHistoryByStockId(stockMaster.getStockId());
		} catch (DAOException e) {
			throw new StockServiceException(e);
		}
	}

	public StockCurrentQuotes getStockCurrentQuoteByStockSymbol(
			String tickerSymbol) throws StockServiceException {
		try {
			return stockDAO.findStockCurrentQuoteByTickerSymbol(tickerSymbol);
		} catch (DBRecordNotFoundException e) {
			throw new StockServiceException(
					STOCK_ERR_CODES.CURRENT_DAY_STOCK_NOT_FOUND, e);
		} catch (DAOException e) {
			throw new StockServiceException(e);
		}
	}

	public StockCurrentQuotes getStockCurrentQuoteByStockId(long stockId) {
		return stockDAO.findStockCurrentQuoteByStockId(stockId);
	}

	public void saveStockHistoryList(
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
				StockMaster stockMaster;
				try {
					stockMaster = stockDAO
							.findByTickerSymbol(quoteHistoryJSONModel.Symbol);
				} catch (DAOException e) {
					continue;
				}
				stockHistory.setStockMaster(stockMaster);
				stockMasterByTickerMap.put(quoteHistoryJSONModel.Symbol,
						stockMaster);
			}
			try {
				stockDAO.findStockHistoryByStockIdStockDate(stockHistory
						.getStockMaster().getStockId(), stockHistory
						.getStockDate());
			} catch (DBRecordNotFoundException e) {
				saveStockHistory(stockHistory);
			}
		}
	}

	/**
	 * @param stockHistory
	 */
	@Transactional(rollbackFor = DAOException.class)
	private void saveStockHistory(StockHistory stockHistory) {
		try {
			stockDAO.persist(stockHistory);
		} catch (DAOException daoException) {
			LOG.error(daoException);
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
			try {
				stockDAO.updateCurrentStock(stockCurrent);
			} catch (DAOException e) {
				LOG.error(e);
			}
		}
	}

	public void updateStocks(List<StockMaster> updateStocksList) {
		for (StockMaster stockMaster : updateStocksList) {
			try {
				stockDAO.update(stockMaster);
			} catch (DAOException e) {
				// TODO: SHOULD I CONTINUE WITH THIS OR NOT. CHECK LATER
				LOG.error(e);
			}
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
							mapper.getTypeFactory().constructCollectionType(
									List.class, QuoteCurrentJSONModel.class));
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
				if (!quoteArray.isArray()) {
					String quoteString = quoteArray.toString();
					ObjectMapper mapperTemp = new ObjectMapper();
					JsonNode actualObj = mapperTemp.readTree("[" + quoteString
							+ "]");
					quoteHistoryJSONModels = mapper.readValue(
							actualObj,
							mapper.getTypeFactory().constructCollectionType(
									List.class, QuoteHistoryJSONModel.class));
				} else {
					quoteHistoryJSONModels = mapper.readValue(
							quoteArray,
							mapper.getTypeFactory().constructCollectionType(
									List.class, QuoteHistoryJSONModel.class));
				}

				saveStockHistoryList(quoteHistoryJSONModels);
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
