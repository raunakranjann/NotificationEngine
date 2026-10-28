package com.notification.engine.channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.notification.engine.model.NotificationPayload;
import com.notification.engine.model.NotificationType;


@Component
public class PushSender implements NotificationSender{

	private static final Logger log = LoggerFactory.getLogger(PushSender.class);
	
	@Override
	public void send(NotificationPayload payload) {
		//Industry simulation (Firebase FCM / Apple APNS notification)
		
		log.info("[PUSH GATEWAY - FCM] Dispatching Push Alert to Device Token : {} | Data : {}",
				payload.getRecipient(), payload.getMessage());
		
	}
	
	@Override
	public NotificationType supportsType() {
		return NotificationType.PUSH;
	}
	
}
