package com.misys.stockmarket.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.dao.AlertsDAO;
import com.misys.stockmarket.dao.StockDAO;
import com.misys.stockmarket.domain.entity.StockCurrentQuotes;
import com.misys.stockmarket.domain.entity.StockMaster;
import com.misys.stockmarket.domain.entity.UserAlerts;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.domain.entity.WatchStock;
import com.misys.stockmarket.exception.DAOException;
import com.misys.stockmarket.exception.EmailNotFoundException;
import com.misys.stockmarket.exception.service.AlertsServiceException;
import com.misys.stockmarket.mbeans.WatchListFormBean;
import com.misys.stockmarket.platform.web.ResponseMessage;
import com.misys.stockmarket.services.email.EmailSenderService;
import com.misys.stockmarket.utility.EmailFormatter;

@Service("alertsService")
@Repository
public class AlertsService {

	private static final Log LOG = LogFactory.getLog(AlertsService.class);

	@Inject
	private AlertsDAO alertsDAO;

	@Inject
	private StockDAO stockDAO;

	@Inject
	private EmailSenderService emailSender;

	@Inject
	private StockService stockService;

	@Inject
	private UserService userService;

	public ResponseMessage saveNewWatchStock(WatchListFormBean watchListFormBean) {
		// Validate bean

		// Process Watch Stock
		try {
			WatchStock watchStock = new WatchStock();
			watchStock.setUserMaster(userService.getLoggedInUser());
			watchStock.setLimit(watchListFormBean.getLimit());
			watchStock.setLowerLimit(watchListFormBean.getLowerLimit());
			watchStock
					.setStatus(IApplicationConstants.WATCH_STOCK_STATUS_PENDING);
			watchStock.setStockMaster(stockDAO.findBySymbol(watchListFormBean
					.getTikerSymbol()));

			alertsDAO.persist(watchStock);

			return new ResponseMessage(
					ResponseMessage.Type.success,
					"Your Watch Stock Alert has been setup. You will get a Email when the Price Limit is reached!");
		} catch (Exception e) {
			LOG.error(e);
			return new ResponseMessage(
					ResponseMessage.Type.danger,
					"There was a technical error while setting up Watch Stock Alert. Please try again!");
		}
	}

	public List<UserAlerts> listAllAlerts() throws AlertsServiceException {
		try {
			return alertsDAO.findAllAlerts(userService.getLoggedInUser());
		} catch (EmailNotFoundException e) {
			LOG.error(e);
			throw new AlertsServiceException(e);
		} catch (DAOException e) {
			LOG.error(e);
			throw new AlertsServiceException(e);
		}
	}

	public void triggerWatchStockAlerts() {
		// Trigger Watch Stock
		try {
			List<StockCurrentQuotes> stockCurrentQuotes = stockService
					.listAllCurrentStockQuotes();
			List<WatchStock> pendingWatchStockList = alertsDAO
					.findAllPendingWatchList();
			List<StockMaster> stockmasterList = stockService
					.listAllActiveStocks();

			HashMap<Long, StockCurrentQuotes> stockCurrentQuotesMap = new HashMap<Long, StockCurrentQuotes>();
			for (Iterator<StockCurrentQuotes> iterator = stockCurrentQuotes
					.iterator(); iterator.hasNext();) {
				StockCurrentQuotes stockCurrentQuote = (StockCurrentQuotes) iterator
						.next();
				stockCurrentQuotesMap.put(new Long(stockCurrentQuote
						.getStockMaster().getStockId()), stockCurrentQuote);
			}

			HashMap<Long, StockMaster> stockMasterMap = new HashMap<Long, StockMaster>();
			for (Iterator<StockMaster> iterator = stockmasterList.iterator(); iterator
					.hasNext();) {
				StockMaster stockMaster = (StockMaster) iterator.next();
				stockMasterMap.put(new Long(stockMaster.getStockId()),
						stockMaster);
			}

			for (Iterator<WatchStock> iterator = pendingWatchStockList
					.iterator(); iterator.hasNext();) {
				WatchStock watchStock = (WatchStock) iterator.next();
				StringBuffer mailContent = new StringBuffer();

				int typeOfAlert = triggerAlert(watchStock,
						stockCurrentQuotesMap.get(watchStock.getStockMaster()
								.getStockId()));
				// Trigger Alert
				if (typeOfAlert == 0 || typeOfAlert == 1) {
					StockMaster stockmaster = stockMasterMap.get(watchStock
							.getStockMaster().getStockId());
					mailContent.append("The Stock ");
					mailContent.append(stockmaster.getTikerSymbol()).append(
							" (");
					mailContent.append(stockmaster.getName()).append(")");

					if (typeOfAlert == 0) {
						mailContent
								.append(" has decreased below the Lower Limit (");
						mailContent.append(watchStock.getLowerLimit());
					} else {
						mailContent
								.append(" has increased above the Higher Limit (");
						mailContent.append(watchStock.getLimit().toString());
					}

					mailContent.append(") and the current value is ");
					mailContent.append(stockCurrentQuotesMap.get(
							watchStock.getStockMaster().getStockId())
							.getLastTradePriceOnly());

					UserMaster user = userService.findById(watchStock
							.getUserMaster().getUserId());

					// Store User Alert
					UserAlerts userAlerts = new UserAlerts();
					userAlerts.setUserMaster(user);
					userAlerts.setMessage(mailContent.toString());
					userAlerts.setNotifiedDate(new Date());
					alertsDAO.persist(userAlerts);

					// Update Watch Stock to Completed
					watchStock
							.setStatus(IApplicationConstants.WATCH_STOCK_STATUS_COMPLETED);
					alertsDAO.update(watchStock);

					// SEND ALERT
					SimpleMailMessage message = EmailFormatter
							.generateWatchListAlertsMessage(user,
									mailContent.toString());
					emailSender.sendEmail(message);
				}
			}
		} catch (Exception e) {
			LOG.error(e);
		}
	}

	private int triggerAlert(WatchStock watchStock,
			StockCurrentQuotes stockCurrentQuote) {
		BigDecimal higherLimit = watchStock.getLimit();
		BigDecimal lowerLimit = watchStock.getLowerLimit();
		BigDecimal lastTradePrice = stockCurrentQuote.getLastTradePriceOnly();
		if ((lastTradePrice.compareTo(lowerLimit) <= 0)) {
			// Trigger Lower Limit Reached Alert
			return 0;
		} else if ((lastTradePrice.compareTo(higherLimit) >= 0)) {
			// Trigger Higher Limit Reached Alert
			return 1;
		} else {
			// Don't Trigger
			return -1;
		}
	}

}
