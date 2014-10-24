package com.misys.stockmarket.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.domain.entity.OrderMaster;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.exception.DAOException;

@Service("orderMasterDAO")
@Repository
public class OrderMasterDAO extends BaseDAO {
	public List<OrderMaster> findAllPendingOrders() throws DAOException {
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("status", IApplicationConstants.ORDER_STATUS_PENDING);
		return findByFilter(OrderMaster.class, criteria);
	}

	public List<OrderMaster> findAllCompletedOrders(UserMaster userMaster)
			throws DAOException {
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("userMaster", userMaster);
		criteria.put("status", IApplicationConstants.ORDER_STATUS_COMPLETED);
		return findByFilter(OrderMaster.class, criteria);
	}

	public List<OrderMaster> findAllCompletedOrdersByLeaugeUser(
			long leagueUserId) throws DAOException {
		try {
			Query q = entityManager
					.createQuery("select e from OrderMaster e join e.orderExecutions where e.leagueUser.leagueUserId = ?1 ");
			q.setParameter(1, leagueUserId);
			return q.getResultList();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
}
