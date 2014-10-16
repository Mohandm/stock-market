package com.misys.stockmarket.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

	@Override
	public <T extends BaseEntity, X extends Long> T findById(
			Class<? extends T> type, X id) {
		return entityManager.find(type, id);

	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BaseEntity> List<T> findAll(Class<? extends T> type) {

		String entityName = type.getSimpleName();
		return entityManager.createQuery("select e from " + entityName + " e ")
				.getResultList();
	}

	@Override
	public <T extends BaseEntity> List<T> findByFilter(Class<? extends T> type,
			Map<String, Object> criteria) {
		String entityName = type.getSimpleName();
		StringBuffer query = new StringBuffer();
		query.append("select e from " + entityName + " e ");
		List<Object> values = new ArrayList<Object>();
		if (criteria != null && criteria.size() > 0) {
			query.append(" where ");
			int index = 1;
			for (Entry<String, Object> entry : criteria.entrySet()) {
				values.add(entry.getValue());
				if (index > 1) {
					query.append(" and ");
				}
				query.append("e.").append(entry.getKey()).append(" = ?")
						.append(index++);
			}
		}
		Query q = entityManager.createQuery(query.toString());
		for (int i = 0; i < values.size(); i++) {
			q.setParameter(i + 1, values.get(i));
		}
		return (List<T>) q.getResultList();
	}

}