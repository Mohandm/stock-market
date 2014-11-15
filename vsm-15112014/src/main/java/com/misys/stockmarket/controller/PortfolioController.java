package com.misys.stockmarket.controller;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.misys.stockmarket.domain.entity.LeagueMaster;
import com.misys.stockmarket.domain.entity.StockCurrentQuotes;
import com.misys.stockmarket.domain.entity.StockMaster;
import com.misys.stockmarket.enums.STOCK_ERR_CODES;
import com.misys.stockmarket.exception.EmailNotFoundException;
import com.misys.stockmarket.exception.ServiceException;
import com.misys.stockmarket.exception.service.PortfolioServiceException;
import com.misys.stockmarket.exception.service.StockServiceException;
import com.misys.stockmarket.mbeans.MyLeagueFormBean;
import com.misys.stockmarket.mbeans.MyPortfolioFormBean;
import com.misys.stockmarket.mbeans.MyRecentTradeFormBean;
import com.misys.stockmarket.services.LeagueService;
import com.misys.stockmarket.services.PortfolioService;
import com.misys.stockmarket.services.UserService;


/**
 * @author Gurudath Reddy
 * @version 1.0
 */
@Controller
public class PortfolioController {

	private static final Log LOG = LogFactory
			.getLog(PortfolioController.class);
	
	@Inject
	LeagueService leagueService;
	
	@Inject
	PortfolioService portfolioService;
	
	@Inject
	UserService userService;
	
	@RequestMapping(value = "/userLeaguesList", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public List<MyLeagueFormBean> listAllUserLeagues() {
		try {
			return leagueService.getMyLeaguesIncludingGlobal(userService.getLoggedInUser().getUserId());
		} catch (ServiceException e) {
			LOG.error(e);
			// TODO: HANDLE WHEN EXCEPTION
			return null;
		} catch (EmailNotFoundException e) {
			// TODO: HANDLE WHEN EXCEPTION
			LOG.error(e);
			return null;
		}
	}
	
	@RequestMapping(value = "/myPortfolio", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public MyPortfolioFormBean myPortfolio(
			@RequestParam("leagueId") long leagueId) {
		try {
			return portfolioService.getMyPortfolio(leagueId, userService.getLoggedInUser().getUserId());
		}catch (EmailNotFoundException e) {
			LOG.error(e);
			// TODO: HANDLE WHEN EXCEPTION
			return null;
		} catch (PortfolioServiceException e) {
			LOG.error(e);
			// TODO: HANDLE WHEN EXCEPTION
			return null;
		} 
	}
	
	@RequestMapping(value = "/myRecentTrades", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public MyRecentTradeFormBean myRecentTrades(
			@RequestParam("leagueId") long leagueId) { 
		try {
			return portfolioService.getMyRecentTrades(leagueId, userService.getLoggedInUser().getUserId());
		}catch (EmailNotFoundException e) {
			LOG.error(e);
			// TODO: HANDLE WHEN EXCEPTION
			return null;
		} catch (PortfolioServiceException e) {
			LOG.error(e);
			// TODO: HANDLE WHEN EXCEPTION
			return null;
		} 
	}
	
}
