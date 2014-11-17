package com.misys.stockmarket.utility;

import org.springframework.mail.SimpleMailMessage;

import com.misys.stockmarket.domain.entity.UserInvitation;
import com.misys.stockmarket.domain.entity.UserMaster;

public class EmailFormatter {

	public static SimpleMailMessage generateActivationMessage(UserMaster user) {
		String baseURL = PropertiesUtil.getProperty("base.url");
		String email = user.getEmail();

		StringBuilder mailContent = new StringBuilder();
		mailContent
				.append("Please click on the below link to activate your profile. \n");

		// Encode the email address and send
		mailContent.append(baseURL).append("#/action/activateprofile/")
				.append(SecurityUtil.encodeValue(email));

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("Account Verification");
		message.setText(mailContent.toString());

		return message;
	}

	public static SimpleMailMessage generateInvitationMessage(
			UserInvitation userInvitation) {
		String baseURL = PropertiesUtil.getProperty("base.url");
		String email = userInvitation.getEmail();
		UserMaster referer = userInvitation.getReferer();

		StringBuilder mailContent = new StringBuilder();
		mailContent.append("You have been invited by your friend ")
				.append(referer.getFirstName())
				.append(" to play this awesome game. \n");
		mailContent
				.append("Please click on the below link to accept the invitation. \n");

		// Encode the email address and send
		mailContent.append(baseURL).append("#/action/acceptinvitation/")
				.append(userInvitation.getToken());

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("Invitation Message From " + referer.getFirstName());
		message.setText(mailContent.toString());

		return message;
	}

	public static SimpleMailMessage generateWatchListAlertsMessage(
			UserMaster user, String messageText) {
		String email = user.getEmail();

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("Stock Price Alert");
		message.setText(messageText);

		return message;
	}

	public static SimpleMailMessage resetPasswordMessage(UserMaster user,
			String password) {
		SimpleMailMessage message = new SimpleMailMessage();
		StringBuilder mailContent = new StringBuilder();
		mailContent.append("Your new password is ");

		mailContent.append(password);

		message.setTo(user.getEmail());
		message.setSubject("Password Reset Notification");
		message.setText(mailContent.toString());

		return message;
	}
}
