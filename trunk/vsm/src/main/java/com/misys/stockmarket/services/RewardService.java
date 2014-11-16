package com.misys.stockmarket.services;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.misys.stockmarket.dao.RewardDAO;
import com.misys.stockmarket.domain.entity.RewardMaster;
import com.misys.stockmarket.enums.REWARD_ERROR_CODES;
import com.misys.stockmarket.exception.DAOException;
import com.misys.stockmarket.exception.RewardException;

@Service("rewardService")
@Repository
public class RewardService {

	private static final Log LOG = LogFactory.getLog(RewardService.class);
	
	@Inject
	RewardDAO rewardDAO;
	@Transactional
	public void saveReward(RewardMaster rewardMaster) throws RewardException
	{
		try {
			rewardDAO.persist(rewardMaster);
		} catch (DAOException e) {
			LOG.error(e);
			throw new RewardException(e);
		}
		
	}

	public RewardMaster getRewardByType(String type) throws RewardException {
		
		try {
			return rewardDAO.findRewardByType(type);
		} catch (DAOException e) {
			LOG.error(e);
			throw new RewardException(REWARD_ERROR_CODES.REWARD_NOT_FOUND);
		}
	}
	
}
