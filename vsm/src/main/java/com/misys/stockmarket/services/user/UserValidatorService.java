package com.misys.stockmarket.services.user;

import com.misys.stockmarket.model.User;
import com.misys.stockmarket.services.core.ValidatorService;


public interface UserValidatorService extends ValidatorService {
	
	public boolean isUniqueEmail(String email);
	
	public boolean isValidPassword (String password, User user);

}
