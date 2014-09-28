package com.misys.stockmarket.services.email;

import org.springframework.mail.SimpleMailMessage;

public interface EmailSenderService {
	
	void sendEmail (SimpleMailMessage message);
}
