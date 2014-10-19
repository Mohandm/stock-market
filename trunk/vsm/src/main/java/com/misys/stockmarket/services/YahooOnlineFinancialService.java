package com.misys.stockmarket.services;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.exception.FinancialServiceException;
import com.misys.stockmarket.exception.YQLException;
import com.misys.stockmarket.utility.YQLUtil;

/**
 * @author sam sundar K
 * 
 */
@Service("yahooFinancialService")
public class YahooOnlineFinancialService implements IFinancialService {

	private static final Log LOG = LogFactory
			.getLog(YahooOnlineFinancialService.class);

	@Override
	public String getStockHistory(List<String> stockList, Date startDate,
			Date endDate) throws FinancialServiceException {

		String queryYQL = "select%20*%20from%20yahoo.finance.historicaldata%20where%20symbol%20in%20(<STOCK_LIST>)%20and%20startDate%20=%20%22<START_DATE>%22%20and%20endDate%20=%20%22<END_DATE>%22";

		String hostURL = "http://query.yahooapis.com/v1/public/yql?env=store://datatables.org/alltableswithkeys&format=json&diagnostics=false&q=";

		try {
			String commaSeperatedStockList = YQLUtil
					.getCommaSeperatedQuoteSymbols(stockList);

			queryYQL = queryYQL.replace("<STOCK_LIST>",
					YQLUtil.URLEncode(commaSeperatedStockList));

			queryYQL = queryYQL.replace("<START_DATE>",
					YQLUtil.URLEncode(YQLUtil.dateToYQLStringDate(startDate)));

			queryYQL = queryYQL.replace("<END_DATE>",
					YQLUtil.URLEncode(YQLUtil.dateToYQLStringDate(endDate)));

			StringBuffer requestBufferYQL = new StringBuffer();

			requestBufferYQL.append(hostURL).append(queryYQL);

			LOG.debug(requestBufferYQL.toString());

			return YQLUtil.executeQuery(requestBufferYQL.toString());
		} catch (YQLException e) {
			throw new FinancialServiceException(e);
		}
	}

	@Override
	public String getStockHistory(String tickerSymbol, Date startDate,
			Date endDate) throws FinancialServiceException {

		String queryYQL = "select%20*%20from%20yahoo.finance.historicaldata%20where%20symbol%20in%20(<STOCK_LIST>)%20and%20startDate%20=%20%22<START_DATE>%22%20and%20endDate%20=%20%22<END_DATE>%22";

		String hostURL = "http://query.yahooapis.com/v1/public/yql?env=store://datatables.org/alltableswithkeys&format=json&diagnostics=false&q=";
		try {

			queryYQL = queryYQL.replace("<STOCK_LIST>",
					YQLUtil.URLEncode("\"" + tickerSymbol + "\""));

			queryYQL = queryYQL.replace("<START_DATE>",
					YQLUtil.URLEncode(YQLUtil.dateToYQLStringDate(startDate)));

			queryYQL = queryYQL.replace("<END_DATE>",
					YQLUtil.URLEncode(YQLUtil.dateToYQLStringDate(endDate)));

			StringBuffer requestBufferYQL = new StringBuffer();

			requestBufferYQL.append(hostURL).append(queryYQL);

			LOG.debug(requestBufferYQL.toString());

			return YQLUtil.executeQuery(requestBufferYQL.toString());
		} catch (YQLException e) {
			throw new FinancialServiceException(e);
		}
	}
	
	@Override
	public String getStockCurrent(List<String> stockList) throws FinancialServiceException
	{
		String queryYQL = "select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(<STOCK_LIST>)%0A%09%09";

		String hostURL = "http://query.yahooapis.com/v1/public/yql?env=http%3A%2F%2Fdatatables.org%2Falltables.env&format=json&q=";
		
		try {

			String commaSeperatedStockList = YQLUtil
					.getCommaSeperatedQuoteSymbols(stockList);

			queryYQL = queryYQL.replace("<STOCK_LIST>",
					YQLUtil.URLEncode(commaSeperatedStockList));
			
			StringBuffer requestBufferYQL = new StringBuffer();

			requestBufferYQL.append(hostURL).append(queryYQL);

			LOG.debug(requestBufferYQL.toString());

			return YQLUtil.executeQuery(requestBufferYQL.toString());
		} catch (YQLException e) {
			throw new FinancialServiceException(e);
		}
	}
}