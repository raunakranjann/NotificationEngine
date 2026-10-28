package com.notification.engine.channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.notification.engine.model.NotificationPayload;
import com.notification.engine.model.NotificationType;


@Component
public class EmailSender implements NotificationSender{

	
	private static final Logger log = LoggerFactory.getLogger(EmailSender.class);
	
	
	
	@Override
	public void send(NotificationPayload payload) {
		
		
		log.info("Sending Email to : {} | Message : {}" , payload.getRecipient(), payload.getMessage());
		
//		//Real life mein yaha SendGrid ya JavaMailSender aayega
//		
//		System.out.println("[Email thread : " + Thread.currentThread() + "] Sending Email to : " 
//				+ payload.getRecipient() + "| Body : " + payload.getMessage());
	}
	
	@Override 
	public NotificationType supportsType() {
		return NotificationType.EMAIL;
	}
}
