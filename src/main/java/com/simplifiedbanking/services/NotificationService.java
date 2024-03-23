package com.simplifiedbanking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.simplifiedbanking.domain.user.User;
import com.simplifiedbanking.dtos.NotificationDTO;

@Service
public class NotificationService {
  @Autowired
  private RestTemplate restTemplate;

  @Value("${transaction.notification.url}")
  private String notificationUrl;

  public void sendNotification(User user, String message) throws Exception {
    String email = user.getEmail();
    NotificationDTO notificationRequest = new NotificationDTO(email, message);

    ResponseEntity<String> notificationResponse = restTemplate.postForEntity(this.notificationUrl, notificationRequest, String.class);

    if (notificationResponse.getStatusCode() != HttpStatus.CREATED) {
      System.out.println("Error sending notification.");
      throw new Exception("Notification Service is unvailable.");
    }
  }
}
