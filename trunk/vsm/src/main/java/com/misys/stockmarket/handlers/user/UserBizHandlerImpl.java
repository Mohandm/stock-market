package com.misys.stockmarket.handlers.user;

import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.mbeans.user.UserRegFormBean;
import com.misys.stockmarket.model.User;
import com.misys.stockmarket.platform.web.ResponseMessage;
import com.misys.stockmarket.services.email.EmailSenderService;
import com.misys.stockmarket.services.user.UserManager;
import com.misys.stockmarket.services.user.UserValidatorService;
import com.misys.stockmarket.utility.EmailFormatter;

@Service("userBizHandler")
public class UserBizHandlerImpl implements UserBizHandler {

	@Inject
	private EmailSenderService emailSender;

	@Inject
	private UserValidatorService userValidator;

	@Inject
	private UserManager userManager;

	private static final Log LOG = LogFactory.getLog(UserBizHandlerImpl.class);

	@Override
	public ResponseMessage registerUser(UserRegFormBean userRegFormBean) {
		try {
			userValidator.validateBean(userRegFormBean);
			
			User newUser = new User();
			newUser.completeWith(userRegFormBean);
			userManager.addUser(newUser);

			SimpleMailMessage message = EmailFormatter
					.generateActivationMessage(newUser);

			emailSender.sendEmail(message);

			return new ResponseMessage(
					ResponseMessage.Type.success,
					"You have been successfully registered. A verification link has been sent to your email. Please verify it to continue playing the game");
		} catch (Exception e) {

			e.printStackTrace();
			return new ResponseMessage(ResponseMessage.Type.danger,
					"There was a technical error while registering. Please try again");
		}
	}

	@Override
	public ResponseMessage activateProfile(String token) {
		// TODO: Use decryption
		String targetEmail = token;
		User user = userManager.getUserByEmail(targetEmail);

		if (user != null) {
			user.setActive(true);
			userManager.updateUser(user);
			return new ResponseMessage(ResponseMessage.Type.success,
					"Your account has been verified. Have fun playing the game.");
		} else {
			return new ResponseMessage(ResponseMessage.Type.danger,
					"Your account couldnot be verified. Please try again!!!");
		}
	}

	@Override
	public ResponseMessage resetPassword(UserRegFormBean userRegFormBean) {

		try {
			User user = userManager.getUserByEmail(userRegFormBean
					.getEmailAddress());

			if (user != null) {
				user.setPassword(RandomStringUtils.randomAlphanumeric(8));
				userManager.updateUser(user);
				SimpleMailMessage message = EmailFormatter
						.resetPasswordMessage(user);
				emailSender.sendEmail(message);
			} else {
				// Ignore anyway
				LOG.warn("No user found with email address "
						+ userRegFormBean.getEmailAddress());
			}

		} catch (Exception e) {
			// Dont throw any errors to the screen to prevent account harvesting
			LOG.warn("Errors while processing reset password", e);
		}
		return new ResponseMessage(
				ResponseMessage.Type.success,
				"The new password has been sent to your registered email. Please log into the application using it.");

	}
}
