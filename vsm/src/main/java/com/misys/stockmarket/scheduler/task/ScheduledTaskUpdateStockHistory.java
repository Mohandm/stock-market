package com.misys.stockmarket.scheduler.task;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.misys.stockmarket.domain.entity.StockMaster;
import com.misys.stockmarket.exception.FinancialServiceException;
import com.misys.stockmarket.exception.service.StockServiceException;
import com.misys.stockmarket.services.StockService;

/**
 * @author sam sundar K
 * 
 */
@Component
@EnableScheduling
public class ScheduledTaskUpdateStockHistory {

	private static final Log LOG = LogFactory
			.getLog(ScheduledTaskUpdateStockHistory.class);

	@Inject
	private StockService stockService;

	@Scheduled(fixedRateString = "${scheduler.task.UpdateStockHistory.frequency}")
	public void updateStockHistory() {
		LOG.info("SCHEDULED TASK: UPDATE STOCK HISTORY STARTED");
		try {
			List<StockMaster> activeStockList = stockService
					.listAllActiveStocks();
			for (StockMaster stockMaster : activeStockList) {
				try {
					LOG.info("Started Updating stock History Task of ticker Symbol:"
							+ stockMaster.getTikerSymbol());
					stockService.updateStockHistory(stockMaster);
					LOG.info("Completed Updating stock History Task of ticker Symbol:"
							+ stockMaster.getTikerSymbol());
				} catch (FinancialServiceException e) {
					LOG.error(
							"Technical Error while updating stock history for stock: "
									+ stockMaster.getTikerSymbol(), e);
				}
			}
		} catch (StockServiceException e) {
			LOG.error(e);
		}
		LOG.info("SCHEDULED TASK: UPDATE STOCK HISTORY ENDED");
	}
}
