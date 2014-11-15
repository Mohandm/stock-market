package com.misys.stockmarket.dao;

import java.util.List;
import java.util.Map;

import com.misys.stockmarket.domain.entity.BaseEntity;
import com.misys.stockmarket.exception.DAOException;

public interface IBaseDAO {

	public <T extends BaseEntity> void persist(T anyEntity) throws DAOException;

	public <T extends BaseEntity> void update(T anyEntity) throws DAOException;

	public <T extends BaseEntity> void delete(T anyEntity) throws DAOException;

	public <T extends BaseEntity> List<T> findAll(Class<? extends T> type)
			throws DAOException;

	public <T extends BaseEntity, X extends Long> T findById(
			Class<? extends T> type, X id) throws DAOException;

	/**
	 * 
	 * public <T extends BaseEntity> List<T> findAll(T entity);
	 * 
	 */
	public <T extends BaseEntity> List<T> findByFilter(Class<? extends T> type,
			Map<String, Object> criteria) throws DAOException;

}
