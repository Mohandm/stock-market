package com.misys.stockmarket.utility;

import org.springframework.mail.SimpleMailMessage;

import com.misys.stockmarket.model.User;

public class EmailFormatter {

	public static SimpleMailMessage generateActivationMessage(User user) {
		SimpleMailMessage message = new SimpleMailMessage();
		String baseURL = PropertiesUtil.getProperty("base.url");
		StringBuffer mailContent = new StringBuffer();
		mailContent
				.append("Please click on the below link to activate your profile. \n");

		// TODO: Encrypt the email message
		mailContent.append(baseURL).append("/#/action/activateprofile/")
				.append(user.getEmailAddress());

		message.setTo(user.getEmailAddress());
		message.setSubject("Account Verification");
		message.setText(mailContent.toString());

		return message;
	}
	
	public static SimpleMailMessage resetPasswordMessage(User user) {
		SimpleMailMessage message = new SimpleMailMessage();
		StringBuffer mailContent = new StringBuffer();
		mailContent
				.append("Your new password is ");

		mailContent.append(user.getPassword());

		message.setTo(user.getEmailAddress());
		message.setSubject("Password Reset Notification");
		message.setText(mailContent.toString());

		return message;
	}
}
