package com.misys.stockmarket.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.dao.OrderExecutionDAO;
import com.misys.stockmarket.dao.OrderMasterDAO;
import com.misys.stockmarket.domain.entity.OrderExecution;
import com.misys.stockmarket.domain.entity.OrderMaster;
import com.misys.stockmarket.domain.entity.StockMaster;

@Service("orderExecutionService")
@Repository
public class OrderExecutionService {

	private static final Log LOG = LogFactory
			.getLog(OrderExecutionService.class);

	@Inject
	private OrderMasterDAO orderDAO;

	@Inject
	private OrderExecutionDAO orderExecutionDAO;

	public void executeOrders() {
		List<OrderMaster> pendingOrders = orderDAO.findAllPendingOrders();
		for (Iterator<OrderMaster> iterator = pendingOrders.iterator(); iterator
				.hasNext();) {
			OrderMaster orderMaster = (OrderMaster) iterator.next();
			if (isEligible(orderMaster)) {

				LOG.info("Executing the order " + orderMaster.getOrderId());

				// TODO: Check user has adequate amount to complete the order

				// Create order execution entry
				OrderExecution orderExecution = new OrderExecution();
				orderExecution.setOrderMaster(orderMaster);
				BigDecimal volume = orderMaster.getVolume();
				orderExecution.setUnitsTraded(volume);
				orderExecution.setExecutionPrice(getCurrentPrice(
						orderMaster.getStockMaster()).multiply(volume));
				orderExecution.setExecutionDate(new Date());
				orderDAO.persist(orderExecution);

				LOG.info("Completed executing the order "
						+ orderMaster.getOrderId());
				// Update order master entry
				orderMaster
						.setStatus(IApplicationConstants.ORDER_STATUS_COMPLETED);
				orderExecutionDAO.update(orderMaster);
				
				// TODO: Update LEAGUE_USER table to update the remaining value
			}
		}
	}

	private BigDecimal getCurrentPrice(StockMaster stockMaster) {
		// TODO: Write correct logic
		return new BigDecimal(200);
	}

	private boolean isEligible(OrderMaster orderMaster) {
		// TODO: Write complete logic
		if (IApplicationConstants.ORDER_PRICE_TYPE_MARKET.equals(orderMaster
				.getPriceType())) {
			// TODO: get date of last loaded stock
			Date currentDate = new Date();
			if (currentDate.after(orderMaster.getOrderDate())) {
				return true;
			}
			return false;
		} else {
			return false;
		}
	}
}
