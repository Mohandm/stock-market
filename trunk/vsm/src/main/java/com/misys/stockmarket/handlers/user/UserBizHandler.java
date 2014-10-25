package com.misys.stockmarket.handlers.user;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.enums.VALIDATION_MODE;
import com.misys.stockmarket.exception.BaseException;
import com.misys.stockmarket.exception.UserProfileValidationException;
import com.misys.stockmarket.mbeans.UserFormBean;
import com.misys.stockmarket.platform.web.ResponseMessage;
import com.misys.stockmarket.services.RegistrationService;
import com.misys.stockmarket.services.UserService;
import com.misys.stockmarket.services.email.EmailSenderService;
import com.misys.stockmarket.utility.EmailFormatter;
import com.misys.stockmarket.utility.SecurityUtil;
import com.misys.stockmarket.validator.UserValidator;

@Service("userBizHandler")
public class UserBizHandler {

	@Inject
	private UserService userService;

	@Inject
	private EmailSenderService emailSender;

	@Inject
	private RegistrationService registrationService;

	@Inject
	private UserValidator userValidator;

	@Inject
	private PasswordEncoder passwordEncoder;

	private static final Log LOG = LogFactory.getLog(UserBizHandler.class);

	public ResponseMessage registerUser(UserFormBean userFormBean) {
		try {
			registrationService.registerUser(userFormBean);
			return new ResponseMessage(
					ResponseMessage.Type.success,
					"You have been successfully registered. A verification link has been sent to your email. Please verify it to continue playing the game");
		} catch (UserProfileValidationException e) {
			LOG.error(e);
			return new ResponseMessage(ResponseMessage.Type.danger,
					"There was a validation error while registering. Please try again");

		} catch (Exception e) {
			LOG.error(e);
			return new ResponseMessage(ResponseMessage.Type.danger,
					"There was a technical error while registering. Please try again");
		}
	}

	public ResponseMessage activateProfile(String token) {
		// Decode the email address
		String targetEmail = SecurityUtil.decodeValue(token);
		UserMaster user;
		try {
			user = userService.findByEmail(targetEmail);
			user.setVerified(IApplicationConstants.EMAIL_VERIFIED_YES);
			user.setActive(IApplicationConstants.USER_ACTIVATED);
			userService.updateUser(user);
			return new ResponseMessage(ResponseMessage.Type.success,
					"Your account has been verified. Have fun playing the game.");

		} catch (BaseException e) {
			return new ResponseMessage(ResponseMessage.Type.danger,
					"Your account couldnot be verified. Please try again!!!");
		}
	}

	public ResponseMessage resetPassword(UserFormBean userFormBean) {
		UserMaster user;
		try {
			user = userService.findByEmail(userFormBean.getEmail());
			// Autogenerate password
			String passwordValue = SecurityUtil.autoGeneratePassword(12);
			user.setPassword(passwordEncoder.encode(passwordValue));
			userService.updateUser(user);

			// SEND PASSWORD CHANGE EMAIL NOTIFICATION
			SimpleMailMessage message = EmailFormatter.resetPasswordMessage(
					user, passwordValue);

			emailSender.sendEmail(message);

		} catch (Exception e) {
			// Dont throw any errors to the screen to prevent account harvesting
			LOG.warn("Errors while processing reset password", e);
		}
		return new ResponseMessage(
				ResponseMessage.Type.success,
				"The new password has been sent to your registered email. Please log into the application using it.");
	}

	public ResponseMessage changePassword(UserFormBean userFormBean) {

		// Get logged in user
		try {

			UserMaster user = userService.getLoggedInUser();

			userValidator.isUserMasterDataValid(userFormBean,
					VALIDATION_MODE.CHANGE_PASSWORD, user.getPassword());

			String newPassword = userFormBean.getPassword();
			user.setPassword(passwordEncoder.encode(newPassword));
			user.setActive(IApplicationConstants.USER_ACTIVATED);
			userService.updateUser(user);

		} catch (UserProfileValidationException e) {
			// TODO: Implement error message from exception
			return new ResponseMessage(ResponseMessage.Type.danger,
					"There was some validation errors. Please try again!!!");
		}

		catch (Exception e) {
			e.printStackTrace();
			return new ResponseMessage(ResponseMessage.Type.danger,
					"Unable to change your password. Please try again!!!");
		}
		return new ResponseMessage(
				ResponseMessage.Type.success,
				"The new password has been sent to your registered email. Please log into the application using it.");
	}
}
