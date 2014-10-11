package com.misys.stockmarket.utility;

import org.springframework.mail.SimpleMailMessage;

import com.misys.stockmarket.domain.entity.UserMaster;

public class EmailFormatter {

	public static SimpleMailMessage generateActivationMessage(UserMaster user) {
		String baseURL = PropertiesUtil.getProperty("base.url");
		String email = user.getEmail();
		
		StringBuffer mailContent = new StringBuffer();
		mailContent
				.append("Please click on the below link to activate your profile. \n");

		// Encode the email address and send 
		mailContent.append(baseURL).append("/game#/action/activateprofile/")
				.append(SecurityUtil.encodeValue(email));

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("Account Verification");
		message.setText(mailContent.toString());

		return message;
	}

	public static SimpleMailMessage resetPasswordMessage(UserMaster user, String password) {
		SimpleMailMessage message = new SimpleMailMessage();
		StringBuffer mailContent = new StringBuffer();
		mailContent.append("Your new password is ");

		mailContent.append(password);

		message.setTo(user.getEmail());
		message.setSubject("Password Reset Notification");
		message.setText(mailContent.toString());

		return message;
	}
}
