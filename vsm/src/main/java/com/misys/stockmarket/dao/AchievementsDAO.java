package com.misys.stockmarket.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.domain.entity.AchievementCategory;
import com.misys.stockmarket.domain.entity.AchievementRule;
import com.misys.stockmarket.domain.entity.UserAchievement;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.exception.DAOException;
import com.misys.stockmarket.exception.DBRecordNotFoundException;

@Service("achievementsDAO")
@Repository
public class AchievementsDAO extends BaseDAO {

	public AchievementRule findAchievementRule(AchievementCategory category,
			int level) throws DBRecordNotFoundException {
		try {
			Query q = entityManager
					.createQuery("select e from AchievementRule e where e.achievementCategory = ?1 and e.level = ?2 ");
			q.setParameter(1, category);
			q.setParameter(2, level);
			return (AchievementRule) q.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			throw new DBRecordNotFoundException(e);
		}
	}

	public AchievementCategory findAchievementType(String name)
			throws DBRecordNotFoundException {

		try {
			Query q = entityManager
					.createQuery("select e from AchievementCategory e where e.name = ?1");
			q.setParameter(1, name);
			return (AchievementCategory) q.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			throw new DBRecordNotFoundException(e);
		}
	}

	public List<AchievementCategory> findAllAchievements() throws DAOException {
		try {
			Query q = entityManager
					.createQuery("select e from AchievementCategory e");
			return q.getResultList();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public List<UserAchievement> findAllUserAchievements(UserMaster userMaster,
			AchievementCategory achievementCategory) throws DAOException {
		try {
			Query q = entityManager
					.createQuery("select e from UserAchievement e where e.userMaster = ?1 and e.achievementRule.achievementCategory = ?2 ");
			q.setParameter(1, userMaster);
			q.setParameter(2, achievementCategory);
			return q.getResultList();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	public Long getMaxCompletedLevel(UserMaster userMaster,
			AchievementCategory achievementCategory) throws DAOException {
		try {
			Query q = entityManager
					.createQuery("select count(e) from UserAchievement e where e.userMaster = ?1 and e.achievementRule.achievementCategory = ?2 ");
			q.setParameter(1, userMaster);
			q.setParameter(2, achievementCategory);
			return (Long) q.getSingleResult();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public List<UserAchievement> findAllCompletedAchievements(
			UserMaster userMaster) throws DAOException {
		try {
			Query q = entityManager
					.createQuery("select e from UserAchievement e where e.userMaster = ?1 ");
			q.setParameter(1, userMaster);
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
}
