package com.misys.stockmarket.dao;

import javax.persistence.Query;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.domain.entity.RewardMaster;
import com.misys.stockmarket.exception.DAOException;
import com.misys.stockmarket.exception.DBRecordNotFoundException;

@Service("rewardDAO")
@Repository
public class RewardDAO extends BaseDAO {

	public RewardMaster findRewardByType(String rewardType)throws DAOException {
		try {
			Query q = entityManager
					.createQuery("select e from RewardMaster e where e.rewardType = ? ");
			q.setParameter(1, rewardType);
			return (RewardMaster) q.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			throw new DBRecordNotFoundException(e);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	public RewardMaster findRewardById(long id)throws DAOException {
		try {
			Query q = entityManager
					.createQuery("select e from RewardMaster e where e.rewardType = ? ");
			q.setParameter(1, id);
			return (RewardMaster) q.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			throw new DBRecordNotFoundException(e);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
}
