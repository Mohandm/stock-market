package com.misys.stockmarket.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.dao.LeagueDAO;
import com.misys.stockmarket.domain.entity.FollowerMaster;
import com.misys.stockmarket.domain.entity.LeagueMaster;
import com.misys.stockmarket.domain.entity.LeagueUser;
import com.misys.stockmarket.domain.entity.OrderMaster;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.enums.LEAGUE_ERR_CODES;
import com.misys.stockmarket.exception.DAOException;
import com.misys.stockmarket.exception.DBRecordNotFoundException;
import com.misys.stockmarket.exception.LeagueException;
import com.misys.stockmarket.exception.service.OrderServiceException;
import com.misys.stockmarket.exception.service.PortfolioServiceException;
import com.misys.stockmarket.exception.service.UserServiceException;
import com.misys.stockmarket.mbeans.LeaderBoardFormBean;
import com.misys.stockmarket.mbeans.LeaguePlayerFormBean;
import com.misys.stockmarket.mbeans.MyFollowersFormBean;
import com.misys.stockmarket.mbeans.MyFollowingFormBean;
import com.misys.stockmarket.mbeans.MyLeagueFormBean;
import com.misys.stockmarket.mbeans.MyPortfolioFormBean;
import com.misys.stockmarket.mbeans.OrderHistoryFormBean;
import com.misys.stockmarket.mbeans.PlayersRecentTradesFormBean;
import com.misys.stockmarket.utility.DateUtils;
import com.misys.stockmarket.utility.PropertiesUtil;

@Service("leagueService")
@Repository
public class LeagueService {

	private static final Log LOG = LogFactory.getLog(LeagueService.class);

	@Inject
	private LeagueDAO leagueDAO;

	@Inject
	private UserService userService;

	@Inject
	private PortfolioService portfolioService;
	
	@Inject
	private OrderService orderService;
	
	@Transactional(rollbackFor = DAOException.class)
	public void unlockUsers()
			throws LeagueException {
		try {
			List<LeagueMaster> leagues = new ArrayList<LeagueMaster>();
			leagues.add(getLeagueByName(IApplicationConstants.PREMIER_LEAGUE_NAME));
			leagues.add(getLeagueByName(IApplicationConstants.CHAMPIONS_LEAGUE_NAME));
			for(LeagueMaster league : leagues)
			{
				List<LeagueUser> leagueUserList = leagueDAO.findAllLeagueUsers(league.getLeagueId());
				for (LeagueUser leagueUser : leagueUserList) {
					if(IApplicationConstants.PREMIER_LEAGUE_NAME.equalsIgnoreCase(league.getName()))
					{
						if(checkLeagueUser(leagueUser.getUserMaster(), getLeagueByName(IApplicationConstants.CHAMPIONS_LEAGUE_NAME)))
						{
							break;
						}
					}
					else
					{
						if(checkLeagueUser(leagueUser.getUserMaster(), getLeagueByName(IApplicationConstants.LEGENDS_LEAGUE_NAME)))
						{
							break;
						}
					}
					MyPortfolioFormBean myPortfolioFormBean = portfolioService
							.getMyPortfolio(league.getLeagueId(), leagueUser.getUserMaster()
									.getUserId());
					
					BigDecimal totalValue = new BigDecimal(0);
					if (myPortfolioFormBean.getTotalValue() != null) {
						totalValue = totalValue.add(new BigDecimal(
								myPortfolioFormBean.getTotalValue()));
					}
					
					BigDecimal stage = league.getStage().add(new BigDecimal(1));
					String minimumQualifyingValueStr = PropertiesUtil
							.getProperty("stage.minimum.qualifying.amount."
									+ stage.toPlainString());
					BigDecimal minimumQualifyingValue = new BigDecimal(0);
					if (minimumQualifyingValueStr != null) {
						minimumQualifyingValue = minimumQualifyingValue
								.add(new BigDecimal(minimumQualifyingValueStr));
					}
					if (totalValue.compareTo(new BigDecimal(0)) > 0
							&& totalValue.compareTo(minimumQualifyingValue) >= 0) {
						if(IApplicationConstants.PREMIER_LEAGUE_NAME.equalsIgnoreCase(league.getName()))
						{
							addUserToLeague(leagueUser.getUserMaster(), getLeagueByName(IApplicationConstants.CHAMPIONS_LEAGUE_NAME));
						}
						else
						{
							addUserToLeague(leagueUser.getUserMaster(), getLeagueByName(IApplicationConstants.LEGENDS_LEAGUE_NAME));
						}
					}
				}
			}
		} catch (DAOException e) {
			LOG.error(e);
			throw new LeagueException(e);
		} catch (PortfolioServiceException e) {
			LOG.error(e);
			throw new LeagueException(e);
		}
	}
	
	public boolean checkLeagueUser(UserMaster user, LeagueMaster league)
			throws LeagueException {
		try {
			leagueDAO.findLeagueUser(league.getLeagueId(), user.getUserId());
			return true;
		} catch (DBRecordNotFoundException e) {
			return false;
		}
	}
	

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
	public void followPlayer(UserMaster user, UserMaster playerMaster, LeagueMaster league)
			throws LeagueException {
		try {
			FollowerMaster followMaster = new FollowerMaster();
			followMaster.setFollowerUserMaster(user);
			followMaster.setLeagueMaster(league);
			followMaster.setPlayerUserId(playerMaster.getUserId());
			followMaster.setStatus(IApplicationConstants.FOLLOWER_STATUS_PENDING);
			leagueDAO.persist(followMaster);
		} catch (DAOException e) {
			LOG.error(e);
			throw new LeagueException(e);
		}
	}
	
	@Transactional(rollbackFor = DAOException.class)
	public void followerStatuschange(UserMaster user, UserMaster playerMaster, LeagueMaster league, String oldStatus, String newStatus)
			throws LeagueException {
		try {
			FollowerMaster followMaster = leagueDAO.findFollowerMaster(user.getUserId(),playerMaster.getUserId(),league.getLeagueId(),oldStatus);
			followMaster.setStatus(newStatus);
			leagueDAO.update(followMaster);
		} catch (DAOException e) {
			LOG.error(e);
			throw new LeagueException(e);
		}
	}
	
	public boolean checkFollowRecordExists(UserMaster user, UserMaster playerMaster, LeagueMaster league) throws LeagueException {
		try {
			 leagueDAO.findFollowerMaster(user.getUserId(),playerMaster.getUserId(),league.getLeagueId(),IApplicationConstants.FOLLOWER_STATUS_ACCEPTED, IApplicationConstants.FOLLOWER_STATUS_PENDING);
			 return true;
		} catch (DBRecordNotFoundException e) {
			return false;
		} 
	}
	
	public boolean checkFollowerAccess(UserMaster user, UserMaster playerMaster, LeagueMaster league) throws LeagueException {
		try {
			 leagueDAO.findFollowerMaster(user.getUserId(),playerMaster.getUserId(),league.getLeagueId(), IApplicationConstants.FOLLOWER_STATUS_ACCEPTED);
			 return true;
		} catch (DBRecordNotFoundException e) {
			return false;
		} 
	}

	@Transactional(rollbackFor = DAOException.class)
	public void addUserToDefaultLeague(UserMaster user) throws LeagueException {
		addUserToLeague(user, getDefaultLeague());
	}
	
	@Transactional(rollbackFor = DAOException.class)
	public void addUserToStarterLeague(UserMaster user) throws LeagueException {
		addUserToLeague(user, getStarterLeague());
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
	
	
	public List<LeaderBoardFormBean> getLeaderBoard() throws LeagueException {
		try {
			List<LeagueMaster> leagueMasterList = leagueDAO
					.findAllGameLeagues();
			List<LeaderBoardFormBean> beanList = new ArrayList<LeaderBoardFormBean>();
			for (LeagueMaster leagueMaster : leagueMasterList) {
				LeaderBoardFormBean leaderBoardFormBean = new LeaderBoardFormBean();
				leaderBoardFormBean.setLeagueId(String.valueOf(leagueMaster
						.getLeagueId()));
				leaderBoardFormBean.setName(leagueMaster.getName());
				List<LeagueUser> leagueUser = leagueDAO
						.findAllLeagueUsers(leagueMaster.getLeagueId());
				leaderBoardFormBean.setPlayersCount(String.valueOf(leagueUser
						.size()));
				leaderBoardFormBean.setStage(String.valueOf(leagueMaster
						.getStage()));
				leaderBoardFormBean.setPlayers(getLeaguePlayersBasedOnRanking(leagueMaster.getLeagueId(),12));
				beanList.add(leaderBoardFormBean);
			}
			return beanList;
		} catch (DBRecordNotFoundException e) {
			throw new LeagueException(LEAGUE_ERR_CODES.LEAGUE_NOT_FOUND);
		} catch (DAOException e) {
			throw new LeagueException(LEAGUE_ERR_CODES.UNKNOWN);
		}
	}
	
	public LeagueMaster getStarterLeague() throws LeagueException {
		try {
			return leagueDAO
					.findByName(IApplicationConstants.PREMIER_LEAGUE_NAME);
		} catch (DBRecordNotFoundException e) {
			throw new LeagueException(LEAGUE_ERR_CODES.LEAGUE_NOT_FOUND);
		} catch (DAOException e) {
			throw new LeagueException(LEAGUE_ERR_CODES.UNKNOWN);
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
	
	public LeagueMaster getLeagueById(long id)
			throws LeagueException {
		try {
			return leagueDAO.findById(id);
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
	
	public boolean getLeagueUserAccessCheck(long leagueId, long userId)
			throws LeagueException {
		try {
			leagueDAO.findLeagueUser(leagueId, userId);
			return true;
		} catch (DBRecordNotFoundException e) {
			return false;
		}
	}

	public LeagueUser getLeagueUserById(long leagueUserId)
			throws LeagueException {
		try {
			return leagueDAO.findLeagueUserById(leagueUserId);
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
	
	public List<MyFollowersFormBean> getMyFollowers(UserMaster player, LeagueMaster league) throws LeagueException
	{
		List<MyFollowersFormBean> myFollowerFormBeanList = new ArrayList<MyFollowersFormBean>();
		try {
			List<FollowerMaster> followMasterList = leagueDAO.findMyFollowers(player.getUserId(),league.getLeagueId());
			
			for (FollowerMaster followerMaster : followMasterList) {
				MyFollowersFormBean myFollowersFormBean = new MyFollowersFormBean();
				myFollowersFormBean.setName(followerMaster.getFollowerUserMaster().getFirstName());
				if(IApplicationConstants.FOLLOWER_STATUS_PENDING.equalsIgnoreCase(followerMaster.getStatus()))
				{
					myFollowersFormBean.setStatus("Pending");
				}
				else
				{
					myFollowersFormBean.setStatus("Accepted");
				}
				
				myFollowersFormBean.setUserId(followerMaster.getFollowerUserMaster().getUserId());
				myFollowerFormBeanList.add(myFollowersFormBean);
			}
		} catch (DAOException e) {
			LOG.error(e);
			throw new LeagueException(e);
		}
		return myFollowerFormBeanList;
	}
	
	public List<MyFollowingFormBean> getMyFollowing(UserMaster user, LeagueMaster league) throws LeagueException
	{
		List<MyFollowingFormBean> myFollowingFormBeanList = new ArrayList<MyFollowingFormBean>();
		try {
			List<FollowerMaster> followMasterList = leagueDAO.findYourFollowing(user.getUserId(),league.getLeagueId());
			
			for (FollowerMaster followerMaster : followMasterList) {
				MyFollowingFormBean myFollowingFormBean = new MyFollowingFormBean();
				UserMaster userMaster = userService.findById(followerMaster.getPlayerUserId());
				myFollowingFormBean.setName(userMaster.getFirstName());
				myFollowingFormBean.setUserId(followerMaster.getPlayerUserId());
				myFollowingFormBeanList.add(myFollowingFormBean);
			}
		} catch (DAOException e) {
			LOG.error(e);
			throw new LeagueException(e);
		}
		catch (UserServiceException e) {
			LOG.error(e);
			throw new LeagueException(e);
		}
		return myFollowingFormBeanList;
	}
	
	public List<PlayersRecentTradesFormBean> getPlayersRecentTrades(LeagueMaster league, UserMaster player) throws LeagueException
	{
		List<PlayersRecentTradesFormBean> recentTradesBeanList = new ArrayList<PlayersRecentTradesFormBean>();
		try {
			LeagueUser leagueUser = getLeagueUser(league.getLeagueId(),player.getUserId());
			List<OrderMaster> allOrdersList = orderService.getAllOrders(leagueUser.getLeagueUserId());
			for (OrderMaster orderMaster : allOrdersList) {
				PlayersRecentTradesFormBean orderHistoryFormBean = new PlayersRecentTradesFormBean();
				orderHistoryFormBean
						.setDateTime(DateUtils
								.getFormattedDateTimeString(orderMaster
										.getOrderDate()));
				orderHistoryFormBean.setTikerSymbol(orderMaster
						.getStockMaster().getTikerSymbol());
				// TODO: Change later to II8n
				if (IApplicationConstants.BUY_TYPE
						.equals(orderMaster.getType())) {
					orderHistoryFormBean.setOrderType("Buy");
				} else {
					orderHistoryFormBean.setOrderType("Sell");
				}
				orderHistoryFormBean.setQuantity(orderMaster.getVolume().toPlainString());
				recentTradesBeanList.add(orderHistoryFormBean);
			}
		} catch (OrderServiceException e) {
			LOG.error(e);
			throw new LeagueException(e);
		} catch (LeagueException e) {
			LOG.error(e);
			throw new LeagueException(e);
		}
		return recentTradesBeanList;
	}
	
	public List<MyLeagueFormBean> getMyLeaguesIncludingGlobal(long userId)
			throws LeagueException {
		List<MyLeagueFormBean> myLeagueFormBeanList = new ArrayList<MyLeagueFormBean>();
		try {
			UserMaster userMaster = userService.findById(userId);
			List<LeagueMaster> leagueMasterList = leagueDAO
					.findAll(LeagueMaster.class);
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

	public List<LeaguePlayerFormBean> getLeaguePlayers(long leaugeId)
			throws LeagueException {
		List<LeaguePlayerFormBean> leaguePlayerFormBeanList = new ArrayList<LeaguePlayerFormBean>();
		List<LeaguePlayerFormBean> rankList = new ArrayList<LeaguePlayerFormBean>();
		try {
			List<LeagueUser> leagueUserList = leagueDAO
					.findAllLeagueUsers(leaugeId);
			for (LeagueUser leagueUser : leagueUserList) {
				LeaguePlayerFormBean leaguePlayerFormBean = new LeaguePlayerFormBean();
				leaguePlayerFormBean.setLeagueUserId(String.valueOf(leagueUser
						.getLeagueUserId()));
				leaguePlayerFormBean.setUserId(String.valueOf(leagueUser
						.getUserMaster().getUserId()));
				leaguePlayerFormBean.setName(leagueUser.getUserMaster()
						.getFirstName());
				MyPortfolioFormBean myPortfolioFormBean = portfolioService
						.getMyPortfolio(leaugeId, leagueUser.getUserMaster()
								.getUserId());
				BigDecimal totalValue = new BigDecimal(0);
				if (myPortfolioFormBean.getTotalValue() != null) {
					totalValue = totalValue.add(new BigDecimal(
							myPortfolioFormBean.getTotalValue()));
				}
				leaguePlayerFormBean.setTotalValue(myPortfolioFormBean
						.getTotalValue());
				// TODO:
				String minimumQualifyingValueStr = PropertiesUtil
						.getProperty("stage.minimum.ranking.amount."
								+ leagueUser.getLeagueMaster().getStage()
										.toPlainString());
				BigDecimal minimumQualifyingValue = new BigDecimal(0);
				if (minimumQualifyingValueStr != null) {
					minimumQualifyingValue = minimumQualifyingValue
							.add(new BigDecimal(minimumQualifyingValueStr));
				}
				if (totalValue.compareTo(new BigDecimal(0)) > 0
						&& totalValue.compareTo(minimumQualifyingValue) >= 0) {
					rankList.add(leaguePlayerFormBean);
				}
				leaguePlayerFormBeanList.add(leaguePlayerFormBean);
			}
			sortAndRank(rankList);
		} catch (DAOException e) {
			LOG.error(e);
			throw new LeagueException(e);
		} catch (PortfolioServiceException e) {
			LOG.error(e);
			throw new LeagueException(e);
		}
		return leaguePlayerFormBeanList;
	}
	
	public List<LeaguePlayerFormBean> getLeaguePlayersBasedOnRanking(long leaugeId, int count) throws LeagueException {
		List<LeaguePlayerFormBean> beanList = getLeaguePlayers(leaugeId);
		
		Collections.sort(beanList, new Comparator<LeaguePlayerFormBean>() {
			@Override
			public int compare(LeaguePlayerFormBean o1, LeaguePlayerFormBean o2) {
				if(o1.getRank() != null && o2.getRank() != null)
				{
					return new BigDecimal(o1.getRank())
					.compareTo(new BigDecimal(o2.getRank()));
				}
				else if(o2.getRank() == null)
				{
					return 1;
				}
				else if(o1.getRank() == null)
				{
					return -1;
				}
				else
				{
					return 0;
				}
			}
		});
		
		if(count > 0 && beanList.size() >= count)
		{
			beanList = beanList.subList(0, count);
		}
		
		return beanList;
	}

	private void sortAndRank(List<LeaguePlayerFormBean> rankList) {
		Collections.sort(rankList, new Comparator<LeaguePlayerFormBean>() {
			@Override
			public int compare(LeaguePlayerFormBean o1, LeaguePlayerFormBean o2) {
				return new BigDecimal(o1.getTotalValue())
						.compareTo(new BigDecimal(o2.getTotalValue()));
			}
		});
		int rank = 0;
		Collections.reverse(rankList);
		for (LeaguePlayerFormBean leaguePlayerFormBean : rankList) {
			leaguePlayerFormBean.setRank(String.valueOf(++rank));
		}
	}
}
