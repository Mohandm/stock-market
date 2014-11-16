package com.misys.stockmarket.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.achievements.Achievement;
import com.misys.stockmarket.achievements.BuyOrder;
import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.dao.AchievementsDAO;
import com.misys.stockmarket.domain.entity.AchievementRule;
import com.misys.stockmarket.domain.entity.AchievementType;
import com.misys.stockmarket.domain.entity.UserAchievement;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.exception.DAOException;
import com.misys.stockmarket.exception.DBRecordNotFoundException;
import com.misys.stockmarket.exception.service.AchievementServiceException;

@Service("achievementsService")
@Repository
public class AchievementsService {

	@Inject
	private AchievementsDAO achievementsDAO;

	public void evaluateAchievement(UserAchievement userAchievements)
			throws AchievementServiceException {
		try {
			// Get type first
			AchievementType achievementType = userAchievements
					.getAchievementType();
			Achievement achievement = getAchievement(achievementType.getName());

			// Get volume for user
			UserMaster userMaster = userAchievements.getUserMaster();
			BigDecimal quantity = achievement.getQuantity(userMaster);

			// Retrieve the rule
			BigDecimal nextLevel = userAchievements.getNextLevel();
			// TODO: Review whether the exception needs to be handled or not
			AchievementRule rule = achievementsDAO.findAchievementRule(
					achievementType, nextLevel);

			// Evaluate the rule
			BigDecimal requiredQuantity = rule.getQuantity();
			if (quantity.compareTo(new BigDecimal(0)) > 0
					&& quantity.compareTo(requiredQuantity) >= 0) {
				// Get the next level for this rule and set the publish flag
				if (IApplicationConstants.ACHIEVEMENT_MAX_LEVELS
						.compareTo(nextLevel) != 0) {
					userAchievements.setNextLevel(nextLevel.add(BigDecimal
							.valueOf(1)));
				}
				userAchievements
						.setPublished(IApplicationConstants.ACHIEVEMENT_PUBLISHED_NO);
			}

			// Set as evaluated an the current quantity
			userAchievements.setCurrentQuantity(quantity);
			userAchievements
					.setEvaluated(IApplicationConstants.ACHIEVEMENT_EVALUTED_YES);
			// Update the user achievement in the database
			achievementsDAO.update(userAchievements);
		} catch (DAOException e) {
			throw new AchievementServiceException(e);
		}
	}

	public List<UserAchievement> getPendingAchievementsForEvaluation()
			throws AchievementServiceException {
		List<UserAchievement> pendingAchievements = new ArrayList<UserAchievement>();
		try {
			pendingAchievements = achievementsDAO
					.findAllAchievementsForEvaluation();
		} catch (DAOException e) {
			throw new AchievementServiceException(e);
		}
		return pendingAchievements;
	}

	public List<UserAchievement> getPendingAchievementsForPublishing(
			UserMaster userMaster) throws AchievementServiceException {
		List<UserAchievement> pendingAchievements = new ArrayList<UserAchievement>();
		try {
			pendingAchievements = achievementsDAO
					.findAllAchievementsForPublishing(userMaster);
		} catch (DAOException e) {
			throw new AchievementServiceException(e);
		}
		// TODO: need to set these achievements back as already published to the
		// user
		return pendingAchievements;
	}

	public List<AchievementRule> getCompletedAchievements(UserMaster userMaster) {
		List<UserAchievement> userAchievements = new ArrayList<UserAchievement>();
		List<AchievementRule> completedAchievements = new ArrayList<AchievementRule>();
		try {
			userAchievements = achievementsDAO
					.findAllUserAchievements(userMaster);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (UserAchievement achievement : userAchievements) {
			try {
				completedAchievements.addAll(achievementsDAO
						.findAllAchievements(achievement.getAchievementType(),
								achievement.getNextLevel()));
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return completedAchievements;
	}

	public void setAchievementForEvaluation(UserMaster userMaster, String name) {
		AchievementType achievementType = getAchievementType(name);
		UserAchievement userAchievement = new UserAchievement();
		try {
			userAchievement = achievementsDAO.findUserAchievement(userMaster,
					achievementType);
		} catch (DBRecordNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userAchievement
				.setEvaluated(IApplicationConstants.ACHIEVEMENT_EVALUTED_NO);
		try {
			achievementsDAO.update(userAchievement);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static Achievement getAchievement(String name) {
		// TODO Use some mapping to in the class and remove the
		// hardcoding
		Achievement achievement = new BuyOrder();
		return achievement;
	}

	private AchievementType getAchievementType(String name) {
		Achievement achievement = getAchievement(name);
		AchievementType achievementType = new AchievementType();
		try {
			achievementType = achievementsDAO.findAchievementType(achievement
					.getName());
		} catch (DBRecordNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return achievementType;
	}

}
