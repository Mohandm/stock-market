package com.misys.stockmarket.achievements;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.exception.service.AchievementExecutionServiceException;
import com.misys.stockmarket.services.AchievementExecutionService;

@Service("singleDayOrders")
@Repository
public class SingleDayOrders extends AbstractAchievement implements
		IAchievement {

	private static final Log LOG = LogFactory.getLog(SingleDayOrders.class);

	@Inject
	AchievementExecutionService achievementExecutionService;

	@Override
	public int getCompleted(UserMaster userMaster) 
	{
		try 
		{
			return achievementExecutionService
					.findSingleDayOrders(userMaster);
		} catch (AchievementExecutionServiceException e) 
		{
			LOG.error(e);
		}
		return 0;
	}

}
