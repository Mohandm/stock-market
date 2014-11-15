package com.misys.stockmarket.dao;

import javax.persistence.Query;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.domain.entity.UserInvitation;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.exception.DBRecordNotFoundException;

@Service("invitationDAO")
@Repository
public class InvitationDAO extends BaseDAO {
	
	public UserInvitation findInvitation(String email, UserMaster referer)
			throws DBRecordNotFoundException {
		try {
			Query q = entityManager
					.createQuery("select e from UserInvitation e where e.email = ?1 and e.referer = ?2");
			q.setParameter(1, email);
			q.setParameter(2, referer);
			return (UserInvitation) q.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			throw new DBRecordNotFoundException(e);
		}
	}
	
	public UserInvitation findInvitation(String token)
			throws DBRecordNotFoundException {
		try {
			Query q = entityManager
					.createQuery("select e from UserInvitation e where e.token = ?1");
			q.setParameter(1, token);
			return (UserInvitation) q.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			throw new DBRecordNotFoundException(e);
		}
	}

}
