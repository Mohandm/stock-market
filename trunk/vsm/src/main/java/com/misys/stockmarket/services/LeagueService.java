package com.misys.stockmarket.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.dao.LeagueDAO;
import com.misys.stockmarket.domain.entity.LeagueMaster;
import com.misys.stockmarket.domain.entity.LeagueUser;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.enums.LEAGUE_ERR_CODES;
import com.misys.stockmarket.exception.DAOException;
import com.misys.stockmarket.exception.DBRecordNotFoundException;
import com.misys.stockmarket.exception.EmailNotFoundException;
import com.misys.stockmarket.exception.LeagueException;
import com.misys.stockmarket.exception.ServiceException;
import com.misys.stockmarket.exception.service.UserServiceException;
import com.misys.stockmarket.mbeans.MyLeagueFormBean;

@Service("leagueService")
@Repository
public class LeagueService {

	private static final Log LOG = LogFactory.getLog(LeagueService.class);

	@Inject
	private LeagueDAO leagueDAO;

	@Inject
	private UserService userService;

	@Transactional(rollbackFor = DAOException.class)
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

	@Transactional(rollbackFor = DAOException.class)
	public void addUserToDefaultLeague(UserMaster user) throws LeagueException {
		addUserToLeague(user, getDefaultLeague());
	}

	@Transactional(rollbackFor = DAOException.class)
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
		} catch (DAOException e) {
			throw new LeagueException(LEAGUE_ERR_CODES.UNKNOWN);
		}
	}

	public LeagueMaster getLeagueByName(String leagueName)
			throws LeagueException {
		try {
			return leagueDAO.findByName(leagueName);
		} catch (DBRecordNotFoundException e) {
			throw new LeagueException(LEAGUE_ERR_CODES.LEAGUE_NOT_FOUND);
		} catch (DAOException e) {
			throw new LeagueException(LEAGUE_ERR_CODES.UNKNOWN);
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

	public List<LeagueUser> getLeagueUsersByUserId(UserMaster user)
			throws LeagueException {
		try {
			return leagueDAO.findLeagueUserByUserId(user);
		} catch (DAOException e) {
			LOG.error(e);
			throw new LeagueException(e);
		}
	}

	public List<MyLeagueFormBean> getMyLeagues(long userId)
			throws LeagueException {
		List<MyLeagueFormBean> myLeagueFormBeanList = new ArrayList<MyLeagueFormBean>();
		try {
			UserMaster userMaster = userService.findById(userId);
			List<LeagueMaster> leagueMasterList = leagueDAO
					.findAllGameLeagues();
			List<LeagueUser> leagueUserList = leagueDAO
					.findLeagueUserByUserId(userMaster);
			List<Long> leagueIdList = new ArrayList<Long>();
			for (LeagueUser leagueUser : leagueUserList) {
				leagueIdList.add(leagueUser.getLeagueMaster().getLeagueId());
			}

			for (LeagueMaster leagueMaster : leagueMasterList) {
				MyLeagueFormBean myLeagueFormBean = new MyLeagueFormBean();
				myLeagueFormBean.setLeagueId(String.valueOf(leagueMaster
						.getLeagueId()));
				myLeagueFormBean.setName(leagueMaster.getName());
				List<LeagueUser> leagueUser = leagueDAO
						.findAllLeagueUsers(leagueMaster.getLeagueId());
				myLeagueFormBean.setPlayersCount(String.valueOf(leagueUser
						.size()));
				if (leagueIdList.contains(leagueMaster.getLeagueId())) {
					myLeagueFormBean.setLocked("false");
				} else {
					myLeagueFormBean.setLocked("true");
				}
				myLeagueFormBean.setStage(String.valueOf(leagueMaster
						.getStage()));
				myLeagueFormBeanList.add(myLeagueFormBean);
			}
		} catch (DAOException e) {
			LOG.error(e);
			throw new LeagueException(e);
		} catch (UserServiceException e) {
			LOG.error(e);
			throw new LeagueException(e);
		}
		return myLeagueFormBeanList;
	}
	
	public List<MyLeagueFormBean> getMyLeaguesIncludingGlobal(long userId)
			throws LeagueException {
		List<MyLeagueFormBean> myLeagueFormBeanList = new ArrayList<MyLeagueFormBean>();
		try {
			UserMaster userMaster = userService.findById(userId);
			List<LeagueMaster> leagueMasterList = leagueDAO.findAll(LeagueMaster.class);
			List<LeagueUser> leagueUserList = leagueDAO
					.findLeagueUserByUserId(userMaster);
			List<Long> leagueIdList = new ArrayList<Long>();
			for (LeagueUser leagueUser : leagueUserList) {
				leagueIdList.add(leagueUser.getLeagueMaster().getLeagueId());
			}

			for (LeagueMaster leagueMaster : leagueMasterList) {
				MyLeagueFormBean myLeagueFormBean = new MyLeagueFormBean();
				myLeagueFormBean.setLeagueId(String.valueOf(leagueMaster
						.getLeagueId()));
				myLeagueFormBean.setName(leagueMaster.getName());
				List<LeagueUser> leagueUser = leagueDAO
						.findAllLeagueUsers(leagueMaster.getLeagueId());
				myLeagueFormBean.setPlayersCount(String.valueOf(leagueUser
						.size()));
				if (leagueIdList.contains(leagueMaster.getLeagueId())) {
					myLeagueFormBean.setLocked("false");
				} else {
					myLeagueFormBean.setLocked("true");
				}
				myLeagueFormBean.setStage(String.valueOf(leagueMaster
						.getStage()));
				myLeagueFormBeanList.add(myLeagueFormBean);
			}
		} catch (DAOException e) {
			LOG.error(e);
			throw new LeagueException(e);
		} catch (UserServiceException e) {
			LOG.error(e);
			throw new LeagueException(e);
		}
		return myLeagueFormBeanList;
	}
}
