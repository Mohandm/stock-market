package com.misys.stockmarket.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.domain.entity.StockMaster;

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

}
