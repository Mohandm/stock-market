package com.misys.stockmarket.services.email;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

//@Service("emailSenderService")
public class JavaEmailSenderServiceImpl implements EmailSenderService {

	private static final Log LOG = LogFactory
			.getLog(JavaEmailSenderServiceImpl.class);
	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void sendEmail(SimpleMailMessage message) {
		try {
			LOG.info("About to send email to " + message.getTo()[0]);
			message.setFrom("bipin.poudyal@misys.com");
			mailSender.send(message);
			LOG.info("Email successfully sent");
		} catch (Exception e) {
			LOG.error("Error while sending email", e);
		}
	}

}
