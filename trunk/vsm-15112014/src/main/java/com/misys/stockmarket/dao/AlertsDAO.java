package com.misys.stockmarket.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.domain.entity.UserAlerts;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.domain.entity.WatchStock;
import com.misys.stockmarket.exception.DAOException;

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
	
	public List<UserAlerts> findAllOrderedAlerts(UserMaster user) throws DAOException {
		try {
			Query q = entityManager
					.createQuery("select distinct e from UserAlerts e where e.userMaster.userId = ?1 order by e.notifiedDate");
			q.setParameter(1, user.getUserId());
			return q.getResultList();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
}
