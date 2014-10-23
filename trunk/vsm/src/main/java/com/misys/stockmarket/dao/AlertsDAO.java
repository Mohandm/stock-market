package com.misys.stockmarket.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.domain.entity.UserAlerts;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.domain.entity.WatchStock;
import com.misys.stockmarket.exception.DAOException;
import com.misys.stockmarket.exception.service.AlertsServiceException;

@Service("alertsDAO")
@Repository
public class AlertsDAO extends BaseDAO {

	public List<WatchStock> findAllPendingWatchList() throws DAOException {
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("status", IApplicationConstants.WATCH_STOCK_STATUS_PENDING);
		return findByFilter(WatchStock.class, criteria);
	}

	public List<UserAlerts> findAllAlerts(UserMaster user) throws DAOException {
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("userMaster", user);
		return findByFilter(UserAlerts.class, criteria);
	}
}
