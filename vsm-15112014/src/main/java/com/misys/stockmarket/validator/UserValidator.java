package com.misys.stockmarket.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.enums.USER_PROFILE_ERR_CODES;
import com.misys.stockmarket.enums.VALIDATION_MODE;
import com.misys.stockmarket.exception.EmailNotFoundException;
import com.misys.stockmarket.exception.UserProfileValidationException;
import com.misys.stockmarket.mbeans.UserFormBean;
import com.misys.stockmarket.services.UserService;

@Repository
@Service("userValidator")
public class UserValidator {

	private static final Log LOG = LogFactory.getLog(UserValidator.class);
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@Inject
	private UserService userService;

	@Inject
	private PasswordEncoder passwordEncoder;

	/**
	 * 
	 * @param userFormBean
	 * @param mode
	 * @param encryptedDBPassword
	 * @return
	 * @throws UserProfileValidationException
	 */
	public void isUserMasterDataValid(UserFormBean userFormBean,
			VALIDATION_MODE mode, String encryptedDBPassword)
			throws UserProfileValidationException {
		switch (mode) {
		case SAVE:
			validateSave(userFormBean);
			break;
		case UPDATE:
			break;
		case CHANGE_PASSWORD:
			validateChangePassword(userFormBean, encryptedDBPassword);
			break;
		default:
			break;
		}
	}

	private void validateChangePassword(UserFormBean userFormBean,
			String encryptedDBPassword) throws UserProfileValidationException {

		validateNewPasswordMandatory(userFormBean);
		validateConfirmPasswordMandatory(userFormBean);
		validateOldPasswordMandatory(userFormBean);
		validateNewAndConfirmPasswordValues(userFormBean);
		validateOldPasswordValue(userFormBean, encryptedDBPassword);
	}

	private void validateOldPasswordValue(UserFormBean userFormBean,
			String encryptedDBPassword) throws UserProfileValidationException {

		if (!passwordEncoder.matches(userFormBean.getOldPassword(),
				encryptedDBPassword)) {
			LOG.debug("Old password is incorrect");
			throw new UserProfileValidationException(
					USER_PROFILE_ERR_CODES.INCORRECT_OLD_PASSWORD);

		}
	}

	private void validateSave(UserFormBean userFormBean)
			throws UserProfileValidationException {
		validateFirstNameMandatory(userFormBean);
		validateEmailMandatory(userFormBean);
		validateNewPasswordMandatory(userFormBean);
		validateConfirmPasswordMandatory(userFormBean);
		validateEmailValue(userFormBean);
		validateNewAndConfirmPasswordValues(userFormBean);
	}

	private void validateNewAndConfirmPasswordValues(UserFormBean userFormBean)
			throws UserProfileValidationException {
		// CHECK IS CHANGE PASSWORD AND CONFIRM PASSWORD SAME
		if (!userFormBean.getPassword().equals(
				userFormBean.getConfirmPassword())) {
			LOG.debug("Password and confirm password Not Same");
			throw new UserProfileValidationException(
					USER_PROFILE_ERR_CODES.PASSWORD_CONFIRM_PASSWORD_NOT_SAME);
		}
	}

	private void validateEmailValue(UserFormBean userFormBean)
			throws UserProfileValidationException {
		// CHECK IS EMAIL ADDRESS VALID
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(userFormBean.getEmail());
		if (!matcher.matches()) {
			LOG.debug("Invalid Email Address");
			throw new UserProfileValidationException(
					USER_PROFILE_ERR_CODES.INVALID_EMAIL_ADDRESS);
		}

		// VALIDATE EMAIL ALREADY REGISTERED
		try {
			userService.findByEmail(userFormBean.getEmail());
			// THROW EXCEPTION EMAIL ALREADY REGISTERED
			throw new UserProfileValidationException(
					USER_PROFILE_ERR_CODES.EMAIL_ALREADY_REGISTERED);
		} catch (EmailNotFoundException emailNotFoundException) {
			// Ignore exception
			LOG.debug("The email address is not registered");
		}
	}

	private void validateEmailMandatory(UserFormBean userFormBean)
			throws UserProfileValidationException {
		// CHECK EMAIL IS EMPTY
		if (StringUtils.isEmpty(userFormBean.getEmail())) {
			LOG.debug("Email Address Required");
			throw new UserProfileValidationException(
					USER_PROFILE_ERR_CODES.VALUE_REQUIRED);
		}
	}

	private void validateFirstNameMandatory(UserFormBean userFormBean)
			throws UserProfileValidationException {
		// CHECK IS FIRST NAME EMPTY
		if (StringUtils.isEmpty(userFormBean.getFirstName())) {
			LOG.debug("First Name Required");
			throw new UserProfileValidationException(
					USER_PROFILE_ERR_CODES.VALUE_REQUIRED);
		}
	}

	private void validateConfirmPasswordMandatory(UserFormBean userFormBean)
			throws UserProfileValidationException {
		if (StringUtils.isEmpty(userFormBean.getConfirmPassword())) {
			LOG.debug("Confirm Password Required");
			throw new UserProfileValidationException(
					USER_PROFILE_ERR_CODES.VALUE_REQUIRED);
		}
	}

	private void validateNewPasswordMandatory(UserFormBean userFormBean)
			throws UserProfileValidationException {
		if (StringUtils.isEmpty(userFormBean.getPassword())) {
			LOG.debug("Password Required");
			throw new UserProfileValidationException(
					USER_PROFILE_ERR_CODES.VALUE_REQUIRED);
		}
	}

	private void validateOldPasswordMandatory(UserFormBean userFormBean)
			throws UserProfileValidationException {
		if (StringUtils.isEmpty(userFormBean.getOldPassword())) {
			LOG.debug("Password Required");
			throw new UserProfileValidationException(
					USER_PROFILE_ERR_CODES.VALUE_REQUIRED);
		}
	}
}
