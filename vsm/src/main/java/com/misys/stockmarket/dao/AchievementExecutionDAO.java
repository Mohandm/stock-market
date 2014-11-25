package com.misys.stockmarket.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.domain.entity.FollowerMaster;
import com.misys.stockmarket.domain.entity.OrderMaster;
import com.misys.stockmarket.domain.entity.UserInvitation;
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

	public List<OrderMaster> findAllCompletedRiskOrders(UserMaster userMaster)
			throws DAOException {
		try {
			Query q = entityManager
					.createQuery("select e from OrderMaster e where e.leagueUser.userMaster = ?1 and e.status = ?2 and e.priceType = ?3 ");
			q.setParameter(1, userMaster);
			q.setParameter(2, IApplicationConstants.ORDER_STATUS_COMPLETED);
			q.setParameter(3, IApplicationConstants.ORDER_PRICE_TYPE_MARKET);
			return (List<OrderMaster>) q.getResultList();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public int findDistinctCompanytOrders(UserMaster user) throws DAOException {
		try {
			Query q = entityManager
					.createQuery("select COUNT(DISTINCT e.stockMaster.stockId) from OrderMaster e where e.leagueUser.userMaster = ?1 and e.status = ?2");
			q.setParameter(1, user);
			q.setParameter(2, IApplicationConstants.ORDER_STATUS_COMPLETED);
			Long count = (Long) q.getSingleResult();
			if (count == null) {
				count = 0L;
			}
			return count.intValue();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public List<UserInvitation> findAllAcceptedInvites(UserMaster userMaster)
			throws DAOException {
		try {
			Query q = entityManager
					.createQuery("select e from UserInvitation e where e.referer = ?1 and e.accepted = ?2");
			q.setParameter(1, userMaster);
			q.setParameter(2, IApplicationConstants.INVITATION_ACCEPTED_YES);
			return (List<UserInvitation>) q.getResultList();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public List<FollowerMaster> findAllFollowers(UserMaster userMaster)
			throws DAOException {
		try {
			Query q = entityManager
					.createQuery("select e from FollowerMaster e where e.playerUserId = ?1 ");
			q.setParameter(1, userMaster.getUserId());
			return (List<FollowerMaster>) q.getResultList();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public int findSingleDayOrders(UserMaster user) throws DAOException {
		try {
			Query q = entityManager
					.createQuery("select max(count(e.orderId)) from OrderMaster e where e.leagueUser.userMaster = ?1 and e.status = ?2 group by to_date(to_char(e.orderDate, 'DD-MON-YY'), 'DD-MON-YYYY')");
			q.setParameter(1, user);
			q.setParameter(2, IApplicationConstants.ORDER_STATUS_COMPLETED);
			Long count = (Long) q.getSingleResult();
			if (count == null) {
				count = 0L;
			}
			return count.intValue();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

}
