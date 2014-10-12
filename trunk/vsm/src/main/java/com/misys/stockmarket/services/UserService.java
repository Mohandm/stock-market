package com.misys.stockmarket.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.dao.UserDAO;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.exception.DBRecordNotFoundException;
import com.misys.stockmarket.exception.EmailNotFoundException;

@Service("userService")
@Repository
public class UserService {

	@Inject
	private UserDAO userDAO;

	public void saveUser(UserMaster userMaster) {
		userDAO.persist(userMaster);
	}

	public void updateUser(UserMaster userMaster) {
		userDAO.update(userMaster);
	}

	public UserMaster findById(Long userId) {
		return userDAO.findById(UserMaster.class, userId);
	}

	public List<UserMaster> findAll() {
		return userDAO.findAll(UserMaster.class);
	}

	public UserMaster findByEmail(String email) throws EmailNotFoundException {
		try {
			return userDAO.findByEmail(email);
		} catch (DBRecordNotFoundException e) {
			throw new EmailNotFoundException(e);
		}
	}
	
	/**
	 * Get logged in user
	 * @return
	 * @throws EmailNotFoundException 
	 */
	public UserMaster getLoggedInUser() throws EmailNotFoundException {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		// Retrieve user from database
		UserMaster user = findByEmail(email);
		return user; 
	}

}
