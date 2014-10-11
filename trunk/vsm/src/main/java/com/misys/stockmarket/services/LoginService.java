package com.misys.stockmarket.services;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.enums.LOGIN_ERR_CODES;
import com.misys.stockmarket.exception.EmailNotFoundException;
import com.misys.stockmarket.exception.LoginException;

@Service("loginService")
@Repository
public class LoginService {

	@Inject
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Method to validate the login credentials
	 * 
	 * @param email
	 * @param password
	 * @return
	 * @throws LoginException
	 */
	public boolean validateLogin(String email, String password)
			throws LoginException {
		boolean isValid = true;
		try {
			UserMaster userMaster = userService.findByEmail(email);
			// CHECK IS EMAIL VERIFIED
			if (IApplicationConstants.EMAIL_VERIFIED_NO.equals(userMaster
					.getVerified())) {
				throw new LoginException(LOGIN_ERR_CODES.EMAIL_NOT_VERIFIED);
			}
			// CHECK IS USER ACTIVE
			if (IApplicationConstants.USER_DEACTIVATED.equals(userMaster
					.getActive())) {
				throw new LoginException(LOGIN_ERR_CODES.USER_STATUS_INACTIVE);
			}
			// MATCH PASSWORD
			if (!userMaster.getPassword().equals(password)) {
				throw new LoginException(LOGIN_ERR_CODES.WRONG_PASSWORD);
			}
		} catch (EmailNotFoundException e) {
			throw new LoginException(LOGIN_ERR_CODES.WRONG_EMAIL_ID);
		}
		return isValid;
	}

	public boolean changePassword(Long userId, String newPassword)
			throws LoginException {
		boolean isPasswordChangeSucessfull = true;
		UserMaster userMaster = userService.findById(userId);
		if (userMaster != null) {
			try {
				userMaster.setPassword(passwordEncoder.encode(newPassword));
				userMaster.setActive(IApplicationConstants.USER_PASSWORD_EXPIRED);
				userService.updateUser(userMaster);
			} catch (Exception e) {
				throw new LoginException(LOGIN_ERR_CODES.UNKNOWN);
			}
		} else {
			throw new LoginException(LOGIN_ERR_CODES.USER_NOT_FOUND_BY_ID);
		}
		return isPasswordChangeSucessfull;
	}
}
