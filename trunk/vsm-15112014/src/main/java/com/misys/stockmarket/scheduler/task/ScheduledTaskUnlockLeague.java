package com.misys.stockmarket.scheduler.task;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.misys.stockmarket.exception.LeagueException;
import com.misys.stockmarket.services.LeagueService;


@Component
@EnableScheduling
public class ScheduledTaskUnlockLeague {
	private static final Log LOG = LogFactory
			.getLog(ScheduledTaskUnlockLeague.class);
	
	@Inject
	LeagueService leagueService;
	
	@Scheduled(fixedRateString = "${scheduler.task.UnlockLeague.frequency}")
	public void unlockLeaguesForUsers() {
		LOG.info("SCHEDULED TASK: UNLOCK LEAGUES FOR USERS STARTED");
		try {
			leagueService.unlockUsers();
		} catch (LeagueException e) {
			LOG.error(
					"Technical Error while unlocking users for Leagues: ",
					e);
		}
		LOG.info("SCHEDULED TASK: UNLOCK LEAGUES FOR USERS COMPLETED");
	}
}
