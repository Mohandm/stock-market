package com.misys.stockmarket.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.misys.stockmarket.domain.entity.BaseEntity;

public class BaseDAO implements IBaseDAO {

	protected EntityManager entityManager;

	@PersistenceContext(unitName = "persistenceUnit")
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional
	@Override
	public <T extends BaseEntity> void persist(T anyEntity) {
		entityManager.persist(anyEntity);

	}

	@Transactional
	@Override
	public <T extends BaseEntity> void update(T anyEntity) {
		entityManager.merge(anyEntity);

	}

	/*@Override
	public <T extends GtpBaseEntity, X extends GtpBasePK> T findById(
			Class<? extends T> type, X id) {
		return entityManager.find(type, id);

	}*/
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends BaseEntity> List<T> findAll(Class<? extends T> type) {

		String entityName = type.getSimpleName();
		return entityManager.createQuery("select e from " + entityName + " e ")
				.getResultList();
	}

}
