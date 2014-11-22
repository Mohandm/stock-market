package com.misys.stockmarket.utest;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.misys.stockmarket.domain.entity.UserAchievement;
import com.misys.stockmarket.exception.RewardException;
import com.misys.stockmarket.exception.service.AchievementServiceException;
import com.misys.stockmarket.services.AchievementsService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:META-INF/spring/test-context.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
public class AchievementServiceDBUnitTest {

	@Inject
	private AchievementsService achievementsService;

	@Test
	@DatabaseSetup({ "UserMasterSetup.xml", "AchievementTypeSetup.xml",
			"UserAchievementSetup.xml", "AchievementRuleSetup.xml" })
	/*@DatabaseTearDown(value = { "AchievementRuleTearDown.xml",
			"UserAchievementTearDown.xml", "AchievementTypeTearDown.xml",
			"UserMasterTearDown.xml" }, type = DatabaseOperation.DELETE_ALL)*/
	public final void testEvaluateAchievement() throws RewardException,
			AchievementServiceException {
		/*List<UserAchievement> userAchievementPendingEvaluationList = achievementsService
				.getPendingAchievementsForEvaluation();
		for (UserAchievement userAchievement : userAchievementPendingEvaluationList) {
			achievementsService.evaluateAchievement(userAchievement);
		}*/
	}

}
