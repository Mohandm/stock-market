package com.misys.stockmarket.dao;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.domain.entity.LeagueMaster;

@Service("leagueDAO")
@Repository
public class LeagueDAO extends BaseDAO {
	
public LeagueMaster findByName(String name) {
		Query q = entityManager
				.createQuery("select e from LeagueMaster e where e.name = ? ");
		q.setParameter(1, name);
		return (LeagueMaster) q.getSingleResult();
	}

}
