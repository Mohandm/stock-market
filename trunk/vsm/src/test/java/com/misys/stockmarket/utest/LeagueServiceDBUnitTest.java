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
import com.misys.stockmarket.domain.entity.LeagueMaster;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.exception.EmailNotFoundException;
import com.misys.stockmarket.exception.LeagueException;
import com.misys.stockmarket.services.LeagueService;
import com.misys.stockmarket.services.UserService;

/**
 * @author sasunda1
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:META-INF/spring/test-context.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
public class LeagueServiceDBUnitTest {

	@Inject
	private LeagueService leagueService;

	@Inject
	private UserService userService;

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
	@DatabaseSetup({ "UserMasterSetup.xml", "LeagueMasterSetup.xml" })
	@DatabaseTearDown(value = { "LeagueUserTearDown.xml",
			"LeagueMasterTearDown.xml", "UserMasterTearDown.xml" }, type = DatabaseOperation.DELETE_ALL)
	public final void testCheckLeagueUser() throws EmailNotFoundException,
			LeagueException {
		UserMaster userMaster = userService.findByEmail("sam.sundar@gmail.com");
		assertNotNull(userMaster);
		LeagueMaster LeagueMasterPremier = leagueService
				.getLeagueByName(IApplicationConstants.PREMIER_LEAGUE_NAME);
		assertNotNull(LeagueMasterPremier);
		leagueService.addUserToLeague(userMaster, LeagueMasterPremier);
		boolean leageUserExists = leagueService.checkLeagueUser(userMaster,
				LeagueMasterPremier);
		assertEquals(true, leageUserExists);
	}

	@Test
	@DatabaseSetup({ "UserMasterSetup.xml", "LeagueMasterSetup.xml" })
	@DatabaseTearDown(value = { "LeagueUserTearDown.xml",
			"LeagueMasterTearDown.xml", "UserMasterTearDown.xml" }, type = DatabaseOperation.DELETE_ALL)
	public final void testAddUserToLeague() throws EmailNotFoundException,
			LeagueException {
		UserMaster userMaster = userService.findByEmail("sam.sundar@gmail.com");
		assertNotNull(userMaster);
		LeagueMaster LeagueMasterPremier = leagueService
				.getLeagueByName(IApplicationConstants.PREMIER_LEAGUE_NAME);
		assertNotNull(LeagueMasterPremier);
		leagueService.addUserToLeague(userMaster, LeagueMasterPremier);
	}

	@Test
	@DatabaseSetup({ "UserMasterSetup.xml", "LeagueMasterSetup.xml" })
	@DatabaseTearDown(value = { "LeagueUserTearDown.xml",
			"LeagueMasterTearDown.xml", "UserMasterTearDown.xml" }, type = DatabaseOperation.DELETE_ALL)
	public final void testAddUserToDefaultLeague()
			throws EmailNotFoundException, LeagueException {
		UserMaster userMaster = userService.findByEmail("sam.sundar@gmail.com");
		assertNotNull(userMaster);
		leagueService.addUserToDefaultLeague(userMaster);
	}

	@Test
	@DatabaseSetup("LeagueMasterSetup.xml")
	@DatabaseTearDown(value = { "LeagueMasterTearDown.xml" }, type = DatabaseOperation.DELETE_ALL)
	public final void testGetDefaultLeague() throws LeagueException {
		LeagueMaster leagueMaster = leagueService.getDefaultLeague();
		assertNotNull(leagueMaster);
		assertEquals(IApplicationConstants.DEFAULT_LEAGUE_NAME,
				leagueMaster.getName());
	}

	@Test
	@DatabaseSetup("LeagueMasterSetup.xml")
	@DatabaseTearDown(value = { "LeagueMasterTearDown.xml" }, type = DatabaseOperation.DELETE_ALL)
	public final void testGetLeagueByName() throws LeagueException {
		LeagueMaster LeagueMasterPremier = leagueService
				.getLeagueByName(IApplicationConstants.PREMIER_LEAGUE_NAME);
		assertNotNull(LeagueMasterPremier);
		assertEquals(IApplicationConstants.PREMIER_LEAGUE_NAME,
				LeagueMasterPremier.getName());
	}

}
