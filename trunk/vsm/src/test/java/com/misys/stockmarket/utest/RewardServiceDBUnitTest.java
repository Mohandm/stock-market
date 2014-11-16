package com.misys.stockmarket.utest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.dao.RewardDAO;
import com.misys.stockmarket.domain.entity.RewardMaster;
import com.misys.stockmarket.exception.DAOException;
import com.misys.stockmarket.exception.RewardException;
import com.misys.stockmarket.services.RewardService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:META-INF/spring/test-context.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
public class RewardServiceDBUnitTest {

	@Inject
	private RewardService rewardService;
	
	@Inject
	private
	RewardDAO rewardDAO;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	@DatabaseSetup({ "RewardMasterSetup.xml"})
	@DatabaseTearDown(value = { "RewardMasterTearDown.xml"}, type = DatabaseOperation.DELETE_ALL)
	public final void testCheckLeagueUser() throws RewardException, DAOException {
		RewardMaster rewardMasterAchivement = rewardService.getRewardByType(IApplicationConstants.REWARD_TYPE_ACHIEVEMENT);
		assertNotNull(rewardMasterAchivement);
		assertEquals( IApplicationConstants.REWARD_TYPE_ACHIEVEMENT, rewardMasterAchivement.getRewardType());
		RewardMaster rewardById = rewardDAO.findRewardById(1);
		assertEquals(1, rewardById.getRewardId());
		assertEquals(IApplicationConstants.REWARD_TYPE_ACHIEVEMENT, rewardById.getRewardType());
		
		RewardMaster rewardMasterReferral = rewardService.getRewardByType(IApplicationConstants.REWARD_TYPE_REFERAL);
		assertNotNull(rewardMasterReferral);
		assertEquals(rewardMasterReferral.getRewardType(), IApplicationConstants.REWARD_TYPE_REFERAL);
		RewardMaster rewardByIdReferral = rewardDAO.findRewardById(2);
		assertEquals(2, rewardByIdReferral.getRewardId());
		assertEquals(IApplicationConstants.REWARD_TYPE_REFERAL, rewardByIdReferral.getRewardType());
	}
	
}
