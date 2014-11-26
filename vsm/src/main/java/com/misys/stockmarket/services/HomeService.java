package com.misys.stockmarket.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.domain.entity.LeagueMaster;
import com.misys.stockmarket.domain.entity.LeagueUser;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.exception.EmailNotFoundException;
import com.misys.stockmarket.exception.LeagueException;
import com.misys.stockmarket.exception.service.AchievementExecutionServiceException;
import com.misys.stockmarket.exception.service.PortfolioServiceException;
import com.misys.stockmarket.mbeans.LeaguePlayerFormBean;
import com.misys.stockmarket.mbeans.MyFollowingFormBean;
import com.misys.stockmarket.mbeans.MyPortfolioFormBean;
import com.misys.stockmarket.mbeans.UserHomeBean;
import com.misys.stockmarket.mbeans.UserLeagueBean;

@Service("homeService")
@Repository
public class HomeService {

	@Inject
	private AchievementExecutionService achievementExecutionService;

	@Inject
	private AchievementsService achievementsService;

	@Inject
	private UserService userService;

	@Inject
	private LeagueService leagueService;

	@Inject
	private PortfolioService portfolioService;

	@PreAuthorize("hasRole('ROLE_USER')")
	public UserHomeBean getUserSnapshot() {
		UserHomeBean bean = new UserHomeBean();
		UserMaster user = null;
		try {
			user = userService.getLoggedInUser();
		} catch (EmailNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bean.setEmail(user.getEmail());
		bean.setFirstName(user.getFirstName());
		bean.setLastName(user.getLastName());
		if (user.getCoins()!=null) {
			bean.setCoins(user.getCoins());
		}
		int completedAchievements = achievementsService
				.getCompletedAchievements(user).size();
		int totalAchievements = achievementsService.findAllAchievementRule()
				.size();
		bean.setCompletedAchievements(completedAchievements);
		bean.setTotalAchievements(totalAchievements);
		bean.setPendingAchievements(totalAchievements - completedAchievements);

		try {
			bean.setFollowers(achievementExecutionService
					.findAllFollowers(user).size());
		} catch (AchievementExecutionServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO: Find this number

		try {
			bean.setAcceptedInvites(achievementExecutionService
					.findAllAcceptedInvites(user).size());
		} catch (AchievementExecutionServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Get user leagues
		List<UserLeagueBean> leagues = new ArrayList<UserLeagueBean>();

		List<LeagueUser> userLeagues = null;
		try {
			userLeagues = leagueService.getLeagueUsersByUserId(user);
		} catch (LeagueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<MyFollowingFormBean> myFollowingFormBeanList = new ArrayList<MyFollowingFormBean>();
		for (LeagueUser leagueUser : userLeagues) {
			try {
				myFollowingFormBeanList.addAll(leagueService.getMyFollowing(
						user, leagueUser.getLeagueMaster()));
			} catch (LeagueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		bean.setFollowing(myFollowingFormBeanList.size());

		UserLeagueBean leagueBean = null;
		MyPortfolioFormBean portfolioBean = null;
		LeagueMaster leagueMaster = null;
		List<LeaguePlayerFormBean> leaguePlayers = null;
		for (LeagueUser leagueUser : userLeagues) {
			leagueBean = new UserLeagueBean();
			leagueMaster = leagueUser.getLeagueMaster();

			// Ignore default group
			if (IApplicationConstants.DEFAULT_LEAGUE_NAME.equals(leagueMaster
					.getName())) {
				continue;
			}
			long leagueId = leagueMaster.getLeagueId();
			try {
				portfolioBean = portfolioService.getMyPortfolio(leagueId,
						user.getUserId());
			} catch (PortfolioServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			leagueBean.setLeagueId(leagueId);
			leagueBean.setName(leagueMaster.getName());
			leagueBean.setPortfolioValue(portfolioBean.getPortfolioValue());
			leagueBean.setRemainingBalance(portfolioBean.getRemainingBalance());
			leagueBean.setTotalValue(portfolioBean.getTotalValue());

			try {
				leaguePlayers = leagueService.getLeaguePlayersBasedOnRanking(
						leagueId, -1);
			} catch (LeagueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			leagueBean.setTotalPlayers(leaguePlayers.size());
			for (LeaguePlayerFormBean leaguePlayerFormBean : leaguePlayers) {
				if (leaguePlayerFormBean.getLeagueUserId().equals(
						String.valueOf(leagueUser.getLeagueUserId()))) {
					leagueBean.setRank(Long.valueOf(leaguePlayerFormBean
							.getRank()));
				}
			}
			leagues.add(leagueBean);
		}
		bean.setLeagues(leagues);
		return bean;
	}
}
