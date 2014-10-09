package com.misys.stockmarket.services;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.enums.USER_PROFILE_ERR_CODES;
import com.misys.stockmarket.enums.VALIDATION_MODE;
import com.misys.stockmarket.exception.EmailNotFoundException;
import com.misys.stockmarket.exception.UserProfileValidationException;
import com.misys.stockmarket.mbeans.UserFormBean;
import com.misys.stockmarket.validator.UserValidator;

@Service("registrationService")
@Repository
public class RegistrationService {

	@Inject
	private UserService userService;

	public void registerUser(UserFormBean userFormBean)
			throws UserProfileValidationException {
		// VALIDATE USER MASTER DATA
		if (UserValidator.isUserMasterDataValid(userFormBean,
				VALIDATION_MODE.SAVE)) {
			// VALIDATE EMAIL ALREADY REGISTERED
			try {
				userService.findByEmail(userFormBean.getEmail());
				// THROW EXCEPTION EMAIL ALREADY REGISTERED
				throw new UserProfileValidationException(
						USER_PROFILE_ERR_CODES.EMAIL_ALREADY_REGISTERED);
			} catch (EmailNotFoundException emailNotFoundException) {
				// EMAIL ADRESS OK TO REGISTER
				UserMaster userMaster = new UserMaster();
				userMaster.setFirstName(userFormBean.getFirstName());
				userMaster.setLastName(userFormBean.getLastName());
				userMaster.setEmail(userFormBean.getEmail());
				userMaster.setPassword(userFormBean.getPassword());
				userMaster.setVerified(IApplicationConstants.EMAIL_VERIFIED_NO);
				userMaster.setActive(IApplicationConstants.USER_ACTIVE_NO);
				userService.saveUser(userMaster);
				// TODO:SEND ACTIVATION ACCOUNT EMAIL NOTIFICATION
			}

		}

	}
}
