package com.misys.stockmarket.services;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.dao.LeagueDAO;
import com.misys.stockmarket.domain.entity.LeagueMaster;
import com.misys.stockmarket.domain.entity.LeagueUser;
import com.misys.stockmarket.domain.entity.UserMaster;

@Service("leagueService")
@Repository
public class LeagueService {

	@Inject
	LeagueDAO leagueDAO;

	public void addUserToLeague(UserMaster user, LeagueMaster league) {
		LeagueUser leagueUser = new LeagueUser();
		leagueUser.setLeagueMaster(league);
		leagueUser.setUserMaster(user);
		leagueUser.setRemainingAmount(league.getTotalAmount());
		leagueDAO.persist(leagueUser);
	}

	public void addUserToDefaultLeague(UserMaster user) {
		addUserToLeague(user, getDefaultLeague());
	}

	public void addLeague(LeagueMaster league) {
		leagueDAO.persist(league);
	}

	public LeagueMaster getDefaultLeague() {
		return leagueDAO.findByName(IApplicationConstants.DEFAULT_LEAGUE_NAME);
	}
}
