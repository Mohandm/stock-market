package com.misys.stockmarket.achievements;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.services.AchievementExecutionService;

@Service("differentCompanies")
@Repository
public class DifferentCompanies extends AbstractAchievement implements
		IAchievement {

	@Inject
	AchievementExecutionService achievementExecutionService;

	@Override
	public int getCompleted(UserMaster userMaster) {
		return 0;
	}

}
