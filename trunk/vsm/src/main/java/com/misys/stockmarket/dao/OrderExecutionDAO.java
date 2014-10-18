package com.misys.stockmarket.dao;

import javax.persistence.Query;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.domain.entity.OrderExecution;
import com.misys.stockmarket.domain.entity.OrderMaster;
import com.misys.stockmarket.exception.DBRecordNotFoundException;

@Service("orderExecutionDAO")
@Repository
public class OrderExecutionDAO  extends BaseDAO {
	
	public OrderExecution findByOrderMaster(OrderMaster orderMaster)
			throws DBRecordNotFoundException {
		try {
			Query q = entityManager
					.createQuery("select e from OrderExecution e where e.orderMaster = ?");
			q.setParameter(1, orderMaster);
			return (OrderExecution) q.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			throw new DBRecordNotFoundException(e);
		}
	}

}
