package com.misys.stockmarket.dao;

import javax.persistence.Query;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.domain.entity.LeagueMaster;
import com.misys.stockmarket.domain.entity.LeagueUser;
import com.misys.stockmarket.exception.DBRecordNotFoundException;

@Service("leagueDAO")
@Repository
public class LeagueDAO extends BaseDAO {

	public LeagueMaster findByName(String name)
			throws DBRecordNotFoundException {
		try {
			Query q = entityManager
					.createQuery("select e from LeagueMaster e where e.name = ? ");
			q.setParameter(1, name);
			return (LeagueMaster) q.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			throw new DBRecordNotFoundException(e);
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

	public LeagueUser findLeagueById(long leagueUserId)
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

}
