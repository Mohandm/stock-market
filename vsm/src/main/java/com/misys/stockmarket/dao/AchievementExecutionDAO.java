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

@Service("ahievementExecutionDAO")
@Repository
public class AchievementExecutionDAO extends BaseDAO {

	public List<OrderMaster> findAllCompletedBuyOrders(UserMaster userMaster)
			throws DAOException {
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("leagueUser.userMaster", userMaster);
		criteria.put("status", IApplicationConstants.ORDER_STATUS_COMPLETED);
		criteria.put("type", IApplicationConstants.BUY_TYPE);
		return findByFilter(OrderMaster.class, criteria);
	}

	public List<OrderMaster> findAllCompletedSellOrders(UserMaster userMaster)
			throws DAOException {
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("leagueUser.userMaster", userMaster);
		criteria.put("status", IApplicationConstants.ORDER_STATUS_COMPLETED);
		criteria.put("type", IApplicationConstants.SELL_TYPE);
		return findByFilter(OrderMaster.class, criteria);
	}

	public List<OrderMaster> findAllCompletedSafeOrders(UserMaster userMaster)
			throws DAOException {
		try {
			Query q = entityManager
					.createQuery("select e from OrderMaster e where e.leagueUser.userMaster = ?1 and e.status = ?2 and e.priceType in (?3, ?4)");
			q.setParameter(1, userMaster);
			q.setParameter(2, IApplicationConstants.ORDER_STATUS_COMPLETED);
			q.setParameter(3, IApplicationConstants.ORDER_PRICE_TYPE_LIMIT);
			q.setParameter(4, IApplicationConstants.ORDER_PRICE_TYPE_STOPLOSS);
			return (List<OrderMaster>) q.getResultList();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

}
