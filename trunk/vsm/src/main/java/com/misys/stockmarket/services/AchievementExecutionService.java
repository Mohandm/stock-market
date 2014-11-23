package com.misys.stockmarket.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.dao.AchievementExecutionDAO;
import com.misys.stockmarket.domain.entity.OrderMaster;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.exception.DAOException;
import com.misys.stockmarket.exception.service.AchievementExecutionServiceException;

@Service("achievementExecutionService")
@Repository
public class AchievementExecutionService {

	private static final Log LOG = LogFactory
			.getLog(AchievementExecutionService.class);

	@Inject
	AchievementExecutionDAO achievementExecutionDAO;

	public List<OrderMaster> findAllCompletedBuyOrders(UserMaster user)
			throws AchievementExecutionServiceException {
		List<OrderMaster> orderList = new ArrayList<OrderMaster>();
		try {
			orderList = achievementExecutionDAO.findAllCompletedBuyOrders(user);
		} catch (DAOException e) {
			LOG.error(e);
			throw new AchievementExecutionServiceException(e);
		}
		return orderList;
	}

	public List<OrderMaster> findAllCompletedSellOrders(UserMaster user)
			throws AchievementExecutionServiceException {
		List<OrderMaster> orderList = new ArrayList<OrderMaster>();
		try {
			orderList = achievementExecutionDAO
					.findAllCompletedSellOrders(user);
		} catch (DAOException e) {
			LOG.error(e);
			throw new AchievementExecutionServiceException(e);
		}
		return orderList;
	}

	public List<OrderMaster> findAllCompletedSafeOrders(UserMaster user)
			throws AchievementExecutionServiceException {
		List<OrderMaster> orderList = new ArrayList<OrderMaster>();
		try {
			orderList = achievementExecutionDAO
					.findAllCompletedSafeOrders(user);
		} catch (DAOException e) {
			LOG.error(e);
			throw new AchievementExecutionServiceException(e);
		}
		return orderList;
	}

	public List<OrderMaster> findAllCompletedRiskOrders(UserMaster userMaster)
			throws AchievementExecutionServiceException {
		List<OrderMaster> orderList = new ArrayList<OrderMaster>();
		try {
			orderList = achievementExecutionDAO
					.findAllCompletedRiskOrders(userMaster);
		} catch (DAOException e) {
			LOG.error(e);
			throw new AchievementExecutionServiceException(e);
		}
		return orderList;
	}

}