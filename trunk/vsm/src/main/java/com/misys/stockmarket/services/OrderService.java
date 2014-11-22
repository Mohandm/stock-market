package com.misys.stockmarket.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.misys.stockmarket.achievements.AchievementFacade;
import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.dao.OrderExecutionDAO;
import com.misys.stockmarket.dao.OrderMasterDAO;
import com.misys.stockmarket.dao.StockDAO;
import com.misys.stockmarket.domain.entity.LeagueUser;
import com.misys.stockmarket.domain.entity.OrderExecution;
import com.misys.stockmarket.domain.entity.OrderMaster;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.exception.DAOException;
import com.misys.stockmarket.exception.DBRecordNotFoundException;
import com.misys.stockmarket.exception.EmailNotFoundException;
import com.misys.stockmarket.exception.LeagueException;
import com.misys.stockmarket.exception.service.OrderServiceException;
import com.misys.stockmarket.mbeans.OrderFormBean;

@Service("orderService")
@Repository
public class OrderService {

	private static final Log LOG = LogFactory.getLog(OrderService.class);

	@Inject
	private OrderMasterDAO orderMasterDAO;

	@Inject
	private OrderExecutionDAO orderExecutionDAO;

	@Inject
	private StockDAO stockDAO;

	@Inject
	private UserService userService;

	@Inject
	private LeagueService leagueService;


	@Inject
	private AchievementFacade achievementFacade;

	@PreAuthorize("hasRole('ROLE_USER')")
	@Transactional(rollbackFor = DAOException.class)
	public void saveNewOrder(OrderFormBean orderFormBean)
			throws OrderServiceException {
		// Process order
		try {
			OrderMaster orderMaster = new OrderMaster();
			/* orderMaster.setIntraday(orderFormBean.getIntraday()); */
			orderMaster.setOrderPrice(orderFormBean.getOrderPrice());
			orderMaster.setPriceType(orderFormBean.getPriceType());
			orderMaster.setType(orderFormBean.getType());
			orderMaster.setVolume(orderFormBean.getVolume());
			orderMaster.setStockMaster(stockDAO.findBySymbol(orderFormBean
					.getSymbol()));
			// Set some default values
			orderMaster.setOrderDate(new Date());
			orderMaster.setStatus(IApplicationConstants.ORDER_STATUS_PENDING);
			// TODO: Done, Handled to support multiple leagues
			LeagueUser leagueUser = leagueService.getLeagueUser(orderFormBean
					.getLeagueUserId(), userService.getLoggedInUser()
					.getUserId());
			orderMaster.setLeagueUser(leagueUser);
			orderMasterDAO.persist(orderMaster);
			
			// Evalaute achievements
			List<String> categories = new ArrayList<String>();
			categories.add("buyOrder");
			categories.add("sellOrder");
			achievementFacade.evaluate(leagueUser.getUserMaster(), categories);
		
		} catch (DBRecordNotFoundException e) {
			throw new OrderServiceException(e);
		} catch (LeagueException e) {
			throw new OrderServiceException(e);
		} catch (EmailNotFoundException e) {
			throw new OrderServiceException(e);
		} catch (DAOException e) {
			throw new OrderServiceException(e);
		}
	}

	public List<OrderExecution> getAllCompletedOrdersForLoggedInUser()
			throws OrderServiceException {
		List<OrderExecution> completedOrders = new ArrayList<OrderExecution>();
		try {
			UserMaster userMaster = userService.getLoggedInUser();
			List<OrderMaster> completedOrderMasters = orderMasterDAO
					.findAllCompletedOrders(userMaster);
			for (OrderMaster orderMaster : completedOrderMasters) {
				completedOrders.add(orderExecutionDAO
						.findByOrderMaster(orderMaster));
			}
		} catch (EmailNotFoundException e) {
			LOG.error(e);
			throw new OrderServiceException(e);
		} catch (DAOException e) {
			LOG.error(e);
			throw new OrderServiceException(e);
		}
		return completedOrders;
	}
	
	public List<OrderMaster> findAllCompletedBuyOrders(UserMaster user)
			throws OrderServiceException {
		List<OrderMaster> orderList = new ArrayList<OrderMaster>();
		try {
			orderList = orderMasterDAO.findAllCompletedBuyOrders(user);
		} catch (DAOException e) {
			LOG.error(e);
			throw new OrderServiceException(e);
		}
		return orderList;
	}

	public List<OrderMaster> findAllCompletedSellOrders(UserMaster user)
			throws OrderServiceException {
		List<OrderMaster> orderList = new ArrayList<OrderMaster>();
		try {
			orderList = orderMasterDAO.findAllCompletedSellOrders(user);
		} catch (DAOException e) {
			LOG.error(e);
			throw new OrderServiceException(e);
		}
		return orderList;
	}

	public List<OrderMaster> getAllCompletedPurchaseOrders(long leagueUserId)
			throws OrderServiceException {
		try {
			return orderMasterDAO
					.findAllCompletedOrdersByLeaugeUser(leagueUserId);
		} catch (DAOException e) {
			LOG.error(e);
			throw new OrderServiceException(e);
		}
	}

	public List<OrderMaster> getAllOrders(long leagueUserId)
			throws OrderServiceException {
		try {
			return orderMasterDAO.findAllOrdersByLeaugeUser(leagueUserId);
		} catch (DAOException e) {
			LOG.error(e);
			throw new OrderServiceException(e);
		}
	}
}
