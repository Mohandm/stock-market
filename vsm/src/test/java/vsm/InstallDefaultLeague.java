package vsm;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.domain.entity.LeagueMaster;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.enums.LEAGUE_ERR_CODES;
import com.misys.stockmarket.exception.LeagueException;
import com.misys.stockmarket.exception.service.UserServiceException;
import com.misys.stockmarket.services.LeagueService;
import com.misys.stockmarket.services.UserService;

public class InstallDefaultLeague {

	private static final Log LOG = LogFactory
			.getLog(InstallDefaultLeague.class);

	public static void main(String as[]) throws LeagueException {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"META-INF\\spring\\applicationContext.xml");
		LeagueService leagueService = (LeagueService) applicationContext
				.getBean("leagueService");
		UserService userService = (UserService) applicationContext
				.getBean("userService");

		LeagueMaster defaultLeague = null;
		try {
			defaultLeague = leagueService.getDefaultLeague();
		} catch (LeagueException e) {
			if (e.getErrorCode().compareTo(LEAGUE_ERR_CODES.LEAGUE_NOT_FOUND) == 0) {
				LOG.info("Creating default group");
				defaultLeague = new LeagueMaster();
				defaultLeague
						.setName(IApplicationConstants.DEFAULT_LEAGUE_NAME);
				defaultLeague
						.setDuration(IApplicationConstants.LEAGUE_DURATION_WEEKLY);
				defaultLeague
						.setStatus(IApplicationConstants.LEAGUE_STATUS_ACTIVE);
				defaultLeague
						.setTotalAmount(IApplicationConstants.DEFAULT_LEAGUE_START_AMOUNT);
				defaultLeague
						.setQualifyingAmount(IApplicationConstants.DEFAULT_LEAGUE_QUALIFYING_AMOUNT);
				defaultLeague.setStartDate(new Date());
				leagueService.addLeague(defaultLeague);
				LOG.info("Successfully created default group");
			}
		}
		LOG.info("Add all current users to the default group");
		try {
			List<UserMaster> allUsers = userService.findAll();
			UserMaster user = null;
			for (UserMaster userMaster : allUsers) {
				try {
					leagueService.getLeagueUser(defaultLeague.getLeagueId(),
							userMaster.getUserId());
				} catch (LeagueException e) {
					if (e.getErrorCode().compareTo(
							LEAGUE_ERR_CODES.LEAGUE_USER_NOT_FOUND) == 0) {
						leagueService.addUserToLeague(user, defaultLeague);
					}
				}
			}
		} catch (UserServiceException e) {
			LOG.error(e);
		}
		LOG.info("Completed adding all users to the default group");
	}
}
