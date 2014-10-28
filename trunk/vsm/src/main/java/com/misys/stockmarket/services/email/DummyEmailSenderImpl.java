package com.misys.stockmarket.services.email;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.SimpleMailMessage;

public class DummyEmailSenderImpl implements EmailSenderService {

	private static final Log LOG = LogFactory.getLog(DummyEmailSenderImpl.class);
	@Override
	public void sendEmail(SimpleMailMessage message) {
		
		LOG.info("About to send email to "+message.getTo()[0]);
		LOG.info("The subject is "+message.getSubject());
		LOG.info("The content is "+message.getText());
		LOG.info("Email sent successfully");
	}

}
