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

	/**
	 * Currently hard coded to 10 mins interval TODO: Need to externalize this
	 * configuratoin
	 */
	@Scheduled(fixedRate = 600000)
	public void updateStockHistory() {
		LOG.info("SCHEDULED TASK: UPDATE STOCK HISTORY STARTED");
		List<StockMaster> activeStockList = stockService.listAllActiveStocks();
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
		LOG.info("SCHEDULED TASK: UPDATE STOCK HISTORY ENDED");
	}
}
