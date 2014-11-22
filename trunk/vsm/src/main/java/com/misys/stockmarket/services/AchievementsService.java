package com.misys.stockmarket.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.dao.AchievementsDAO;
import com.misys.stockmarket.domain.entity.AchievementCategory;
import com.misys.stockmarket.domain.entity.AchievementRule;
import com.misys.stockmarket.domain.entity.UserAchievement;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.exception.DAOException;
import com.misys.stockmarket.exception.DBRecordNotFoundException;
import com.misys.stockmarket.exception.EmailNotFoundException;
import com.misys.stockmarket.exception.service.AchievementServiceException;
import com.misys.stockmarket.mbeans.AchievementFormBean;
import com.misys.stockmarket.utility.DateUtils;

@Service("achievementsService")
@Repository
public class AchievementsService {

	@Inject
	private AchievementsDAO achievementsDAO;

	@Inject
	private UserService userService;

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

	public AchievementRule getNextRule(String achievementName,
			UserMaster user) throws AchievementServiceException {
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
			userAchievement.setPublished(IApplicationConstants.ACHIEVEMENT_PUBLISHED_YES);
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

}
