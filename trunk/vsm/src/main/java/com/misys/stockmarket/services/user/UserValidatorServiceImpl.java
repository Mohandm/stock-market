package com.misys.stockmarket.services.user;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.misys.stockmarket.mbeans.common.FormBean;
import com.misys.stockmarket.model.User;

@Service("userValidatorService")
class UserValidatorServiceImpl implements UserValidatorService {

	@Inject
	private UserManager userManager;

	@Override
	public void validateBean(FormBean bean) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isUniqueEmail(String email) {
		boolean isUnique = true;
		for (User users : userManager.getUserList()) {
			if (users.getEmailAddress().equals(email)) {
				isUnique = false;
				break;
			}
		}
		return isUnique;
	}

	@Override
	public boolean isValidPassword(String password, User user) {
		if (password.equals(user.getPassword()))
		{
			return true;
		}
		return false;
	}

}
