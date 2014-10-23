package com.misys.stockmarket.services;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.dao.LeagueDAO;
import com.misys.stockmarket.domain.entity.LeagueMaster;
import com.misys.stockmarket.domain.entity.LeagueUser;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.enums.LEAGUE_ERR_CODES;
import com.misys.stockmarket.exception.DAOException;
import com.misys.stockmarket.exception.DBRecordNotFoundException;
import com.misys.stockmarket.exception.LeagueException;

@Service("leagueService")
@Repository
public class LeagueService {

	private static final Log LOG = LogFactory.getLog(LeagueService.class);

	@Inject
	private LeagueDAO leagueDAO;

	public void addUserToLeague(UserMaster user, LeagueMaster league)
			throws LeagueException {
		try {
			LeagueUser leagueUser = new LeagueUser();
			leagueUser.setLeagueMaster(league);
			leagueUser.setUserMaster(user);
			leagueUser.setRemainingAmount(league.getTotalAmount());
			leagueDAO.persist(leagueUser);
		} catch (DAOException e) {
			LOG.error(e);
			throw new LeagueException(e);
		}
	}

	public void addUserToDefaultLeague(UserMaster user) throws LeagueException {
		addUserToLeague(user, getDefaultLeague());
	}

	public void addLeague(LeagueMaster league) throws LeagueException {
		try {
			leagueDAO.persist(league);
		} catch (DAOException e) {
			LOG.error(e);
			throw new LeagueException(e);
		}
	}

	public LeagueMaster getDefaultLeague() throws LeagueException {
		try {
			return leagueDAO
					.findByName(IApplicationConstants.DEFAULT_LEAGUE_NAME);
		} catch (DBRecordNotFoundException e) {
			throw new LeagueException(LEAGUE_ERR_CODES.LEAGUE_NOT_FOUND);
		}
	}

	public LeagueUser getLeagueUser(long leagueId, long userId)
			throws LeagueException {
		try {
			return leagueDAO.findLeagueUser(leagueId, userId);
		} catch (DBRecordNotFoundException e) {
			throw new LeagueException(LEAGUE_ERR_CODES.LEAGUE_USER_NOT_FOUND);
		}
	}

	public LeagueUser getLeagueUserById(long leagueUserId)
			throws LeagueException {
		try {
			return leagueDAO.findLeagueById(leagueUserId);
		} catch (DBRecordNotFoundException e) {
			throw new LeagueException(LEAGUE_ERR_CODES.LEAGUE_USER_NOT_FOUND);
		}
	}
}
