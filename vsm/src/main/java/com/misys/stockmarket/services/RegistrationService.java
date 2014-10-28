package com.misys.stockmarket.services;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.enums.VALIDATION_MODE;
import com.misys.stockmarket.exception.BaseException;
import com.misys.stockmarket.exception.DAOException;
import com.misys.stockmarket.exception.LeagueException;
import com.misys.stockmarket.exception.UserProfileValidationException;
import com.misys.stockmarket.exception.service.UserServiceException;
import com.misys.stockmarket.mbeans.UserFormBean;
import com.misys.stockmarket.platform.web.ResponseMessage;
import com.misys.stockmarket.services.email.EmailSenderService;
import com.misys.stockmarket.utility.EmailFormatter;
import com.misys.stockmarket.utility.SecurityUtil;
import com.misys.stockmarket.validator.UserValidator;

@Service("registrationService")
@Repository
public class RegistrationService {

	private static final Log LOG = LogFactory.getLog(RegistrationService.class);

	@Inject
	private UserService userService;

	@Inject
	private EmailSenderService emailSender;

	@Inject
	private UserValidator userValidator;

	@Inject
	private LeagueService leagueService;

	@Inject
	private PasswordEncoder passwordEncoder;

	@Transactional(rollbackFor = DAOException.class)
	public ResponseMessage registerUser(UserFormBean userFormBean) {

		// LINK TO DEFAULT GROUP
		try {

			// VALIDATE USER MASTER DATA
			userValidator.isUserMasterDataValid(userFormBean,
					VALIDATION_MODE.SAVE, null);

			// EMAIL ADRESS OK TO REGISTER
			UserMaster userMaster = new UserMaster();
			userMaster.setFirstName(userFormBean.getFirstName());
			userMaster.setLastName(userFormBean.getLastName());
			userMaster.setEmail(userFormBean.getEmail());
			userMaster.setPassword(passwordEncoder.encode(userFormBean
					.getPassword()));
			userMaster.setVerified(IApplicationConstants.EMAIL_VERIFIED_NO);
			userMaster.setActive(IApplicationConstants.USER_DEACTIVATED);
			userService.saveUser(userMaster);

			leagueService.addUserToDefaultLeague(userMaster);

			// SEND ACTIVATION ACCOUNT EMAIL NOTIFICATION
			SimpleMailMessage message = EmailFormatter
					.generateActivationMessage(userMaster);

			emailSender.sendEmail(message);
			return new ResponseMessage(
					ResponseMessage.Type.success,
					"You have been successfully registered. A verification link has been sent to your email. Please verify it to continue playing the game");
		} catch (UserProfileValidationException e) {
			LOG.error("There was a validation error while registering", e);
			return new ResponseMessage(ResponseMessage.Type.danger,
					"There was a validation error while registering. Please try again");
		} catch (LeagueException e) {
			LOG.error("The default league is not installed", e);
			return new ResponseMessage(ResponseMessage.Type.danger,
					"The default league is not installed. Please try again");
		} catch (UserServiceException e) {
			LOG.error("The default league is not installed", e);
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

	@PreAuthorize("hasRole('ROLE_USER')")
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
		} catch (Exception e) {
			LOG.error("Error while changing pasword ", e);
			return new ResponseMessage(ResponseMessage.Type.danger,
					"Unable to change your password. Please try again!!!");
		}
		return new ResponseMessage(
				ResponseMessage.Type.success,
				"The new password has been sent to your registered email. Please log into the application using it.");
	}
}
