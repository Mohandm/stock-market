package com.misys.stockmarket.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.domain.entity.StockCurrentQuotes;
import com.misys.stockmarket.domain.entity.StockHistory;
import com.misys.stockmarket.domain.entity.StockMaster;
import com.misys.stockmarket.exception.DAOException;
import com.misys.stockmarket.exception.DBRecordNotFoundException;

@Service("stockDAO")
@Repository
public class StockDAO extends BaseDAO {

	public List<StockMaster> findAllActiveStocks() throws DAOException {
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("active", IApplicationConstants.STOCK_ACTIVE);
		return findByFilter(StockMaster.class, criteria);
	}

	public List<StockMaster> findAllInActiveStocks() throws DAOException {
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("active", IApplicationConstants.STOCK_INACTIVE);
		return findByFilter(StockMaster.class, criteria);
	}

	public StockMaster findBySymbol(String symbol) throws DAOException {
		try {
			Query q = entityManager
					.createQuery("select e from StockMaster e where e.tikerSymbol = ?1 ");
			q.setParameter(1, symbol);
			return (StockMaster) q.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			throw new DBRecordNotFoundException(e);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	/**
	 * Method to return the list of all the active tiker symbols
	 * 
	 * @return List<String>
	 * @throws DAOException
	 */
	public List<String> findAllActiveStockSymbols() throws DAOException {
		try {
			Query q = entityManager
					.createQuery("select e.tikerSymbol from StockMaster e where e.active = ?1 ");
			q.setParameter(1, IApplicationConstants.STOCK_ACTIVE);
			return q.getResultList();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public List<String> findAllStockSymbols() {
		Query q = entityManager
				.createQuery("select e.tikerSymbol from StockMaster e");
		return q.getResultList();
	}

	public StockMaster findByTickerSymbol(String symbol) throws DAOException {
		try {
			Query q = entityManager
					.createQuery("select e from StockMaster e where e.tikerSymbol = ?1 and e.active = ?2 ");
			q.setParameter(1, symbol);
			q.setParameter(2, IApplicationConstants.STOCK_ACTIVE);
			return (StockMaster) q.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			throw new DBRecordNotFoundException(e);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public StockMaster findByAllTickerSymbol(String symbol) {
		Query q = entityManager
				.createQuery("select e from StockMaster e where e.tikerSymbol = ?1 ");
		q.setParameter(1, symbol);
		return (StockMaster) q.getSingleResult();
	}

	public StockHistory findStockHistoryByStockIdStockDate(long stockId,
			Date stockDate) throws DBRecordNotFoundException {
		try {
			Query q = entityManager
					.createQuery("select e from StockHistory e where e.stockMaster.stockId = ?1 and e.stockDate = ?2 ");
			q.setParameter(1, stockId);
			q.setParameter(2, stockDate);
			return (StockHistory) q.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			throw new DBRecordNotFoundException(e);
		}
	}

	public StockCurrentQuotes findStockCurrentQuoteByStockId(long stockId) {
		Query q = entityManager
				.createQuery("select e from StockCurrentQuotes e where e.stockMaster.stockId = ?1 ");
		q.setParameter(1, stockId);
		return (StockCurrentQuotes) q.getSingleResult();
	}

	public StockCurrentQuotes findStockCurrentQuoteByTickerSymbol(
			String tickerSymbol) throws DAOException {
		try {
			Query q = entityManager
					.createQuery("select e from StockCurrentQuotes e join fetch e.stockMaster where e.stockMaster.tikerSymbol = ?1 ");
			q.setParameter(1, tickerSymbol);
			return (StockCurrentQuotes) q.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			throw new DBRecordNotFoundException(e);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Transactional
	public void updateCurrentStock(StockCurrentQuotes stockCurrentQuotes)
			throws DAOException {
		try {
			Query q = entityManager
					.createQuery("delete from StockCurrentQuotes e where e.stockMaster.stockId = ?1");
			q.setParameter(1, stockCurrentQuotes.getStockMaster().getStockId());
			q.executeUpdate();
			entityManager.persist(stockCurrentQuotes);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public List<StockHistory> findStockHistory(long stockId, Date startDate,
			Date endDate) throws DAOException {
		try {
			Query q = entityManager
					.createQuery("select e from StockHistory e where e.stockMaster.stockId = ?1 and e.stockDate between ?2 and ?3 order by e.stockDate ");
			q.setParameter(1, stockId);
			q.setParameter(2, startDate);
			q.setParameter(3, endDate);
			return (List<StockHistory>) q.getResultList();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public List<StockHistory> findStockHistoryByStockId(long stockId)
			throws DAOException {
		try {
			Query q = entityManager
					.createQuery("select e from StockHistory e where e.stockMaster.stockId = ?1 order by e.stockDate");
			q.setParameter(1, stockId);
			return (List<StockHistory>) q.getResultList();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public Date findMaxStockHistoryStockDate(long stockId) {
		Query q = entityManager
				.createQuery("select max(e.stockDate) from StockHistory e where e.stockMaster.stockId = ?1 ");
		q.setParameter(1, stockId);
		return (Date) q.getSingleResult();
	}
}
