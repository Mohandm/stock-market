package com.misys.stockmarket.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Query;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.domain.entity.AchievementRule;
import com.misys.stockmarket.domain.entity.AchievementType;
import com.misys.stockmarket.domain.entity.UserAchievement;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.exception.DAOException;
import com.misys.stockmarket.exception.DBRecordNotFoundException;

@Service("achievementsDAO")
@Repository
public class AchievementsDAO extends BaseDAO {

	public AchievementRule findAchievementRule(AchievementType type,
			BigDecimal level) throws DBRecordNotFoundException {
		try {
			Query q = entityManager
					.createQuery("select e from AchievementRule e where e.achievementType = ?1 and e.level = ?2 ");
			q.setParameter(1, type);
			q.setParameter(2, level);
			return (AchievementRule) q.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			throw new DBRecordNotFoundException(e);
		}
	}

	public AchievementType findAchievementType(String name)
			throws DBRecordNotFoundException {

		try {
			Query q = entityManager
					.createQuery("select e from AchievementRule e where e.name = ?1");
			q.setParameter(1, name);
			return (AchievementType) q.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			throw new DBRecordNotFoundException(e);
		}

	}

	public UserAchievement findUserAchievement(UserMaster userMaster, AchievementType achievementType)
			throws DBRecordNotFoundException {

		try {
			Query q = entityManager
					.createQuery("select e from UserAchievement e where e.userMaster = ?1 and e.achievementType = ?2");
			q.setParameter(1, userMaster);
			q.setParameter(2, achievementType);
			return (UserAchievement) q.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			throw new DBRecordNotFoundException(e);
		}

	}
	
	public List<UserAchievement> findAllAchievementsForEvaluation()
			throws DAOException {
		try {
			Query q = entityManager
					.createQuery("select e from UserAchievement e where e.evaluated = ?1");
			q.setParameter(1, IApplicationConstants.ACHIEVEMENT_EVALUTED_NO);
			return q.getResultList();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public List<UserAchievement> findAllAchievementsForPublishing(
			UserMaster userMaster) throws DAOException {
		try {
			Query q = entityManager
					.createQuery("select e from UserAchievement e where e.published = ?1 and e.userMaster = ?2");
			q.setParameter(1, IApplicationConstants.ACHIEVEMENT_PUBLISHED_NO);
			q.setParameter(2, userMaster);
			return q.getResultList();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	public List<UserAchievement> findAllUserAchievements(UserMaster userMaster) throws DAOException {
		try {
			Query q = entityManager
					.createQuery("select e from UserAchievement e where e.userMaster = ?1");
			q.setParameter(1, userMaster);
			return q.getResultList();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	public List<AchievementRule> findAllAchievements(AchievementType type, BigDecimal level) throws DAOException {
		try {
			Query q = entityManager
					.createQuery("select e from AchievementRule e where e.achievementType = ?1 and e.level < ?2");
			q.setParameter(1, type);
			q.setParameter(2, level);
			return q.getResultList();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
}
