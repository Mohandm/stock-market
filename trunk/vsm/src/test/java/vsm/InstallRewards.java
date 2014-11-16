package vsm;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.domain.entity.RewardMaster;
import com.misys.stockmarket.enums.REWARD_ERROR_CODES;
import com.misys.stockmarket.exception.RewardException;
import com.misys.stockmarket.exception.service.StockServiceException;
import com.misys.stockmarket.services.RewardService;

public class InstallRewards {
	private static final Log LOG = LogFactory.getLog(InstallNASDAQStocks.class);

	public static void main(String as[]) throws StockServiceException,
			RewardException {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"META-INF\\spring\\applicationContext.xml");
		RewardService rewardService = (RewardService) applicationContext
				.getBean("rewardService");

		Map<String, BigDecimal> rewardMap = new HashMap<String, BigDecimal>();
		
		rewardMap.put(IApplicationConstants.REWARD_TYPE_ACHIEVEMENT,
				new BigDecimal(100));
		rewardMap.put(IApplicationConstants.REWARD_TYPE_REFERAL,
				new BigDecimal(200));

		for (Entry<String, BigDecimal> rewardEntry : rewardMap.entrySet()) {
			RewardMaster rewardMaster = null;
			try {
				rewardMaster = rewardService.getRewardByType(rewardEntry
						.getKey());
			} catch (RewardException e) {
				if (e.getErrorCode().compareTo(
						REWARD_ERROR_CODES.REWARD_NOT_FOUND) == 0) {
					LOG.info("Creating Reward: " + rewardEntry.getKey());
					rewardMaster = new RewardMaster();
					rewardMaster.setRewardType(rewardEntry.getKey());
					rewardMaster.setRewardValue(rewardEntry.getValue());
						
					try
					{
						rewardService.saveReward(rewardMaster);
					}
					catch(Exception e1)
					{
						LOG.info("Error while creating reward: "
								+ rewardEntry.getKey());
					}
					
					LOG.info("Successfully created reward: "
							+ rewardEntry.getKey());
				}
			}
		}
	}

}
