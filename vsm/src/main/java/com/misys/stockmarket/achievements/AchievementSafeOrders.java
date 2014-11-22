package com.misys.stockmarket.achievements;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.domain.entity.OrderMaster;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.exception.service.AchievementExecutionServiceException;
import com.misys.stockmarket.services.AchievementExecutionService;

@Service("safeOrders")
@Repository
public class AchievementSafeOrders extends AbstractAchievement implements
		IAchievement {

	@Inject
	AchievementExecutionService achievementExecutionService;

	@Override
	public int getCompleted(UserMaster userMaster) {
		int completed = 0;
		List<OrderMaster> orderList = new ArrayList<OrderMaster>();
		try {
			orderList = achievementExecutionService
					.findAllCompletedSafeOrders(userMaster);
			completed = orderList.size();
		} catch (AchievementExecutionServiceException e) {
			e.printStackTrace();
		}
		return completed;
	}
}
