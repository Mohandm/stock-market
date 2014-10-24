package com.misys.stockmarket.services;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.domain.entity.OrderMaster;
import com.misys.stockmarket.exception.service.OrderServiceException;
import com.misys.stockmarket.exception.service.PortfolioServiceException;
import com.misys.stockmarket.mbeans.StockHoldingFormBean;

@Service("portfolioService")
@Repository
public class PortfolioService {

	private static final Log LOG = LogFactory.getLog(PortfolioService.class);

	@Inject
	private OrderService orderService;

	public StockHoldingFormBean getMyPortfolio(long leagueUserId)
			throws PortfolioServiceException {

		try {
			List<OrderMaster> completedPurchaseOrderList = orderService
					.getAllCompletedPurchaseOrders(leagueUserId);
			for (OrderMaster orderMaster : completedPurchaseOrderList) {
				System.out.println(orderMaster);
			}
		} catch (OrderServiceException e) {
			LOG.error(e);
			throw new PortfolioServiceException(e);
		}
		return null;
	}
}
