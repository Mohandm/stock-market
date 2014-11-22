package com.misys.stockmarket.achievements;

import java.util.Date;

import javax.inject.Inject;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.domain.entity.AchievementRule;
import com.misys.stockmarket.domain.entity.UserAchievement;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.exception.service.AchievementServiceException;
import com.misys.stockmarket.services.AchievementsService;

public abstract class AbstractAchievement {

	@Inject
	AchievementsService achievementsService;

	public AbstractAchievement() {
		super();
	}

	public boolean evaluate(UserMaster user, String category) {

		AchievementRule rule = new AchievementRule();
		try {
			rule = achievementsService.getNextRule(category, user);
		} catch (AchievementServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (getCompleted(user) >= rule.getQuantity()) {
			UserAchievement achievement = new UserAchievement();
			achievement.setUserMaster(user);
			achievement.setAchievementRule(rule);
			achievement.setCompleted(new Date());
			achievement
					.setPublished(IApplicationConstants.ACHIEVEMENT_PUBLISHED_NO);
			try {
				achievementsService.addUserAchievement(achievement);
			} catch (AchievementServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			// TODO: Evaluate next rule as well
			return true;

		}
		return false;
	}

	public abstract int getCompleted(UserMaster userMaster);
}