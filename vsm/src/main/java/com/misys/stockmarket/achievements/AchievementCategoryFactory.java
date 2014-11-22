package com.misys.stockmarket.achievements;

import javax.inject.Inject;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service("achievementCategoryFactory")
@Repository
public class AchievementCategoryFactory {

	// TODO: Find why this is not working
	
	// TODO: get the data from ServiceFactory from mylc
	@Inject
	private ApplicationContext applicationContext;

	public IAchievement getAchievementCategory(String categoryName) {
		// TODO: handle exception
		return (IAchievement) applicationContext.getBean(categoryName);
		/*if ("buyOrder".equals(categoryName)) {
			return new BuyOrder();
		} else {
			return null;
		}*/
	}

}
