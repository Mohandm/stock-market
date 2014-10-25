package com.misys.stockmarket.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.domain.entity.StockCurrentQuotes;
import com.misys.stockmarket.domain.entity.StockHistory;
import com.misys.stockmarket.domain.entity.StockMaster;
import com.misys.stockmarket.domain.entity.UserAlerts;
import com.misys.stockmarket.domain.entity.WatchStock;
import com.misys.stockmarket.enums.STOCK_ERR_CODES;
import com.misys.stockmarket.exception.ServiceException;
import com.misys.stockmarket.exception.service.AlertsServiceException;
import com.misys.stockmarket.exception.service.OrderServiceException;
import com.misys.stockmarket.exception.service.StockServiceException;
import com.misys.stockmarket.handlers.user.UserBizHandler;
import com.misys.stockmarket.mbeans.OrderFormBean;
import com.misys.stockmarket.mbeans.UserAlertsBean;
import com.misys.stockmarket.mbeans.UserFormBean;
import com.misys.stockmarket.mbeans.WatchListFormBean;
import com.misys.stockmarket.platform.web.ResponseMessage;
import com.misys.stockmarket.security.LoginResponse;
import com.misys.stockmarket.services.AlertsService;
import com.misys.stockmarket.services.OrderService;
import com.misys.stockmarket.services.StockService;
import com.misys.stockmarket.services.UserService;

/**
 * @author Gurudath Reddy
 * @version 1.0
 */
@Controller
public class StockMarketController {

	private static final Log LOG = LogFactory
			.getLog(StockMarketController.class);

	@Inject
	UserBizHandler userBizHandler;

	@Inject
	private UserService userService;

	@Inject
	StockService stockService;

	@Inject
	OrderService orderService;

	@Inject
	AlertsService alertsService;

	@RequestMapping(value = "/registeruser", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage registerUser(@RequestBody UserFormBean userFormBean) {
		return userBizHandler.registerUser(userFormBean);
	}

	@RequestMapping(value = "/activateprofile/{token:.+}", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseMessage activateProfile(@PathVariable("token") String token) {
		return userBizHandler.activateProfile(token);
	}

	@RequestMapping(value = "/resetpassword", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseMessage resetPassword(@RequestBody UserFormBean userFormBean) {
		return userBizHandler.resetPassword(userFormBean);
	}

	@RequestMapping(value = "/changepassword", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseMessage changePassword(@RequestBody UserFormBean userFormBean) {
		return userBizHandler.changePassword(userFormBean);
	}

	@RequestMapping(value = "/buystock", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseMessage buyStock(@RequestBody OrderFormBean orderFormBean) {
		orderFormBean.setType(IApplicationConstants.BUY_TYPE);
		try {
			orderService.saveNewOrder(orderFormBean);
			return new ResponseMessage(ResponseMessage.Type.success,
					"Your order has been placed.");
		} catch (OrderServiceException e) {
			return new ResponseMessage(ResponseMessage.Type.danger,
					"There was a technical error while processing your order. Please try again");
		}
	}

	@RequestMapping(value = "/sellstock", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseMessage sellStock(@RequestBody OrderFormBean orderFormBean) {
		orderFormBean.setType(IApplicationConstants.SELL_TYPE);
		try {
			orderService.saveNewOrder(orderFormBean);
			return new ResponseMessage(ResponseMessage.Type.success,
					"Your order has been placed.");
		} catch (OrderServiceException e) {
			return new ResponseMessage(ResponseMessage.Type.danger,
					"There was a technical error while processing your order. Please try again");
		}
	}

	@RequestMapping(value = "/watchStock", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseMessage watchStock(
			@RequestBody WatchListFormBean watchListFormBean) {
		return alertsService.saveNewWatchStock(watchListFormBean);
	}

	@RequestMapping(value = "/alertList", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public List<UserAlertsBean> alertList() {
		try {
			List<UserAlerts> alerts = alertsService.listAllAlerts();
			List<UserAlertsBean> userAlertsBeans = new ArrayList<UserAlertsBean>();
			for(Iterator<UserAlerts> iterator = alerts
					.iterator(); iterator.hasNext();)
			{
				UserAlerts userAlerts = (UserAlerts) iterator.next();
				UserAlertsBean userAlertsBean = new UserAlertsBean();
				userAlertsBean.setMessage(userAlerts.getMessage());
				userAlertsBean.setNotifiedDate(userAlerts.getNotifiedDate().toString());
				userAlertsBeans.add(userAlertsBean);
			}
			Collections.reverse(userAlertsBeans);
			return userAlertsBeans;
		} catch (AlertsServiceException e) {
			LOG.error(e);
			// TODO: HANDLE WHEN EXCEPTION
			return null;
		}
	}

	@RequestMapping(value = "/stockList", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public List<StockMaster> stockList() {
		try {
			return stockService.listAllActiveStocks();
		} catch (StockServiceException e) {
			LOG.error(e);
			// TODO: HANDLE WHEN EXCEPTION
			return null;
		}
	}

	@RequestMapping(value = "/stockListCurrentQuotes", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public StockCurrentQuotes stockListCurrentQuotes(
			@RequestParam("stockSymbol") String symbol) {
		try {
			return stockService.getStockCurrentQuoteByStockSymbol(symbol);
		} catch (StockServiceException e) {
			if (STOCK_ERR_CODES.CURRENT_DAY_STOCK_NOT_FOUND.compareTo(e
					.getErrorCode()) == 0) {
				LOG.error("Current Day Stock Not Found for Ticker Symbol: "
						+ symbol, e);
			} else {
				LOG.error(e);
			}
			// TODO: HANDLE WHEN EXCEPTION
			return null;
		}
	}

	@RequestMapping(value = "/stockListAllCurrentQuotes", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<StockCurrentQuotes> stockListAllCurrentQuotes() {
		try {
			return stockService.listAllCurrentStockQuotes();
		} catch (ServiceException e) {
			LOG.error(e);
			// TODO: HANDLE WHEN EXCEPTION
			return null;
		}
	}

	@RequestMapping(value = "/stockListHistory", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public List<StockHistory> stockListHistory(
			@RequestParam("stockSymbol") String symbol) {
		try {
			return stockService.listStockHistory(symbol);
		} catch (StockServiceException e) {
			LOG.error(e);
			// TODO: HANDLE WHEN EXCEPTION
			return null;
		}
	}

	@RequestMapping(value = "/getuserdetails", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public LoginResponse getUserDetails() {
			return userService.getLoggedInUserResponse();
		
	}
	
	@RequestMapping("/")
	public String index(Model model) {
		return "index";
	}

	@RequestMapping("/game")
	public String app(Model model) {
		return "game";
	}
}
