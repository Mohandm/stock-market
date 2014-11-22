package com.misys.stockmarket.achievements;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.domain.entity.OrderMaster;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.exception.service.OrderServiceException;
import com.misys.stockmarket.services.AchievementExecutionService;

@Service("sellOrder")
@Repository
public class SellOrder extends AbstractAchievement implements IAchievement {

	@Inject
	AchievementExecutionService achievementExecutionService;

	@Override
	public int getCompleted(UserMaster userMaster) {
		int completed = 0;
		List<OrderMaster> orderList = new ArrayList<OrderMaster>();
		try {
			orderList = achievementExecutionService.findAllCompletedSellOrders(userMaster);
			completed = orderList.size();
		} catch (OrderServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return completed;
	}
}
