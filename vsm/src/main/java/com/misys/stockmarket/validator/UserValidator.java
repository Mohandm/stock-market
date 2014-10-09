package com.misys.stockmarket.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.misys.stockmarket.enums.USER_PROFILE_ERR_CODES;
import com.misys.stockmarket.enums.VALIDATION_MODE;
import com.misys.stockmarket.exception.UserProfileValidationException;
import com.misys.stockmarket.mbeans.UserFormBean;

public class UserValidator {

	private static final Log LOG = LogFactory.getLog(UserValidator.class);
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	/**
	 * 
	 * @param userFormBean
	 * @param mode
	 * @return
	 * @throws UserProfileValidationException
	 */
	public static boolean isUserMasterDataValid(UserFormBean userFormBean,
			VALIDATION_MODE mode) throws UserProfileValidationException {
		boolean isValid = true;
		switch (mode) {
		case SAVE:
			// CHECK IS FIRST NAME EMPTY
			if (userFormBean.getFirstName() == null
					|| userFormBean.getFirstName().isEmpty()) {
				LOG.debug("First Name Required");
				isValid = false;
				throw new UserProfileValidationException(
						USER_PROFILE_ERR_CODES.VALUE_REQUIRED);
			}
			// CHECK EMAIL IS EMPTY
			if (userFormBean.getEmail() == null
					|| userFormBean.getEmail().isEmpty()) {
				LOG.debug("Email Address Required");
				isValid = false;
				throw new UserProfileValidationException(
						USER_PROFILE_ERR_CODES.VALUE_REQUIRED);
			}
			if (userFormBean.getPassword() == null
					|| userFormBean.getPassword().isEmpty()) {
				LOG.debug("Password Required");
				isValid = false;
				throw new UserProfileValidationException(
						USER_PROFILE_ERR_CODES.VALUE_REQUIRED);
			}
			if (userFormBean.getConfirmPassword() == null
					|| userFormBean.getConfirmPassword().isEmpty()) {
				LOG.debug("Confirm Password Required");
				isValid = false;
				throw new UserProfileValidationException(
						USER_PROFILE_ERR_CODES.VALUE_REQUIRED);
			}
			// CHECK IS EMAIL ADDRESS VALID
			Pattern pattern = Pattern.compile(EMAIL_PATTERN);
			Matcher matcher = pattern.matcher(userFormBean.getEmail());
			if (!matcher.matches()) {
				LOG.debug("Invalid Email Address");
				isValid = false;
				throw new UserProfileValidationException(
						USER_PROFILE_ERR_CODES.INVALID_EMAIL_ADDRESS);
			}
			// CHECK IS CHANGE PASSWORD AND CONFIRM PASSWORD SAME
			if (!userFormBean.getPassword().equals(
					userFormBean.getConfirmPassword())) {
				LOG.debug("Password and confirm password Not Same");
				isValid = false;
				throw new UserProfileValidationException(
						USER_PROFILE_ERR_CODES.PASSWORD_CONFIRM_PASSWORD_NOT_SAME);
			}
			break;
		case UPDATE:
			break;
		default:
			break;
		}
		return isValid;
	}
}
