package com.misys.stockmarket.services;

import java.math.BigDecimal;
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
import com.misys.stockmarket.mbeans.StockHoldingFormBean;

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
				for (OrderExecution orderExecution : orderMaster.getOrderExecutions()) { 
					
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
					
					if(orderMaster.getType().equals(IApplicationConstants.BUY_TYPE))
					{
						stockHoldingFormBean.addVolume(orderExecution
								.getUnitsTraded());
					}
					else if(orderMaster.getType().equals(IApplicationConstants.SELL_TYPE))
					{
						stockHoldingFormBean.subtractVolume(orderExecution
								.getUnitsTraded());
					}
					/*stockHoldingFormBean.addPricePaid(orderExecution
							.getExecutionPrice());*/
					StockCurrentQuotes stockCurrentQuotes = stockService
							.getStockCurrentQuoteByStockId(stockMaster
									.getStockId());
					stockHoldingFormBean.setMarketPrice(stockCurrentQuotes
							.getLastTradePriceOnly().toPlainString());
					
					BigDecimal marketValue = stockCurrentQuotes.getLastTradePriceOnly().multiply(new BigDecimal(stockHoldingFormBean.getVolume()));
					
					stockHoldingFormBean
							.setMarketCalculatedValue(marketValue.toPlainString());
				}
			}
			for (StockHoldingFormBean stockHoldingFormBean : stockHoldingBySymbolMap.values())
			{
				portfolioValue = portfolioValue.add(new BigDecimal(stockHoldingFormBean.getMarketCalculatedValue()));
			}
			MyPortfolioFormBean myPortfolioFormBean = new MyPortfolioFormBean();
			myPortfolioFormBean.setPortfolioValue(portfolioValue
					.toPlainString());
			// CALCULATE REMAINING CASH BALANCE
			BigDecimal remainingAmount = leagueUser.getRemainingAmount();
			myPortfolioFormBean.setRemainingBalance(remainingAmount
					.toPlainString());
			myPortfolioFormBean.setTotalValue(remainingAmount.add(
					new BigDecimal(myPortfolioFormBean.getPortfolioValue()))
					.toPlainString());
			myPortfolioFormBean.getStockHoldings().addAll(
					stockHoldingBySymbolMap.values());
			return myPortfolioFormBean;
		} catch (OrderServiceException e) {
			LOG.error(e);
			throw new PortfolioServiceException(e);
		} catch (LeagueException e) {
			LOG.error(e);
			throw new PortfolioServiceException(e);
		}
	}
}
