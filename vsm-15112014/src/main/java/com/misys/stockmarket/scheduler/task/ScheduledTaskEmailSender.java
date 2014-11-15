package com.misys.stockmarket.scheduler.task;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.exception.EmailSenderServiceException;
import com.misys.stockmarket.exception.service.UserServiceException;
import com.misys.stockmarket.services.UserService;
import com.misys.stockmarket.services.email.EmailSenderService;
import com.misys.stockmarket.utility.EmailFormatter;

@Component
@EnableScheduling
public class ScheduledTaskEmailSender {

	private static final Log LOG = LogFactory
			.getLog(ScheduledTaskEmailSender.class);

	@Inject
	private UserService userService;

	@Inject
	private EmailSenderService emailSender;

	@Scheduled(fixedRateString = "${scheduler.task.EmailSender.frequency}")
	public void sendUserActivationEmailNotification() {
		String email = null;
		try {
			List<UserMaster> userMasterList = userService
					.findAllPendingUserActivationEmailNotifications();
			for (UserMaster userMaster : userMasterList) {
				// SEND ACTIVATION ACCOUNT EMAIL NOTIFICATION
				email = userMaster.getEmail();
				LOG.info("Sending User Verification Email Notificatio to Email Address: "
						+ email);
				SimpleMailMessage message = EmailFormatter
						.generateActivationMessage(userMaster);
				emailSender.sendEmail(message);
				LOG.info("User Verfication Email Notification has been sent to email address: "
						+ email);
				userMaster.setActive(IApplicationConstants.USER_EMAIL_SENT);
				userService.updateUser(userMaster);
			}
		} catch (UserServiceException e) {
			LOG.error(
					"Encountered Error while sending User Verfication Email Notification to email address: "
							+ email, e);
		} catch (EmailSenderServiceException e) {
			LOG.error(
					"Encountered Error while sending User Verfication Email Notification to email address: "
							+ email, e);
		}
	}
}
