package com.misys.stockmarket.services.email;

import org.springframework.mail.SimpleMailMessage;

import com.misys.stockmarket.exception.EmailSenderServiceException;

public interface EmailSenderService {

	public void sendEmail(SimpleMailMessage message)
			throws EmailSenderServiceException;
}
