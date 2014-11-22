package vsm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.misys.stockmarket.domain.entity.AchievementCategory;
import com.misys.stockmarket.domain.entity.AchievementRule;
import com.misys.stockmarket.exception.service.AchievementServiceException;
import com.misys.stockmarket.services.AchievementsService;

public class InstallDefaultAchievements {

	private static final Log LOG = LogFactory
			.getLog(InstallDefaultAchievements.class);

	public static void main(String as[]) {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"META-INF\\spring\\installer-context.xml");
		AchievementsService achievementsService = (AchievementsService) applicationContext
				.getBean("achievementsService");

		// TODO: Test if the category already exists
		
		
		// Setup buy order category
		try {
			setupBuyOrder(achievementsService);
		} catch (AchievementServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Setup sell order category
		try {
			setupSellOrder(achievementsService);
		} catch (AchievementServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void setupBuyOrder(AchievementsService achievementsService)
			throws AchievementServiceException {
		AchievementCategory buyOrder = new AchievementCategory();
		buyOrder.setName("buyOrder");
		buyOrder.setAlias("Buy Order");
		achievementsService.addCategory(buyOrder);

		// Create levels for buy order
		AchievementRule buyRule = new AchievementRule();
		buyRule.setAchievementCategory(buyOrder);

		// Level 1
		buyRule.setLevel(1);
		buyRule.setQuantity(Long.valueOf(1));
		buyRule.setCoins(Long.valueOf(100));
		buyRule.setDescription("Execute 1 buy order");
		achievementsService.addRule(buyRule);

		// Level 2

		buyRule = new AchievementRule();
		buyRule.setAchievementCategory(buyOrder);
		buyRule.setLevel(2);
		buyRule.setQuantity(Long.valueOf(10));
		buyRule.setCoins(Long.valueOf(200));
		buyRule.setDescription("Execute 10 buy orders");
		achievementsService.addRule(buyRule);

		// Level 3
		buyRule = new AchievementRule();
		buyRule.setAchievementCategory(buyOrder);
		buyRule.setLevel(3);
		buyRule.setQuantity(Long.valueOf(100));
		buyRule.setCoins(Long.valueOf(400));
		buyRule.setDescription("Execute 100 buy orders");
		achievementsService.addRule(buyRule);

		// Level 4
		buyRule = new AchievementRule();
		buyRule.setAchievementCategory(buyOrder);
		buyRule.setLevel(4);
		buyRule.setQuantity(Long.valueOf(1000));
		buyRule.setCoins(Long.valueOf(800));
		buyRule.setDescription("Execute 1000 buy orders");
		achievementsService.addRule(buyRule);

		// Level 5
		buyRule = new AchievementRule();
		buyRule.setAchievementCategory(buyOrder);
		buyRule.setLevel(5);
		buyRule.setQuantity(Long.valueOf(10000));
		buyRule.setCoins(Long.valueOf(1600));
		buyRule.setDescription("Execute 10000 buy orders");
		achievementsService.addRule(buyRule);
	}

	private static void setupSellOrder(AchievementsService achievementsService)
			throws AchievementServiceException {
		AchievementCategory sellOrder = new AchievementCategory();
		sellOrder.setName("sellOrder");
		sellOrder.setAlias("Sell Order");
		achievementsService.addCategory(sellOrder);

		// Create levels for buy order
		AchievementRule sellRule = new AchievementRule();
		sellRule.setAchievementCategory(sellOrder);

		// Level 1
		sellRule.setLevel(1);
		sellRule.setQuantity(Long.valueOf(1));
		sellRule.setCoins(Long.valueOf(100));
		sellRule.setDescription("Execute 1 sell order");
		achievementsService.addRule(sellRule);

		// Level 2

		sellRule = new AchievementRule();
		sellRule.setAchievementCategory(sellOrder);
		sellRule.setLevel(2);
		sellRule.setQuantity(Long.valueOf(10));
		sellRule.setCoins(Long.valueOf(200));
		sellRule.setDescription("Execute 10 sell orders");
		achievementsService.addRule(sellRule);

		// Level 3
		sellRule = new AchievementRule();
		sellRule.setAchievementCategory(sellOrder);
		sellRule.setLevel(3);
		sellRule.setQuantity(Long.valueOf(100));
		sellRule.setCoins(Long.valueOf(400));
		sellRule.setDescription("Execute 100 sell orders");
		achievementsService.addRule(sellRule);

		// Level 4
		sellRule = new AchievementRule();
		sellRule.setAchievementCategory(sellOrder);
		sellRule.setLevel(4);
		sellRule.setQuantity(Long.valueOf(1000));
		sellRule.setCoins(Long.valueOf(800));
		sellRule.setDescription("Execute 1000 sell orders");
		achievementsService.addRule(sellRule);

		// Level 5
		sellRule = new AchievementRule();
		sellRule.setAchievementCategory(sellOrder);
		sellRule.setLevel(5);
		sellRule.setQuantity(Long.valueOf(10000));
		sellRule.setCoins(Long.valueOf(1600));
		sellRule.setDescription("Execute 10000 sell orders");
		achievementsService.addRule(sellRule);
	}

}
