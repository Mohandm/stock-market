package com.misys.stockmarket.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.dao.UserDAO;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.exception.DAOException;
import com.misys.stockmarket.exception.DBRecordNotFoundException;
import com.misys.stockmarket.exception.EmailNotFoundException;
import com.misys.stockmarket.exception.service.UserServiceException;
import com.misys.stockmarket.security.LoginResponse;

@Service("userService")
@Repository
public class UserService {

	@Inject
	private UserDAO userDAO;

	public void saveUser(UserMaster userMaster) throws UserServiceException {
		try {
			userDAO.persist(userMaster);
		} catch (DAOException e) {
			throw new UserServiceException(e);
		}
	}

	@Transactional(rollbackFor = DAOException.class)
	public void updateUser(UserMaster userMaster) throws UserServiceException {
		try {
			userDAO.update(userMaster);
		} catch (DAOException e) {
			throw new UserServiceException(e);
		}
	}

	public UserMaster findById(Long userId) throws UserServiceException {
		try {
			return userDAO.findById(UserMaster.class, userId);
		} catch (DAOException e) {
			throw new UserServiceException(e);
		}
	}

	public List<UserMaster> findAll() throws UserServiceException {
		try {
			return userDAO.findAll(UserMaster.class);
		} catch (DAOException e) {
			throw new UserServiceException(e);
		}
	}

	public List<UserMaster> findAllPendingUserActivationEmailNotifications()
			throws UserServiceException {
		try {
			Map<String, Object> criteria = new HashMap<String, Object>();
			criteria.put("active", IApplicationConstants.USER_DEACTIVATED);
			criteria.put("verified", IApplicationConstants.EMAIL_VERIFIED_NO);
			return userDAO.findByFilter(UserMaster.class, criteria);
		} catch (DAOException e) {
			throw new UserServiceException(e);
		}
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
	 * 
	 * @return
	 * @throws EmailNotFoundException
	 */
	public UserMaster getLoggedInUser() throws EmailNotFoundException {
		String email = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		// Retrieve user from database
		UserMaster user = findByEmail(email);
		return user;
	}

	public LoginResponse getLoggedInUserResponse() {
		try {
			UserMaster user = getLoggedInUser();
			LoginResponse loginResponse = new LoginResponse();
			loginResponse.setEmail(user.getEmail());
			return loginResponse;
		} catch (EmailNotFoundException e) {
			return null;
		}
	}
}
