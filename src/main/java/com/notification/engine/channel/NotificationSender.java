package com.notification.engine.channel;

import com.notification.engine.model.NotificationPayload;
import com.notification.engine.model.NotificationType;

public interface NotificationSender {
void send(NotificationPayload payload); 

NotificationType supportsType();
	
}
