package com.org.utils;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.org.constants.AppConstants;

@Component
public class EmailUtils {

	@Autowired
	private JavaMailSender mailSender;
	
	private Logger logger = LoggerFactory.getLogger(EmailUtils.class);
	
	public boolean sendEmail(String to, String subject, String body) {
		boolean isSent = false;
		try {
			
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			
			helper.setFrom(AppConstants.FROM_EMAIL_ADDRESS);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);
			
			mailSender.send(mimeMessage);
			
			isSent = true;
		}catch(Exception e) {
			String error = e.getMessage();
			logger.error(error);
			logger.error(AppConstants.MAIL_ERROR);
		}
		
		return isSent;
	}
}
