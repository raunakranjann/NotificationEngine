package com.notification.engine.core;

import java.util.*;
import java.util.concurrent.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.notification.engine.channel.NotificationSender;
import com.notification.engine.model.NotificationPayload;
import com.notification.engine.model.NotificationType;


@Service
public class NotificationEngine {

	
	private static final Logger log = LoggerFactory.getLogger(NotificationEngine.class);
	
	
	
	//1. Thread safe Queue jisme tasks jama honge (Capacity : 100)
	
	private final BlockingQueue<NotificationPayload> queue = new LinkedBlockingQueue<>(100);
	
	//2. Fixed Thread Pool - sirf 4 background worker threads chalenge 
	
	private final ExecutorService workerPool = Executors.newFixedThreadPool(4);
	
	//3. Sender strategies (Email, SMS, Push)
	
	private final Map<NotificationType, NotificationSender> senders = new HashMap<>();
	private volatile boolean isRunning = true;
	
	public NotificationEngine(List<NotificationSender> senderList) {
		
		for(NotificationSender sender : senderList) {
			senders.put(sender.supportsType(), sender);
			
		}
		
		log.info("NotificationEngine initialized with channels : {}", senders.keySet());
		
		startWorkerConsumers();
		
		
	}
	
	//Producer Method : Bahar se koi bhi task queue mein daal skega 
	
	public boolean submitNotification(NotificationPayload payload) {
		
		//offer() non-blocking tareeka se queue mein insert karta hai 
		boolean queued = queue.offer(payload);
		
		if(queued) {
			log.info("Task queued successfully. Current queue depth : {}", queue.size());
		}
		else
		{
			log.warn("Queue is full! Dropping notification ID : {}", payload.getId());
		}
		return queued;
	}
	
	//Consumer Workers start karna 
	
	private void startWorkerConsumers() {
		//Pool ke 4 threads lagatar queue ko poll krenge 
		
		for(int i =0; i<4; i++) {
			workerPool.submit(()->{
				while(isRunning || !queue.isEmpty()) {
					try {
						//take () thread ko block/wait krega jab tak queue me task na aye
						NotificationPayload task = queue.poll(500, TimeUnit.MILLISECONDS);
						if(task!= null) {
							processTask(task);
						}
					
						} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						log.error("Worker thread was interrupted", e);
						break;
					}
				}
			});
		}
	}
	
	private void processTask(NotificationPayload task) {
		NotificationSender sender = senders.get(task.getType());
		
		if(sender != null) 
		{
			sender.send(task);
			
		}
		else
		{
			log.error("No sender configured for notification type : {}", task.getType());
					
//			System.err.println("No sender configured for type : " + task.getType());
		}
	}
	
	
	
	
	
	public void shutdown() {
		log.info("Shutting down NotificationEngine worker pool...");
		this.isRunning = false;
		workerPool.shutdown();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
