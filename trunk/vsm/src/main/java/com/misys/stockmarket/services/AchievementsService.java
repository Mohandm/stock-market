package com.misys.stockmarket.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.dao.AchievementsDAO;
import com.misys.stockmarket.domain.entity.AchievementCategory;
import com.misys.stockmarket.domain.entity.AchievementRule;
import com.misys.stockmarket.domain.entity.LeagueUser;
import com.misys.stockmarket.domain.entity.UserAchievement;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.exception.BaseException;
import com.misys.stockmarket.exception.DAOException;
import com.misys.stockmarket.exception.DBRecordNotFoundException;
import com.misys.stockmarket.exception.EmailNotFoundException;
import com.misys.stockmarket.exception.service.AchievementServiceException;
import com.misys.stockmarket.mbeans.AchievementFormBean;
import com.misys.stockmarket.mbeans.RedeemCoinsFormBean;
import com.misys.stockmarket.platform.web.ResponseMessage;
import com.misys.stockmarket.utility.DateUtils;

@Service("achievementsService")
@Repository
public class AchievementsService {

	@Inject
	private AchievementsDAO achievementsDAO;

	@Inject
	private UserService userService;

	@Inject
	private LeagueService leagueService;

	private static final Log LOG = LogFactory.getLog(AchievementsService.class);

	@Transactional(rollbackFor = DAOException.class)
	public void addCategory(AchievementCategory category)
			throws AchievementServiceException {
		try {
			achievementsDAO.persist(category);
		} catch (DAOException e) {
			throw new AchievementServiceException(e);
		}
	}

	@Transactional(rollbackFor = DAOException.class)
	public void addRule(AchievementRule rule)
			throws AchievementServiceException {
		try {
			achievementsDAO.persist(rule);
		} catch (DAOException e) {
			throw new AchievementServiceException(e);
		}
	}

	@Transactional(rollbackFor = DAOException.class)
	public void addUserAchievement(UserAchievement achievement)
			throws AchievementServiceException {
		try {
			achievementsDAO.persist(achievement);
		} catch (DAOException e) {
			throw new AchievementServiceException(e);
		}
	}

	@Transactional(rollbackFor = DAOException.class)
	public void addUserAchievement(UserMaster user, AchievementRule rule)
			throws AchievementServiceException {
		UserAchievement achievement = new UserAchievement();
		achievement.setUserMaster(user);
		achievement.setAchievementRule(rule);
		achievement.setCompleted(new Date());
		achievement
				.setPublished(IApplicationConstants.ACHIEVEMENT_PUBLISHED_NO);
		try {
			achievementsDAO.persist(achievement);
		} catch (DAOException e) {
			throw new AchievementServiceException(e);
		}

		// Update coins in user master
		Long existingCoins = user.getCoins();
		Long ruleCoins = rule.getCoins();
		if (existingCoins != null) {
			user.setCoins(existingCoins + ruleCoins);
		} else {
			user.setCoins(ruleCoins);
		}
		try {
			achievementsDAO.update(user);
		} catch (DAOException e) {
			throw new AchievementServiceException(e);
		}
	}

	public AchievementRule getNextRule(String achievementName, UserMaster user)
			throws AchievementServiceException {
		AchievementCategory category = getCategory(achievementName);
		int level = getNextLevel(user, category);
		return getRule(level, category);
	}

	private AchievementCategory getCategory(String achievementName)
			throws AchievementServiceException {

		AchievementCategory category = new AchievementCategory();
		try {
			category = achievementsDAO.findAchievementType(achievementName);
		} catch (DBRecordNotFoundException e) {
			throw new AchievementServiceException(e);
		}
		return category;
	}

	private AchievementRule getRule(int level, AchievementCategory category)
			throws AchievementServiceException {
		AchievementRule rule = new AchievementRule();
		try {
			rule = achievementsDAO.findAchievementRule(category, level);
		} catch (DBRecordNotFoundException e) {
			throw new AchievementServiceException(e);
		}

		return rule;
	}

	private int getNextLevel(UserMaster user, AchievementCategory category)
			throws AchievementServiceException {
		List<UserAchievement> userAchievements = new ArrayList<UserAchievement>();
		try {
			userAchievements = achievementsDAO.findAllUserAchievements(user,
					category);
		} catch (DAOException e) {
			throw new AchievementServiceException(e);
		}
		// TODO: Handle max value for levels
		return userAchievements.size() + 1;
	}

	public List<UserAchievement> getPendingAchievementsForPublishing(
			UserMaster userMaster) {
		List<UserAchievement> pendingAchievements = new ArrayList<UserAchievement>();
		try {
			pendingAchievements = achievementsDAO
					.findAllAchievementsForPublishing(userMaster);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pendingAchievements;
	}

	public List<UserAchievement> getCompletedAchievements(UserMaster userMaster) {
		List<UserAchievement> userAchievements = new ArrayList<UserAchievement>();
		try {
			userAchievements = achievementsDAO
					.findAllCompletedAchievements(userMaster);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userAchievements;
	}
	
	public List<AchievementRule> findAllAchievementRule() {
		List<AchievementRule> userAchievements = new ArrayList<AchievementRule>();
		try {
			userAchievements = achievementsDAO
					.findAllAchievementRule();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userAchievements;
	}

	public List<UserAchievement> getUnpublishedAchievements(
			UserMaster userMaster) {
		List<UserAchievement> userAchievements = new ArrayList<UserAchievement>();
		try {
			userAchievements = achievementsDAO
					.findAllAchievementsForPublishing(userMaster);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userAchievements;
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@Transactional(rollbackFor = DAOException.class)
	public List<AchievementFormBean> getUnpublishedAchievements() {
		List<AchievementFormBean> achievementList = new ArrayList<AchievementFormBean>();

		UserMaster userMaster = null;
		try {
			userMaster = userService.getLoggedInUser();
		} catch (EmailNotFoundException e) {
			// TODO Auto-generated catch block - handle exceptions
			e.printStackTrace();
		}
		List<UserAchievement> userAchievements = getPendingAchievementsForPublishing(userMaster);
		createBeanListFromUserAchievements(achievementList, userAchievements);

		try {
			markAchievementsAsPublished(userAchievements);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return achievementList;
	}

	private void markAchievementsAsPublished(
			List<UserAchievement> userAchievements) throws DAOException {
		for (UserAchievement userAchievement : userAchievements) {
			userAchievement
					.setPublished(IApplicationConstants.ACHIEVEMENT_PUBLISHED_YES);
			achievementsDAO.update(userAchievement);
		}
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	public List<AchievementFormBean> getCompletedAchievements() {
		List<AchievementFormBean> achievementList = new ArrayList<AchievementFormBean>();

		UserMaster userMaster = null;
		try {
			userMaster = userService.getLoggedInUser();
		} catch (EmailNotFoundException e) {
			// TODO Auto-generated catch block - handle exceptions
			e.printStackTrace();
		}
		List<UserAchievement> userAchievements = getCompletedAchievements(userMaster);
		createBeanListFromUserAchievements(achievementList, userAchievements);
		return achievementList;
	}

	private void createBeanListFromUserAchievements(
			List<AchievementFormBean> achievementList,
			List<UserAchievement> userAchievements) {
		AchievementFormBean achForm = null;
		AchievementRule rule = null;
		AchievementCategory achievementCategory = null;
		for (UserAchievement userAchievement : userAchievements) {
			achForm = new AchievementFormBean();
			rule = userAchievement.getAchievementRule();
			achForm.setLevel(rule.getLevel());
			achForm.setDescription(rule.getDescription());
			achForm.setCompleted(DateUtils
					.getFormattedDateTimeString(userAchievement.getCompleted()));
			achForm.setCoins(rule.getCoins());
			achievementCategory = rule.getAchievementCategory();
			achForm.setAlias(achievementCategory.getAlias());
			achForm.setName(achievementCategory.getName());
			achievementList.add(achForm);
		}
	}

	private void createBeanListFromAchievementRules(
			List<AchievementFormBean> achievementList,
			List<AchievementRule> rules) {
		AchievementFormBean achForm = null;
		AchievementCategory achievementCategory = null;
		for (AchievementRule rule : rules) {
			achForm = new AchievementFormBean();
			achForm.setLevel(rule.getLevel());
			achForm.setDescription(rule.getDescription());
			achForm.setCoins(rule.getCoins());
			achievementCategory = rule.getAchievementCategory();
			achForm.setAlias(achievementCategory.getAlias());
			achForm.setName(achievementCategory.getName());
			achievementList.add(achForm);
		}
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	public List<AchievementFormBean> getPendingAchievements() {
		UserMaster userMaster = null;
		try {
			userMaster = userService.getLoggedInUser();
		} catch (EmailNotFoundException e) {
			// TODO Auto-generated catch block - handle exceptions
			e.printStackTrace();
		}

		List<AchievementFormBean> achievementList = new ArrayList<AchievementFormBean>();
		List<AchievementRule> pendingList = new ArrayList<AchievementRule>();
		try {
			pendingList = getPendingList(userMaster);
		} catch (AchievementServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createBeanListFromAchievementRules(achievementList, pendingList);
		return achievementList;
	}

	private List<AchievementRule> getPendingList(UserMaster userMaster)
			throws AchievementServiceException {
		List<AchievementRule> pendingList = new ArrayList<AchievementRule>();
		try {
			List<AchievementCategory> category = achievementsDAO
					.findAllAchievements();
			AchievementRule rule = null;
			for (AchievementCategory achievementCategory : category) {
				rule = getNextRule(achievementCategory.getName(), userMaster);
				pendingList.add(rule);
			}
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			throw new AchievementServiceException(e);
		}
		return pendingList;
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@Transactional(rollbackFor = DAOException.class)
	public ResponseMessage redeemCoins(RedeemCoinsFormBean redeemCoinsFormBean) {

		try {
			// Get the user
			UserMaster user = null;
			user = userService.getLoggedInUser();

			// Check whether there is enough coins with user
			long redeemedCoins = redeemCoinsFormBean.getCoins();
			long userCoins = user.getCoins();

			if (redeemedCoins > userCoins) {
				LOG.error(" User doesnot have sufficient coins");
				return new ResponseMessage(ResponseMessage.Type.danger,
						"You donot have sufficient coins.");
			}
			// Get the league user
			LeagueUser leagueUser = null;
			leagueUser = leagueService.getLeagueUser(
					redeemCoinsFormBean.getLeagueId(), user.getUserId());

			BigDecimal redeemedAmount = BigDecimal
					.valueOf(redeemedCoins
							* IApplicationConstants.ACHIEVEMENTS_COINS_TO_LEAGUE_AMOUNT_MULTIPLIER);

			// Update league user
			BigDecimal leagueAmount = leagueUser.getRemainingAmount();
			leagueUser.setRemainingAmount(leagueAmount.add(redeemedAmount));
			achievementsDAO.update(leagueUser);

			// Update user master
			user.setCoins(userCoins - redeemedCoins);
			achievementsDAO.update(user);
			return new ResponseMessage(ResponseMessage.Type.success,
					"The coins have been successfully redeemed against the league.");

		} catch (BaseException e) {
			LOG.error(e);
			return new ResponseMessage(ResponseMessage.Type.danger,
					"There was a technical error while redeeming the coins");
		}
	}
}
