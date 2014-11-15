/**
 * 
 */
package com.misys.stockmarket.utest;

import static org.junit.Assert.assertEquals;

import java.util.List;

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
import org.springframework.util.Assert;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.misys.stockmarket.domain.entity.StockMaster;
import com.misys.stockmarket.exception.service.StockServiceException;
import com.misys.stockmarket.mbeans.StockCurrentQuotesBean;
import com.misys.stockmarket.services.StockService;

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
public class StockServiceDBUnitTest {

	@Inject
	private StockService stockService;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.misys.stockmarket.services.StockService#listStockHistory(java.lang.String)}
	 * .
	 * 
	 * @throws StockServiceException
	 */
	@Test
	@DatabaseSetup("stockMasterSetup.xml")
	@DatabaseTearDown(value = "stockMasterTearDown.xml", type = DatabaseOperation.DELETE_ALL)
	public final void testListAllActiveStocks() throws StockServiceException {
		List<StockMaster> stockMasterList = stockService.listAllActiveStocks();
		Assert.notNull(stockMasterList);
		assertEquals(2, stockMasterList.size());
	}

	/**
	 * 
	 * @throws StockServiceException
	 */
	@Test
	@DatabaseSetup("stockMasterSetup.xml")
	@DatabaseTearDown(value = "stockMasterTearDown.xml", type = DatabaseOperation.DELETE_ALL)
	public final void testListAllInActiveStocks() throws StockServiceException {
		List<StockMaster> stockMasterList = stockService
				.listAllInActiveStocks();
		Assert.notNull(stockMasterList);
		assertEquals(1, stockMasterList.size());
	}

	/**
	 * Test method for
	 * {@link com.misys.stockmarket.services.StockService#getStockCurrentQuoteByStockSymbol(java.lang.String)}
	 * .
	 * 
	 * @throws StockServiceException
	 */
	@Test
	@DatabaseSetup({ "stockMasterSetup.xml", "StockCurrentQuotesSetup.xml" })
	@DatabaseTearDown(value = { "StockCurrentQuotesSetup.xml",
			"stockMasterTearDown.xml" }, type = DatabaseOperation.DELETE_ALL)
	public final void testGetStockCurrentQuoteByStockSymbol()
			throws StockServiceException {
		StockCurrentQuotesBean stockCurrentQuotesBean = stockService
				.getStockCurrentQuoteByStockSymbol("AAPL");
		Assert.notNull(stockCurrentQuotesBean);
		assertEquals("1", stockCurrentQuotesBean.getStockId());
	}

}
