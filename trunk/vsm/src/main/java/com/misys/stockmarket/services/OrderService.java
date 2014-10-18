package com.misys.stockmarket.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.dao.OrderExecutionDAO;
import com.misys.stockmarket.dao.OrderMasterDAO;
import com.misys.stockmarket.dao.StockDAO;
import com.misys.stockmarket.domain.entity.OrderExecution;
import com.misys.stockmarket.domain.entity.OrderMaster;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.mbeans.OrderFormBean;
import com.misys.stockmarket.platform.web.ResponseMessage;

@Service("orderService")
@Repository
public class OrderService {

	@Inject
	private OrderMasterDAO orderMasterDAO;

	@Inject
	private OrderExecutionDAO orderExecutionDAO;

	@Inject
	private StockDAO stockDAO;

	@Inject
	private UserService userService;

	@Inject
	private OrderExecutionService orderExecutionService;

	public ResponseMessage saveNewOrder(OrderFormBean orderFormBean) {
		// Validate bean

		// Process order
		try {
			OrderMaster orderMaster = new OrderMaster();
			orderMaster.setIntraday(orderFormBean.getIntraday());
			orderMaster.setOrderPrice(orderFormBean.getOrderPrice());
			orderMaster.setPriceType(orderFormBean.getPriceType());
			orderMaster.setType(orderFormBean.getType());
			orderMaster.setVolume(orderFormBean.getVolume());
			orderMaster.setStockMaster(stockDAO.findBySymbol(orderFormBean
					.getSymbol()));
			orderMaster.setUserMaster(userService.getLoggedInUser());
			// Set some default values
			orderMaster.setOrderDate(new Date());
			orderMaster.setStatus(IApplicationConstants.ORDER_STATUS_PENDING);
			orderMasterDAO.persist(orderMaster);

			return new ResponseMessage(ResponseMessage.Type.success,
					"Your order has been placed.");
		} catch (Exception e) {

			e.printStackTrace();
			return new ResponseMessage(ResponseMessage.Type.danger,
					"There was a technical error while processing your order. Please try again");
		}
	}

	public List<OrderExecution> getAllCompletedOrdersForLoggedInUser()
			throws Exception {

		List<OrderExecution> completedOrders = new ArrayList<OrderExecution>();
		UserMaster userMaster = userService.getLoggedInUser();
		List<OrderMaster> completedOrderMasters = orderMasterDAO
				.findAllCompletedOrders(userMaster);
		for (Iterator<OrderMaster> iterator = completedOrderMasters.iterator(); iterator
				.hasNext();) {
			OrderMaster orderMaster = (OrderMaster) iterator.next();
			completedOrders.add(orderExecutionDAO
					.findByOrderMaster(orderMaster));
		}
		return completedOrders;
	}
}
