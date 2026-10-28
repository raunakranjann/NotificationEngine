package com.notification.engine.channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.notification.engine.model.NotificationPayload;
import com.notification.engine.model.NotificationType;

@Component
public class SmsSender implements NotificationSender{
	
	private static final Logger log = LoggerFactory.getLogger(SmsSender.class);
	
	@Override
	public void send(NotificationPayload payload) {
		//Industry simulation (Twilio ya aws sms jaisa payload
		
		log.info("[SMS GATEWAY] Sending SMS to Phone : {} | SMS Body : {}", payload.getRecipient(), payload.getMessage());
		
		
	}

	@Override
	public NotificationType supportsType() {
		return NotificationType.SMS;
	}
	
}
