package com.misys.stockmarket.handlers.user;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.exception.BaseException;
import com.misys.stockmarket.exception.LoginException;
import com.misys.stockmarket.mbeans.UserFormBean;
import com.misys.stockmarket.platform.web.ResponseMessage;
import com.misys.stockmarket.services.LoginService;
import com.misys.stockmarket.services.RegistrationService;
import com.misys.stockmarket.services.UserService;
import com.misys.stockmarket.services.email.EmailSenderService;
import com.misys.stockmarket.utility.EmailFormatter;
import com.misys.stockmarket.utility.SecurityUtil;

@Service("userBizHandler")
public class UserBizHandler {

	@Inject
	private UserService userService;

	@Inject
	private EmailSenderService emailSender;

	@Inject
	private RegistrationService registrationService;

	@Inject
	private LoginService loginService;

	private static final Log LOG = LogFactory.getLog(UserBizHandler.class);

	public ResponseMessage registerUser(UserFormBean userFormBean) {
		try {

			registrationService.registerUser(userFormBean);

			return new ResponseMessage(
					ResponseMessage.Type.success,
					"You have been successfully registered. A verification link has been sent to your email. Please verify it to continue playing the game");
		} catch (Exception e) {

			e.printStackTrace();
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
			loginService.changePassword(user.getUserId(), passwordValue);

			// SEND PASSWORD CHANGE EMAIL NOTIFICATION
			SimpleMailMessage message = EmailFormatter
					.resetPasswordMessage(user,passwordValue);

			emailSender.sendEmail(message);

		} catch (Exception e) {
			// Dont throw any errors to the screen to prevent account harvesting
			LOG.warn("Errors while processing reset password", e);
		}
		return new ResponseMessage(
				ResponseMessage.Type.success,
				"The new password has been sent to your registered email. Please log into the application using it.");
	}
	
	public ResponseMessage loginUser (UserFormBean userFormBean) {
		try {
			loginService.validateLogin(userFormBean.getEmail(), userFormBean.getPassword());
			return new ResponseMessage(ResponseMessage.Type.success,
					"You have been successfully logged in.");

		} catch (LoginException e) {
			return new ResponseMessage(ResponseMessage.Type.danger,
					"Unable to login. Please try again!!!");
		}
		
	}
}
