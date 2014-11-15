package com.misys.stockmarket.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.domain.entity.FollowerMaster;
import com.misys.stockmarket.domain.entity.LeagueMaster;
import com.misys.stockmarket.domain.entity.LeagueUser;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.exception.DAOException;
import com.misys.stockmarket.exception.DBRecordNotFoundException;

@Service("leagueDAO")
@Repository
public class LeagueDAO extends BaseDAO {

	public LeagueMaster findByName(String name) throws DAOException {
		try {
			Query q = entityManager
					.createQuery("select e from LeagueMaster e where e.name = ? ");
			q.setParameter(1, name);
			return (LeagueMaster) q.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			throw new DBRecordNotFoundException(e);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public LeagueMaster findById(long id) throws DAOException {
		try {
			Query q = entityManager
					.createQuery("select e from LeagueMaster e where e.leagueId = ? ");
			q.setParameter(1, id);
			return (LeagueMaster) q.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			throw new DBRecordNotFoundException(e);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	/**
	 * Method to find User League by League Id and User Id
	 * 
	 * @param leagueId
	 * @param userId
	 * @return LeagueUser
	 * @throws DBRecordNotFoundException
	 */
	public LeagueUser findLeagueUser(long leagueId, long userId)
			throws DBRecordNotFoundException {
		try {
			Query q = entityManager
					.createQuery("select e from LeagueUser e where e.leagueMaster.leagueId = ?1 and e.userMaster.userId = ?2 ");
			q.setParameter(1, leagueId);
			q.setParameter(2, userId);
			return (LeagueUser) q.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			throw new DBRecordNotFoundException(e);
		}
	}
	
	public FollowerMaster findFollowerMaster(long userId, long playerId, long leagueId, String status1, String status2)
			throws DBRecordNotFoundException {
		try {
			Query q = entityManager
					.createQuery("select e from FollowerMaster e where e.leagueMaster.leagueId = ?1 and e.followerUserMaster.userId = ?2 and e.playerUserId = ?3 and (e.status = ?4 or e.status = ?5)");
			q.setParameter(1, leagueId);
			q.setParameter(2, userId);
			q.setParameter(3, playerId);
			q.setParameter(4, status1);
			q.setParameter(5, status2);
			return (FollowerMaster) q.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			throw new DBRecordNotFoundException(e);
		}
	}
	
	public FollowerMaster findFollowerMaster(long userId, long playerId, long leagueId, String status)
			throws DBRecordNotFoundException {
		try {
			Query q = entityManager
					.createQuery("select e from FollowerMaster e where e.leagueMaster.leagueId = ?1 and e.followerUserMaster.userId = ?2 and e.playerUserId = ?3 and e.status = ?4");
			q.setParameter(1, leagueId);
			q.setParameter(2, userId);
			q.setParameter(3, playerId);
			q.setParameter(4, status);
			return (FollowerMaster) q.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			throw new DBRecordNotFoundException(e);
		}
	}

	public LeagueUser findLeagueUserById(long leagueUserId)
			throws DBRecordNotFoundException {
		try {
			Query q = entityManager
					.createQuery("select e from LeagueUser e where e.leagueUserId = ?1 ");
			q.setParameter(1, leagueUserId);
			return (LeagueUser) q.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			throw new DBRecordNotFoundException(e);
		}
	}

	public List<LeagueUser> findLeagueUserByUserId(UserMaster user)
			throws DAOException {
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("userMaster", user);
		return findByFilter(LeagueUser.class, criteria);
	}

	public List<LeagueMaster> findAllGameLeagues() throws DAOException {
		try {
			Query q = entityManager
					.createQuery("select distinct e from LeagueMaster e where e.stage <> 0 order by e.stage");
			return q.getResultList();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	public List<FollowerMaster> findMyFollowers(long playerId, long leagueId) throws DAOException {
		try {
			Query q = entityManager.createQuery("select distinct e from FollowerMaster e where e.leagueMaster.leagueId = ?1 and e.playerUserId = ?2 and (e.status = ?3 or e.status = ?4)");
			q.setParameter(1, leagueId);
			q.setParameter(2, playerId);
			q.setParameter(3, IApplicationConstants.FOLLOWER_STATUS_PENDING);
			q.setParameter(4, IApplicationConstants.FOLLOWER_STATUS_ACCEPTED);
			return q.getResultList();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	public List<FollowerMaster> findYourFollowing(long userId, long leagueId) throws DAOException {
		try {
			Query q = entityManager.createQuery("select distinct e from FollowerMaster e where e.leagueMaster.leagueId = ?1 and e.followerUserMaster.userId = ?2 and e.status = ?3");
			q.setParameter(1, leagueId);
			q.setParameter(2, userId);
			q.setParameter(3, IApplicationConstants.FOLLOWER_STATUS_ACCEPTED);
			return q.getResultList();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	

	public List<LeagueUser> findAllLeagueUsers(long leagueId)
			throws DAOException {

		try {
			Query q = entityManager
					.createQuery("select  e from LeagueUser e where e.leagueMaster.leagueId = ?1 ");
			q.setParameter(1, leagueId);
			return q.getResultList();
		} catch (Exception e) {
			throw new DAOException(e);
		}

	}
}
