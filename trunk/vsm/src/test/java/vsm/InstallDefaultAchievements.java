package vsm;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.misys.stockmarket.domain.entity.AchievementCategory;
import com.misys.stockmarket.domain.entity.AchievementRule;
import com.misys.stockmarket.exception.service.AchievementServiceException;
import com.misys.stockmarket.services.AchievementsService;

public class InstallDefaultAchievements {

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

		// Setup different companies category
		try {
			setupDifferentCompanies(achievementsService);
		} catch (AchievementServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Setup safe order category
		try {
			setupSafeOrder(achievementsService);
		} catch (AchievementServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Setup market order category
		try {
			setupMarketOrder(achievementsService);
		} catch (AchievementServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Setup followers category
		try {
			setupFollowers(achievementsService);
		} catch (AchievementServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Setup invites category
		try {
			setupInvites(achievementsService);
		} catch (AchievementServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Setup single day category
		try {
			setupSingleDayOrder(achievementsService);
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
		buyRule.setDescription("Complete 1 buy order");
		achievementsService.addRule(buyRule);

		// Level 2

		buyRule = new AchievementRule();
		buyRule.setAchievementCategory(buyOrder);
		buyRule.setLevel(2);
		buyRule.setQuantity(Long.valueOf(10));
		buyRule.setCoins(Long.valueOf(200));
		buyRule.setDescription("Complete 10 buy orders");
		achievementsService.addRule(buyRule);

		// Level 3
		buyRule = new AchievementRule();
		buyRule.setAchievementCategory(buyOrder);
		buyRule.setLevel(3);
		buyRule.setQuantity(Long.valueOf(100));
		buyRule.setCoins(Long.valueOf(500));
		buyRule.setDescription("Complete 100 buy orders");
		achievementsService.addRule(buyRule);

		// Level 4
		buyRule = new AchievementRule();
		buyRule.setAchievementCategory(buyOrder);
		buyRule.setLevel(4);
		buyRule.setQuantity(Long.valueOf(1000));
		buyRule.setCoins(Long.valueOf(1000));
		buyRule.setDescription("Complete 1000 buy orders");
		achievementsService.addRule(buyRule);

		// Level 5
		buyRule = new AchievementRule();
		buyRule.setAchievementCategory(buyOrder);
		buyRule.setLevel(5);
		buyRule.setQuantity(Long.valueOf(10000));
		buyRule.setCoins(Long.valueOf(2000));
		buyRule.setDescription("Complete 10000 buy orders");
		achievementsService.addRule(buyRule);
	}

	private static void setupDifferentCompanies(
			AchievementsService achievementsService)
			throws AchievementServiceException {
		AchievementCategory category = new AchievementCategory();
		category.setName("differentCompanies");
		category.setAlias("Diverse Portfolio");
		achievementsService.addCategory(category);

		// Create levels for buy order
		AchievementRule rule = new AchievementRule();
		rule.setAchievementCategory(category);

		// Level 1
		rule.setLevel(1);
		rule.setQuantity(Long.valueOf(5));
		rule.setCoins(Long.valueOf(100));
		rule.setDescription("Hold stocks of 5 different companies");
		achievementsService.addRule(rule);

		// Level 2

		rule = new AchievementRule();
		rule.setAchievementCategory(category);
		rule.setLevel(2);
		rule.setQuantity(Long.valueOf(10));
		rule.setCoins(Long.valueOf(200));
		rule.setDescription("Hold stocks of 10 different companies");
		achievementsService.addRule(rule);

		// Level 3
		rule = new AchievementRule();
		rule.setAchievementCategory(category);
		rule.setLevel(3);
		rule.setQuantity(Long.valueOf(20));
		rule.setCoins(Long.valueOf(500));
		rule.setDescription("Hold stocks of 20 different companies");
		achievementsService.addRule(rule);

		// Level 4
		rule = new AchievementRule();
		rule.setAchievementCategory(category);
		rule.setLevel(4);
		rule.setQuantity(Long.valueOf(50));
		rule.setCoins(Long.valueOf(1000));
		rule.setDescription("Hold stocks of 50 different companies");
		achievementsService.addRule(rule);

		// Level 5
		rule = new AchievementRule();
		rule.setAchievementCategory(category);
		rule.setLevel(5);
		rule.setQuantity(Long.valueOf(100));
		rule.setCoins(Long.valueOf(2500));
		rule.setDescription("Hold stocks of 100 different companies");
		achievementsService.addRule(rule);
	}

	private static void setupInvites(AchievementsService achievementsService)
			throws AchievementServiceException {
		AchievementCategory category = new AchievementCategory();
		category.setName("invites");
		category.setAlias("Invites");
		achievementsService.addCategory(category);

		// Create levels for buy order
		AchievementRule rule = new AchievementRule();
		rule.setAchievementCategory(category);

		// Level 1
		rule.setLevel(1);
		rule.setQuantity(Long.valueOf(1));
		rule.setCoins(Long.valueOf(100));
		rule.setDescription("Invite 1 friend");
		achievementsService.addRule(rule);

		// Level 2

		rule = new AchievementRule();
		rule.setAchievementCategory(category);
		rule.setLevel(2);
		rule.setQuantity(Long.valueOf(5));
		rule.setCoins(Long.valueOf(200));
		rule.setDescription("Invite 5 friends");
		achievementsService.addRule(rule);

		// Level 3
		rule = new AchievementRule();
		rule.setAchievementCategory(category);
		rule.setLevel(3);
		rule.setQuantity(Long.valueOf(20));
		rule.setCoins(Long.valueOf(500));
		rule.setDescription("Invite 20 friends");
		achievementsService.addRule(rule);

		// Level 4
		rule = new AchievementRule();
		rule.setAchievementCategory(category);
		rule.setLevel(4);
		rule.setQuantity(Long.valueOf(50));
		rule.setCoins(Long.valueOf(1000));
		rule.setDescription("Invite 50 friends");
		achievementsService.addRule(rule);

		// Level 5
		rule = new AchievementRule();
		rule.setAchievementCategory(category);
		rule.setLevel(5);
		rule.setQuantity(Long.valueOf(100));
		rule.setCoins(Long.valueOf(2000));
		rule.setDescription("Invite 100 friends");
		achievementsService.addRule(rule);
	}

	private static void setupFollowers(AchievementsService achievementsService)
			throws AchievementServiceException {
		AchievementCategory category = new AchievementCategory();
		category.setName("followers");
		category.setAlias("Followers");
		achievementsService.addCategory(category);

		// Create levels for buy order
		AchievementRule rule = new AchievementRule();
		rule.setAchievementCategory(category);

		// Level 1
		rule.setLevel(1);
		rule.setQuantity(Long.valueOf(1));
		rule.setCoins(Long.valueOf(100));
		rule.setDescription("Have 1 follower");
		achievementsService.addRule(rule);

		// Level 2

		rule = new AchievementRule();
		rule.setAchievementCategory(category);
		rule.setLevel(2);
		rule.setQuantity(Long.valueOf(5));
		rule.setCoins(Long.valueOf(200));
		rule.setDescription("Have 5 followers");
		achievementsService.addRule(rule);

		// Level 3
		rule = new AchievementRule();
		rule.setAchievementCategory(category);
		rule.setLevel(3);
		rule.setQuantity(Long.valueOf(20));
		rule.setCoins(Long.valueOf(500));
		rule.setDescription("Have 20 followers");
		achievementsService.addRule(rule);

		// Level 4
		rule = new AchievementRule();
		rule.setAchievementCategory(category);
		rule.setLevel(4);
		rule.setQuantity(Long.valueOf(50));
		rule.setCoins(Long.valueOf(1000));
		rule.setDescription("Have 50 followers");
		achievementsService.addRule(rule);

		// Level 5
		rule = new AchievementRule();
		rule.setAchievementCategory(category);
		rule.setLevel(5);
		rule.setQuantity(Long.valueOf(100));
		rule.setCoins(Long.valueOf(2000));
		rule.setDescription("Have 100 followers");
		achievementsService.addRule(rule);
	}

	private static void setupSingleDayOrder(
			AchievementsService achievementsService)
			throws AchievementServiceException {
		AchievementCategory category = new AchievementCategory();
		category.setName("singleDayOrder");
		category.setAlias("Busy Day");
		achievementsService.addCategory(category);

		// Create levels for buy order
		AchievementRule rule = new AchievementRule();
		rule.setAchievementCategory(category);

		// Level 1
		rule.setLevel(1);
		rule.setQuantity(Long.valueOf(10));
		rule.setCoins(Long.valueOf(600));
		rule.setDescription("Complete 10 orders in a single day");
		achievementsService.addRule(rule);

		// Level 2

		rule = new AchievementRule();
		rule.setAchievementCategory(category);
		rule.setLevel(2);
		rule.setQuantity(Long.valueOf(25));
		rule.setCoins(Long.valueOf(1000));
		rule.setDescription("Complete 25 orders in a single day");
		achievementsService.addRule(rule);

		// Level 3
		rule = new AchievementRule();
		rule.setAchievementCategory(category);
		rule.setLevel(3);
		rule.setQuantity(Long.valueOf(50));
		rule.setCoins(Long.valueOf(1500));
		rule.setDescription("Complete 50 orders in a single day");
		achievementsService.addRule(rule);

		// Level 4
		rule = new AchievementRule();
		rule.setAchievementCategory(category);
		rule.setLevel(4);
		rule.setQuantity(Long.valueOf(100));
		rule.setCoins(Long.valueOf(2500));
		rule.setDescription("Complete 100 orders in a single day");
		achievementsService.addRule(rule);

		// Level 5
		rule = new AchievementRule();
		rule.setAchievementCategory(category);
		rule.setLevel(5);
		rule.setQuantity(Long.valueOf(500));
		rule.setCoins(Long.valueOf(4000));
		rule.setDescription("Complete 500 orders in a single day");
		achievementsService.addRule(rule);
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
		sellRule.setDescription("Complete 1 sell order");
		achievementsService.addRule(sellRule);

		// Level 2

		sellRule = new AchievementRule();
		sellRule.setAchievementCategory(sellOrder);
		sellRule.setLevel(2);
		sellRule.setQuantity(Long.valueOf(10));
		sellRule.setCoins(Long.valueOf(200));
		sellRule.setDescription("Complete 10 sell orders");
		achievementsService.addRule(sellRule);

		// Level 3
		sellRule = new AchievementRule();
		sellRule.setAchievementCategory(sellOrder);
		sellRule.setLevel(3);
		sellRule.setQuantity(Long.valueOf(100));
		sellRule.setCoins(Long.valueOf(500));
		sellRule.setDescription("Complete 100 sell orders");
		achievementsService.addRule(sellRule);

		// Level 4
		sellRule = new AchievementRule();
		sellRule.setAchievementCategory(sellOrder);
		sellRule.setLevel(4);
		sellRule.setQuantity(Long.valueOf(1000));
		sellRule.setCoins(Long.valueOf(1000));
		sellRule.setDescription("Complete 1000 sell orders");
		achievementsService.addRule(sellRule);

		// Level 5
		sellRule = new AchievementRule();
		sellRule.setAchievementCategory(sellOrder);
		sellRule.setLevel(5);
		sellRule.setQuantity(Long.valueOf(10000));
		sellRule.setCoins(Long.valueOf(2000));
		sellRule.setDescription("Complete 10000 sell orders");
		achievementsService.addRule(sellRule);
	}

	private static void setupSafeOrder(AchievementsService achievementsService)
			throws AchievementServiceException {
		AchievementCategory category = new AchievementCategory();
		category.setName("safeOrders");
		category.setAlias("Safe Orders");
		achievementsService.addCategory(category);

		// Create levels for buy order
		AchievementRule rule = new AchievementRule();
		rule.setAchievementCategory(category);

		// Level 1
		rule.setLevel(1);
		rule.setQuantity(Long.valueOf(10));
		rule.setCoins(Long.valueOf(200));
		rule.setDescription("Complete 10 limit/stoploss orders");
		achievementsService.addRule(rule);

		// Level 2

		rule = new AchievementRule();
		rule.setAchievementCategory(category);
		rule.setLevel(2);
		rule.setQuantity(Long.valueOf(50));
		rule.setCoins(Long.valueOf(500));
		rule.setDescription("Complete 50 limit/stoploss orders");
		achievementsService.addRule(rule);

		// Level 3
		rule = new AchievementRule();
		rule.setAchievementCategory(category);
		rule.setLevel(3);
		rule.setQuantity(Long.valueOf(100));
		rule.setCoins(Long.valueOf(1000));
		rule.setDescription("Complete 100 limit/stoploss orders");
		achievementsService.addRule(rule);

		// Level 4
		rule = new AchievementRule();
		rule.setAchievementCategory(category);
		rule.setLevel(4);
		rule.setQuantity(Long.valueOf(1000));
		rule.setCoins(Long.valueOf(1500));
		rule.setDescription("Complete 1000 limit/stoploss orders");
		achievementsService.addRule(rule);

		// Level 5
		rule = new AchievementRule();
		rule.setAchievementCategory(category);
		rule.setLevel(5);
		rule.setQuantity(Long.valueOf(10000));
		rule.setCoins(Long.valueOf(2000));
		rule.setDescription("Complete 10000 safe(limit/stoploss) orders");
		achievementsService.addRule(rule);
	}

	private static void setupMarketOrder(AchievementsService achievementsService)
			throws AchievementServiceException {
		AchievementCategory category = new AchievementCategory();
		category.setName("marketOrders");
		category.setAlias("Risky Orders");
		achievementsService.addCategory(category);

		// Create levels for buy order
		AchievementRule rule = new AchievementRule();
		rule.setAchievementCategory(category);

		// Level 1
		rule.setLevel(1);
		rule.setQuantity(Long.valueOf(10));
		rule.setCoins(Long.valueOf(200));
		rule.setDescription("Complete 10 orders at market price");
		achievementsService.addRule(rule);

		// Level 2

		rule = new AchievementRule();
		rule.setAchievementCategory(category);
		rule.setLevel(2);
		rule.setQuantity(Long.valueOf(50));
		rule.setCoins(Long.valueOf(500));
		rule.setDescription("Complete 50 orders at market price");
		achievementsService.addRule(rule);

		// Level 3
		rule = new AchievementRule();
		rule.setAchievementCategory(category);
		rule.setLevel(3);
		rule.setQuantity(Long.valueOf(100));
		rule.setCoins(Long.valueOf(1000));
		rule.setDescription("Complete 100 orders at market price");
		achievementsService.addRule(rule);

		// Level 4
		rule = new AchievementRule();
		rule.setAchievementCategory(category);
		rule.setLevel(4);
		rule.setQuantity(Long.valueOf(1000));
		rule.setCoins(Long.valueOf(1500));
		rule.setDescription("Complete 1000 orders at market price");
		achievementsService.addRule(rule);

		// Level 5
		rule = new AchievementRule();
		rule.setAchievementCategory(category);
		rule.setLevel(5);
		rule.setQuantity(Long.valueOf(10000));
		rule.setCoins(Long.valueOf(2500));
		rule.setDescription("Complete 10000 orders at market price");
		achievementsService.addRule(rule);
	}
}
