package com.misys.stockmarket.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.domain.entity.StockHistory;
import com.misys.stockmarket.domain.entity.StockMaster;
import com.misys.stockmarket.exception.DBRecordNotFoundException;

@Service("stockDAO")
@Repository
public class StockDAO extends BaseDAO {

	public List<StockMaster> findAllActiveStocks() {
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("active", IApplicationConstants.STOCK_ACTIVE);
		return findByFilter(StockMaster.class, criteria);
	}

	public List<StockMaster> findAllInActiveStocks() {
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("active", IApplicationConstants.STOCK_INACTIVE);
		return findByFilter(StockMaster.class, criteria);
	}
	
	public StockMaster findBySymbol(String symbol)
			throws DBRecordNotFoundException {
		try {
			Query q = entityManager
					.createQuery("select e from StockMaster e where e.tikerSymbol = ?");
			q.setParameter(1, symbol);
			return (StockMaster) q.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			throw new DBRecordNotFoundException(e);
		}
	}

	/**
	 * Method to return the list of all the active tiker symbols
	 * 
	 * @return List<String>
	 */
	public List<String> findAllActiveStockSymbols() {
		Query q = entityManager
				.createQuery("select e.tikerSymbol from StockMaster e where e.active = ? ");
		q.setParameter(1, IApplicationConstants.STOCK_ACTIVE);
		return q.getResultList();
	}

	public StockMaster findByTickerSymbol(String symbol) {
		Query q = entityManager
				.createQuery("select e from StockMaster e where e.tikerSymbol = ? and e.active = ? ");
		q.setParameter(1, symbol);
		q.setParameter(2, IApplicationConstants.STOCK_ACTIVE);
		return (StockMaster) q.getSingleResult();
	}

	public StockHistory findStockHistoryByStockIdStockDate(long stockId,
			Date stockDate) throws DBRecordNotFoundException {
		try {
			Query q = entityManager
					.createQuery("select e from StockHistory e where e.stockMaster.stockId = ? and e.stockDate = ? ");
			q.setParameter(1, stockId);
			q.setParameter(2, stockDate);
			return (StockHistory) q.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			throw new DBRecordNotFoundException(e);
		}
	}

	public List<StockHistory> findStockHistory(long stockId, Date startDate,
			Date endDate) {
		Query q = entityManager
				.createQuery("select e from StockHistory e where e.stockMaster.stockId = ? and e.stockDate between ? and ? order by e.stockDate ");
		q.setParameter(1, stockId);
		q.setParameter(2, startDate);
		q.setParameter(3, endDate);
		return (List<StockHistory>) q.getResultList();
	}
}
