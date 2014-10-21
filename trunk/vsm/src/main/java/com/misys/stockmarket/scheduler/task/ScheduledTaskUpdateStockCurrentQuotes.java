package com.misys.stockmarket.scheduler.task;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.misys.stockmarket.exception.FinancialServiceException;
import com.misys.stockmarket.services.AlertsService;
import com.misys.stockmarket.services.StockService;

@Component
@EnableScheduling
public class ScheduledTaskUpdateStockCurrentQuotes {
	private static final Log LOG = LogFactory
			.getLog(ScheduledTaskUpdateStockCurrentQuotes.class);

	@Inject
	private StockService stockService;
	
	@Inject
	private AlertsService alertsService;

	/**
	 * Currently hard coded to 10 mins interval TODO: Need to externalize this
	 * configuratoin
	 */
	@Scheduled(fixedRate = 600000)
	public void updateStockCurrentQuotes() {
		LOG.info("SCHEDULED TASK: UPDATE STOCK CURRENT QUOTES STARTED");
		List<String> stockList = stockService.listAllStockSymbols();
		try {
			stockService.updateStockCurrentQuotes(stockList);
			alertsService.triggerWatchStockAlerts();
		} catch (FinancialServiceException e) {
			LOG.error(
					"Technical Error while updating stock current quotes for stock: ", e);
		}
		LOG.info("SCHEDULED TASK: UPDATE STOCK CURRENT QUOTES ENDED");
	}
}
