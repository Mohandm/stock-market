package vsm;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

		Map<String, BigDecimal> leaguesMap = new HashMap<String, BigDecimal>();
		leaguesMap.put(IApplicationConstants.DEFAULT_LEAGUE_NAME,
				new BigDecimal(0));
		leaguesMap.put(IApplicationConstants.PREMIER_LEAGUE_NAME,
				new BigDecimal(1));
		leaguesMap.put(IApplicationConstants.CHAMPIONS_LEAGUE_NAME,
				new BigDecimal(2));
		leaguesMap.put(IApplicationConstants.LEGENDS_LEAGUE_NAME,
				new BigDecimal(3));

		for (Map.Entry<String, BigDecimal> leagueEntry : leaguesMap.entrySet()) {
			LeagueMaster leaugeMaster = null;
			try {
				leaugeMaster = leagueService.getLeagueByName(leagueEntry
						.getKey());
			} catch (LeagueException e) {
				if (e.getErrorCode().compareTo(
						LEAGUE_ERR_CODES.LEAGUE_NOT_FOUND) == 0) {
					LOG.info("Creating League: " + leagueEntry.getKey());
					leaugeMaster = new LeagueMaster();
					leaugeMaster.setName(leagueEntry.getKey());
					leaugeMaster
							.setDuration(IApplicationConstants.LEAGUE_DURATION_WEEKLY);
					leaugeMaster
							.setStatus(IApplicationConstants.LEAGUE_STATUS_ACTIVE);
					leaugeMaster
							.setTotalAmount(IApplicationConstants.DEFAULT_LEAGUE_START_AMOUNT);
					leaugeMaster
							.setQualifyingAmount(IApplicationConstants.DEFAULT_LEAGUE_QUALIFYING_AMOUNT);
					leaugeMaster.setStartDate(new Date());
					leaugeMaster.setStage(leagueEntry.getValue());
					leagueService.addLeague(leaugeMaster);
					LOG.info("Successfully created League: "
							+ leagueEntry.getKey());
				}
			}
		}

		LOG.info("Add all current users to the default group");
		try {
			List<UserMaster> allUsers = userService.findAll();
			LeagueMaster defaultLeague = leagueService.getDefaultLeague();
			for (UserMaster userMaster : allUsers) {
				try {
					leagueService.getLeagueUser(defaultLeague.getLeagueId(),
							userMaster.getUserId());
				} catch (LeagueException e) {
					if (e.getErrorCode().compareTo(
							LEAGUE_ERR_CODES.LEAGUE_USER_NOT_FOUND) == 0) {
						leagueService
								.addUserToLeague(userMaster, defaultLeague);
					}
				}
			}
		} catch (UserServiceException e) {
			LOG.error(e);
		}
		LOG.info("Completed adding all users to the default group");
	}
}
