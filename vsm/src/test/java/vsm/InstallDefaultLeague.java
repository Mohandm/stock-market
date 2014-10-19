package vsm;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.domain.entity.LeagueMaster;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.services.LeagueService;
import com.misys.stockmarket.services.UserService;

public class InstallDefaultLeague {

	private static final Log LOG = LogFactory
			.getLog(InstallDefaultLeague.class);

	public static void main(String as[]) {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"META-INF\\spring\\applicationContext.xml");
		LeagueService leagueService = (LeagueService) applicationContext
				.getBean("leagueService");
		UserService userService = (UserService) applicationContext
				.getBean("userService");

		LOG.info("Creating default group");
		LeagueMaster defaultLeague = new LeagueMaster();
		defaultLeague.setName(IApplicationConstants.DEFAULT_LEAGUE_NAME);
		defaultLeague.setDuration(IApplicationConstants.LEAGUE_DURATION_WEEKLY);
		defaultLeague.setStatus(IApplicationConstants.LEAGUE_STATUS_ACTIVE);
		defaultLeague
				.setTotalAmount(IApplicationConstants.DEFAULT_LEAGUE_START_AMOUNT);
		defaultLeague
				.setQualifyingAmount(IApplicationConstants.DEFAULT_LEAGUE_QUALIFYING_AMOUNT);
		defaultLeague.setStartDate(new Date());
		leagueService.addLeague(defaultLeague);
		LOG.info("Successfully created default group");

		LOG.info("Add all current users to the default group");
		List<UserMaster> allUsers = userService.findAll();
		UserMaster user = null;
		for (Iterator<UserMaster> iterator = allUsers.iterator(); iterator
				.hasNext();) {
			user = iterator.next();
			// Add user to default league
			leagueService.addUserToLeague(user, defaultLeague);
		}
		LOG.info("Completed adding all users to the default group");
	}
}
