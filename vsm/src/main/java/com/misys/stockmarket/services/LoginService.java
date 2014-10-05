package com.misys.stockmarket.services;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.exception.EmailNotFoundException;
import com.misys.stockmarket.exception.InvalidPasswordException;

@Service("loginService")
@Repository
public class LoginService {

	@Inject
	private UserService userService;

	public boolean validateLogin(String email, String password)
			throws EmailNotFoundException, InvalidPasswordException {
		boolean isValid = true;
		UserMaster userMaster = userService.findByEmail(email);
		// CHECK IS EMAIL VERIFIED

		// CHECK IS USER ACTIVE

		// MATCH PASSWORD
		if (!userMaster.getPassword().equals(password)) {
			throw new InvalidPasswordException();
		}
		return isValid;
	}
}
