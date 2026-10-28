package com.notification.engine.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notification.engine.core.NotificationEngine;
import com.notification.engine.model.NotificationPayload;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

	private final NotificationEngine notificationEngine;
	
	//Constructor Injection 
	
	
	public NotificationController(NotificationEngine notificationEngine) {
		this.notificationEngine = notificationEngine;
	}
		
		@PostMapping("/send")
		public ResponseEntity<Map<String, String>> sendNotification(@Valid @RequestBody NotificationPayload payload){
			
			//Queue me taks submit kia 
			boolean accepted = notificationEngine.submitNotification(payload);
			
			if(accepted) {
				//HTTP 202 Accepted : "Request le li gyi hai , background me delivery ho rhi hai "
				
				return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(Map.of(
								"status", "ACCEPTED",
								"message", "Notification queued for delivery",
								"recipient", payload.getRecipient()
								));
			} else {
				//Agar queue full ho gayi ho
				return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
						.body(Map.of(
								"status", "REJECTED",
								"message", "Server is busy, queue is full!"));
			}
			
		}
}
	

