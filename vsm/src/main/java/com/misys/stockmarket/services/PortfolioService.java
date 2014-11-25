package com.misys.stockmarket.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.domain.entity.LeagueUser;
import com.misys.stockmarket.domain.entity.OrderExecution;
import com.misys.stockmarket.domain.entity.OrderMaster;
import com.misys.stockmarket.domain.entity.StockCurrentQuotes;
import com.misys.stockmarket.domain.entity.StockMaster;
import com.misys.stockmarket.exception.LeagueException;
import com.misys.stockmarket.exception.service.OrderServiceException;
import com.misys.stockmarket.exception.service.PortfolioServiceException;
import com.misys.stockmarket.mbeans.MyPortfolioFormBean;
import com.misys.stockmarket.mbeans.MyRecentTradeFormBean;
import com.misys.stockmarket.mbeans.OrderHistoryFormBean;
import com.misys.stockmarket.mbeans.StockHoldingFormBean;
import com.misys.stockmarket.utility.DateUtils;

@Service("portfolioService")
@Repository
public class PortfolioService {

	private static final Log LOG = LogFactory.getLog(PortfolioService.class);

	@Inject
	private OrderService orderService;

	@Inject
	private StockService stockService;

	@Inject
	private LeagueService leagueService;

	public MyPortfolioFormBean getMyPortfolio(long leagueId, long userId)
			throws PortfolioServiceException {
		try {
			LeagueUser leagueUser = leagueService.getLeagueUser(leagueId,
					userId);
			List<OrderMaster> completedPurchaseOrderList = orderService
					.getAllCompletedPurchaseOrders(leagueUser.getLeagueUserId());
			BigDecimal portfolioValue = new BigDecimal(0);
			Map<String, StockHoldingFormBean> stockHoldingBySymbolMap = new HashMap<String, StockHoldingFormBean>();
			for (OrderMaster orderMaster : completedPurchaseOrderList) {
				for (OrderExecution orderExecution : orderMaster
						.getOrderExecutions()) {

					// SUM UP ALL SAME STOCKS
					StockMaster stockMaster = orderMaster.getStockMaster();
					String tickerSymbol = stockMaster.getTikerSymbol();
					StockHoldingFormBean stockHoldingFormBean = null;
					if (stockHoldingBySymbolMap.containsKey(tickerSymbol)) {
						stockHoldingFormBean = stockHoldingBySymbolMap
								.get(tickerSymbol);
					} else {
						stockHoldingFormBean = new StockHoldingFormBean();
						stockHoldingBySymbolMap.put(tickerSymbol,
								stockHoldingFormBean);
					}
					stockHoldingFormBean.setTikerSymbol(tickerSymbol);
					stockHoldingFormBean.setName(stockMaster.getName());

					if (orderMaster.getType().equals(
							IApplicationConstants.BUY_TYPE)) {
						stockHoldingFormBean.addVolume(orderExecution
								.getUnitsTraded());
					} else if (orderMaster.getType().equals(
							IApplicationConstants.SELL_TYPE)) {
						stockHoldingFormBean.subtractVolume(orderExecution
								.getUnitsTraded());
					}
					StockCurrentQuotes stockCurrentQuotes = stockService
							.getStockCurrentQuoteByStockId(stockMaster
									.getStockId());
					stockHoldingFormBean.setMarketPrice(stockCurrentQuotes
							.getLastTradePriceOnly().toPlainString());

					BigDecimal marketValue = stockCurrentQuotes
							.getLastTradePriceOnly().multiply(
									new BigDecimal(stockHoldingFormBean
											.getVolume()));

					stockHoldingFormBean.setMarketCalculatedValue(marketValue
							.toPlainString());
				}
			}

			List<StockHoldingFormBean> stockHoldingFormBeanFinalList = new ArrayList<StockHoldingFormBean>();
			for (StockHoldingFormBean stockHoldingFormBean : stockHoldingBySymbolMap
					.values()) {
				portfolioValue = portfolioValue.add(new BigDecimal(
						stockHoldingFormBean.getMarketCalculatedValue()));
				BigDecimal volume = new BigDecimal(
						stockHoldingFormBean.getVolume());
				if (volume.compareTo(new BigDecimal(0)) > 0) {
					stockHoldingFormBean.setVolume(Long.toString(volume
							.longValue()));
					stockHoldingFormBeanFinalList.add(stockHoldingFormBean);
				}
			}
			MyPortfolioFormBean myPortfolioFormBean = new MyPortfolioFormBean();
			portfolioValue = portfolioValue.setScale(2, RoundingMode.CEILING);
			myPortfolioFormBean.setPortfolioValue(portfolioValue
					.toPlainString());
			// CALCULATE REMAINING CASH BALANCE
			BigDecimal remainingAmount = leagueUser.getRemainingAmount();
			remainingAmount = remainingAmount.setScale(2, RoundingMode.CEILING);
			myPortfolioFormBean.setRemainingBalance(remainingAmount
					.toPlainString());
			myPortfolioFormBean.setTotalValue(remainingAmount.add(
					new BigDecimal(myPortfolioFormBean.getPortfolioValue())).setScale(2, RoundingMode.CEILING).toPlainString());
			myPortfolioFormBean.getStockHoldings().addAll(
					stockHoldingFormBeanFinalList);
			return myPortfolioFormBean;
		} catch (OrderServiceException e) {
			LOG.error(e);
			throw new PortfolioServiceException(e);
		} catch (LeagueException e) {
			LOG.error(e);
			throw new PortfolioServiceException(e);
		}
	}

	public MyRecentTradeFormBean getMyRecentTrades(long leagueId, long userId)
			throws PortfolioServiceException {
		MyRecentTradeFormBean myRecentTradeFormBean = new MyRecentTradeFormBean();
		try {
			LeagueUser leagueUser = leagueService.getLeagueUser(leagueId,
					userId);
			List<OrderMaster> allOrdersList = orderService
					.getAllOrders(leagueUser.getLeagueUserId());
			for (OrderMaster orderMaster : allOrdersList) {
				OrderHistoryFormBean orderHistoryFormBean = new OrderHistoryFormBean();
				orderHistoryFormBean
						.setDateTime(DateUtils
								.getFormattedDateTimeString(orderMaster
										.getOrderDate()));
				orderHistoryFormBean.setTikerSymbol(orderMaster
						.getStockMaster().getTikerSymbol());
				orderHistoryFormBean.setName(orderMaster.getStockMaster()
						.getName());
				// TODO: Change later to II8n
				if (IApplicationConstants.BUY_TYPE
						.equals(orderMaster.getType())) {
					orderHistoryFormBean.setOrderType("Buy");
				} else {
					orderHistoryFormBean.setOrderType("Sell");
				}
				if (IApplicationConstants.ORDER_PRICE_TYPE_MARKET
						.equals(orderMaster.getPriceType())) {
					orderHistoryFormBean.setPriceType("Market");
				} else if (IApplicationConstants.ORDER_PRICE_TYPE_LIMIT
						.equals(orderMaster.getPriceType())) {
					orderHistoryFormBean.setPriceType("Limit");
				} else if (IApplicationConstants.ORDER_PRICE_TYPE_STOPLOSS
						.equals(orderMaster.getPriceType())) {
					orderHistoryFormBean.setPriceType("Stop Loss");
				}
				
				orderHistoryFormBean.setQuantity(Long.toString(orderMaster.getVolume()
						.longValue()));
				if (IApplicationConstants.ORDER_STATUS_COMPLETED
						.equals(orderMaster.getStatus())) {
					orderHistoryFormBean.setStatus("Completed");
				} else if (IApplicationConstants.ORDER_STATUS_PENDING
						.equals(orderMaster.getStatus())) {
					orderHistoryFormBean.setStatus("Pending");
				} else if (IApplicationConstants.ORDER_STATUS_INSUFFICIENT_FUNDS
						.equals(orderMaster.getStatus())) {
					orderHistoryFormBean.setStatus("Failed");
				}
				myRecentTradeFormBean.getRecentTrades().add(
						orderHistoryFormBean);
			}
		} catch (OrderServiceException e) {
			LOG.error(e);
			throw new PortfolioServiceException(e);
		} catch (LeagueException e) {
			LOG.error(e);
			throw new PortfolioServiceException(e);
		}
		return myRecentTradeFormBean;
	}
}
