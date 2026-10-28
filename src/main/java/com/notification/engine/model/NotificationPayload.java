package com.notification.engine.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NotificationPayload {

	@NotBlank(message = "Notification ID is mandatory")
	private String id;
	
	@NotBlank(message = "Recipient cannot be empty")
	private String recipient; // Email ya phone number 
	
	@NotBlank(message = "Message body cannot be empty")
	private String message; 
	
	@NotNull(message = "Notification type (EMAIL, SMS, PUSH) must be provided")
	private NotificationType type;
	
	
	
	public NotificationPayload(String id, String recipient, String message, NotificationType type) {
		super();
		this.id = id;
		this.recipient = recipient;
		this.message = message;
		this.type = type;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getRecipient() {
		return recipient;
	}



	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	public NotificationType getType() {
		return type;
	}



	public void setType(NotificationType type) {
		this.type = type;
	}
	
	

	
	
	
	
	
}
