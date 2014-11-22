package com.misys.stockmarket.achievements;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.domain.entity.UserMaster;

@Service("achievementFacade")
@Repository
public class AchievementFacade {

	@Inject
	AchievementCategoryFactory achievementCategoryFactory;
	public void evaluate (UserMaster user, List<String> categories) {
		for (String category : categories) {
			achievementCategoryFactory.getAchievementCategory(category).evaluate(user,category);
		}
	}

}
