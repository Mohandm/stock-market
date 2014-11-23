package com.misys.stockmarket.achievements;

import javax.inject.Inject;

import com.misys.stockmarket.domain.entity.AchievementRule;
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
			return false;
		}

		if (getCompleted(user) >= rule.getQuantity()) {
			try {
				achievementsService.addUserAchievement(user, rule);
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