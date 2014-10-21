package com.misys.stockmarket;


import java.util.List;

import javax.inject.Inject;

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
import com.misys.stockmarket.handlers.user.UserBizHandler;
import com.misys.stockmarket.mbeans.OrderFormBean;
import com.misys.stockmarket.mbeans.UserFormBean;
import com.misys.stockmarket.mbeans.WatchListFormBean;
import com.misys.stockmarket.platform.web.ResponseMessage;
import com.misys.stockmarket.services.AlertsService;
import com.misys.stockmarket.services.OrderService;
import com.misys.stockmarket.services.StockService;
 
/**
 * @author Gurudath Reddy
 * @version 1.0
 */
@Controller
public class StockMarketController {
     
	@Inject
	UserBizHandler userBizHandler;
	
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
	
	@RequestMapping(value = "/activateprofile/{token:.+}", method = { RequestMethod.GET,
			RequestMethod.POST })
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
		return orderService.saveNewOrder(orderFormBean);		
	}

	@RequestMapping(value = "/sellstock", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseMessage sellStock(@RequestBody OrderFormBean orderFormBean) {
		orderFormBean.setType(IApplicationConstants.SELL_TYPE);
		return orderService.saveNewOrder(orderFormBean);
	}
	
	@RequestMapping(value = "/watchStock", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseMessage watchStock(@RequestBody WatchListFormBean watchListFormBean) {
		return alertsService.saveNewWatchStock(watchListFormBean);
	}
	
	@RequestMapping(value = "/alertList", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public List<UserAlerts> alertList() {
		return alertsService.listAllAlerts(); 
	}
	
	@RequestMapping(value = "/stockList", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public List<StockMaster> stockList() {
		return stockService.listAllActiveStocks(); 
	}
	
	@RequestMapping(value = "/stockListCurrentQuotes", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public StockCurrentQuotes stockListCurrentQuotes(@RequestParam("stockSymbol") String symbol) {
		return stockService.getStockCurrentQuoteByStockSymbol(symbol); 
	}
	
	@RequestMapping(value = "/stockListAllCurrentQuotes", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public List<StockCurrentQuotes> stockListAllCurrentQuotes() {
		return stockService.listAllCurrentStockQuotes(); 
	}
	
	@RequestMapping(value = "/stockListHistory", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public List<StockHistory> stockListHistory(@RequestParam("stockSymbol") String symbol) {
		return stockService.listStockHistory(symbol); 
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

