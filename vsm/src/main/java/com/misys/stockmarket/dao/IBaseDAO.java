package com.misys.stockmarket.dao;

import java.util.List;

import com.misys.stockmarket.domain.entity.BaseEntity;

public interface IBaseDAO {

	public <T extends BaseEntity> void persist(T anyEntity);

	public <T extends BaseEntity> void update(T anyEntity);

	public <T extends BaseEntity> List<T> findAll(Class<? extends T> type);

	public <T extends BaseEntity, X extends Long> T findById(
			Class<? extends T> type, X id);
	/**
	 * 
	 * public <T extends BaseEntity> List<T> findAll(T entity);
	 * 
	 * 
	 * public <T extends BaseEntity> List<T> findByFilter( Class<? extends T>
	 * type, Map<String, Object> criteria);
	 */

}
