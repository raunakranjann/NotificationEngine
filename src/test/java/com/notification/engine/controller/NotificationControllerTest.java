package com.notification.engine.controller;

import com.notification.engine.core.NotificationEngine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should return 202 ACCEPTED when valid payload is submitted")
    void shouldReturn202WhenPayloadIsValid() throws Exception {
        String validJson = """
                {
                    "id": "NOTIF-001",
                    "recipient": "alex@example.com",
                    "message": "Security code is 445521",
                    "type": "EMAIL"
                }
                """;

        mockMvc.perform(post("/api/v1/notifications/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validJson))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.status").value("ACCEPTED"))
                .andExpect(jsonPath("$.message").value("Notification queued for delivery"))
                .andExpect(jsonPath("$.recipient").value("alex@example.com"));
    }

    @Test
    @DisplayName("Should return 400 BAD REQUEST when mandatory fields are missing")
    void shouldReturn400WhenRecipientIsBlank() throws Exception {
        // Recipient khali choda hai validation fail trigger karne ke liye
        String invalidJson = """
                {
                    "id": "NOTIF-002",
                    "recipient": "",
                    "message": "Invalid alert",
                    "type": "EMAIL"
                }
                """;

        mockMvc.perform(post("/api/v1/notifications/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.details.recipient").value("Recipient cannot be empty"));
    }
}