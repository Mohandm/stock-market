package com.misys.stockmarket.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.dao.StockDAO;
import com.misys.stockmarket.domain.entity.StockMaster;

@Service("stockService")
@Repository
public class StockService {
	@Inject
	private StockDAO stockDAO;

	public List<StockMaster> listAllActiveStocks() {
		return stockDAO.findAllActiveStocks();
	}

	public List<StockMaster> listAllInActiveStocks() {
		return stockDAO.findAllInActiveStocks();
	}

	public List<StockMaster> listAllStocks() {
		return stockDAO.findAll(StockMaster.class);
	}

	public void saveStocks(List<StockMaster> newStocksList) {
		for (StockMaster stockMaster : newStocksList) {
			stockDAO.persist(stockMaster);
		}
	}

	public void updateStocks(List<StockMaster> updateStocksList) {
		for (StockMaster stockMaster : updateStocksList) {
			stockDAO.update(stockMaster);
		}
	}

}
