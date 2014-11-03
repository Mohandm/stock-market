package com.misys.stockmarket.services;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.misys.stockmarket.mbeans.StockCurrentQuotesBean;
import com.misys.stockmarket.mbeans.StockHistoryBean;
import com.misys.stockmarket.mbeans.StockMasterBean;
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

	public List<StockMasterBean> listAllActiveStockBeans() throws StockServiceException {
		try {
			List<StockMaster> stockMasterList = stockDAO.findAllActiveStocks();
			List<StockMasterBean> stockMasterBeanList = new ArrayList<StockMasterBean>();
			for (StockMaster stockMaster : stockMasterList) 
			{
				StockMasterBean bean = new StockMasterBean();
				bean.setTikerSymbol(stockMaster.getTikerSymbol());
				bean.setName(stockMaster.getName());
				bean.setStockId(Long.toString(stockMaster.getStockId()));
				bean.setActive(stockMaster.getActive());
				stockMasterBeanList.add(bean);
			}
			
			return stockMasterBeanList;
		} catch (DAOException e) {
			throw new StockServiceException(e);
		}
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

	public List<StockCurrentQuotesBean> listAllCurrentStockQuotesBean()
			throws ServiceException {
		try {
			List<StockCurrentQuotesBean> beanList = new ArrayList<StockCurrentQuotesBean>();
			List<StockCurrentQuotes> stockQuotesList = stockDAO.findAll(StockCurrentQuotes.class);
			for(StockCurrentQuotes quotes : stockQuotesList)
			{
				StockCurrentQuotesBean bean = new StockCurrentQuotesBean();
				bean.setStockId(Long.toString(quotes.getStockMaster().getStockId()));
				bean.setChange(quotes.getChange());
				bean.setChangeinPercent(quotes.getChangeinPercent());
				bean.setCurrency(quotes.getCurrency());
				bean.setDaysRange(quotes.getDaysRange());
				bean.setLastTradeDate(quotes.getLastTradeDate());
				bean.setLastTradePriceOnly(quotes.getLastTradePriceOnly());
				bean.setLastTradeTime(quotes.getLastTradeTime());
				bean.setOpen(quotes.getOpen());
				bean.setPreviousClose(quotes.getPreviousClose());
				bean.setUpdatedTimeStamp(quotes.getUpdatedTimeStamp());
				bean.setVolume(quotes.getVolume());
				bean.setYearRange(quotes.getYearRange());
				beanList.add(bean);
			}
			return beanList;
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

	public void saveStockList(List<StockMaster> newStocksList) {
		for (StockMaster stockMaster : newStocksList) {
			saveStock(stockMaster);
		}
	}

	/**
	 * @param stockMaster
	 */
	@Transactional(rollbackFor = DAOException.class)
	private void saveStock(StockMaster stockMaster) {
		try {
			stockDAO.persist(stockMaster);
		} catch (DAOException e) {
			LOG.error(e);
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

	public List<StockHistoryBean> listStockHistory(String tickerSymbol)
			throws StockServiceException {
		try {
			StockMaster stockMaster = stockDAO.findByTickerSymbol(tickerSymbol);
			List<StockHistory> historyList = stockDAO.findStockHistoryByStockId(stockMaster.getStockId());
			List<StockHistoryBean> beanList = new ArrayList<StockHistoryBean>();
			for(StockHistory history : historyList)
			{
				StockHistoryBean bean = new StockHistoryBean();
				bean.setStockId(Long.toString(history.getStockMaster().getStockId()));
				bean.setClose(history.getClose());
				bean.setHigh(history.getHigh());
				bean.setLow(history.getLow());
				bean.setOpen(history.getOpen());
				bean.setStockDate(history.getStockDate());
				bean.setVolume(history.getVolume());
				beanList.add(bean);
			}
			
			return beanList;
		} catch (DAOException e) {
			throw new StockServiceException(e);
		}
	}

	public StockCurrentQuotesBean getStockCurrentQuoteByStockSymbol(
			String tickerSymbol) throws StockServiceException {
		try {
			StockCurrentQuotesBean bean = new StockCurrentQuotesBean();
			StockCurrentQuotes quotes = stockDAO.findStockCurrentQuoteByTickerSymbol(tickerSymbol);
			bean.setStockId(Long.toString(quotes.getStockMaster().getStockId()));
			bean.setChange(quotes.getChange());
			bean.setChangeinPercent(quotes.getChangeinPercent());
			bean.setCurrency(quotes.getCurrency());
			bean.setDaysRange(quotes.getDaysRange());
			bean.setLastTradeDate(quotes.getLastTradeDate());
			bean.setLastTradePriceOnly(quotes.getLastTradePriceOnly());
			bean.setLastTradeTime(quotes.getLastTradeTime());
			bean.setOpen(quotes.getOpen());
			bean.setPreviousClose(quotes.getPreviousClose());
			bean.setUpdatedTimeStamp(quotes.getUpdatedTimeStamp());
			bean.setVolume(quotes.getVolume());
			bean.setYearRange(quotes.getYearRange());
			return bean;
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
			if(quoteHistoryJSONModel.Close != null)
			{
				stockHistory.setClose(new BigDecimal(quoteHistoryJSONModel.Close));
			}
			if(quoteHistoryJSONModel.High != null)
			{
				stockHistory.setHigh(new BigDecimal(quoteHistoryJSONModel.High));
			}
			if(quoteHistoryJSONModel.Low != null)
			{
				stockHistory.setLow(new BigDecimal(quoteHistoryJSONModel.Low));
			}
			if(quoteHistoryJSONModel.Open != null)
			{
				stockHistory.setOpen(new BigDecimal(quoteHistoryJSONModel.Open));
			}
			if(quoteHistoryJSONModel.Date != null)
			{
				stockHistory.setStockDate(DateUtils.stringDateToDate(
						quoteHistoryJSONModel.Date, YQLUtil.YQL_DATE_FORMAT));
			}
			
			if(quoteHistoryJSONModel.Volume != null)
			{
				stockHistory.setVolume(new BigDecimal(quoteHistoryJSONModel.Volume));
			}
			
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
			if(quoteCurrentJSONModel.LastTradePriceOnly != null)
			{
				stockCurrent.setLastTradePriceOnly(new BigDecimal(
						quoteCurrentJSONModel.LastTradePriceOnly));
			}
			if(quoteCurrentJSONModel.Open != null)
			{
				stockCurrent.setOpen(new BigDecimal(quoteCurrentJSONModel.Open));
			}
			stockCurrent.setLastTradeTime(quoteCurrentJSONModel.LastTradeTime);
			if(quoteCurrentJSONModel.PreviousClose != null)
			{
				stockCurrent.setPreviousClose(new BigDecimal(
						quoteCurrentJSONModel.PreviousClose));
			}
			if(quoteCurrentJSONModel.Volume != null)
			{
				stockCurrent.setVolume(new BigDecimal(quoteCurrentJSONModel.Volume));
			}
			
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

	public void updateStockList(List<StockMaster> updateStocksList) {
		for (StockMaster stockMaster : updateStocksList) {
			updateStock(stockMaster);
		}
	}

	/**
	 * @param stockMaster
	 */
	@Transactional(rollbackFor = DAOException.class)
	private void updateStock(StockMaster stockMaster) {
		try {
			stockDAO.update(stockMaster);
		} catch (DAOException e) {
			LOG.error(e);
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
				if (quoteArray != null && !quoteArray.isArray()) {
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
