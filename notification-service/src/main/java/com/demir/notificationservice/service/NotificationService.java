package com.demir.notificationservice.service;

import com.demir.notificationservice.entity.User;
import com.demir.notificationservice.event.OrderPlacedEvent;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
class NotificationService {

    private final JavaMailSender mailSender;
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public NotificationService(JavaMailSender mailSender, WebClient.Builder webClientBuilder) {
        this.mailSender = mailSender;
        this.webClientBuilder = webClientBuilder;
    }

    @KafkaListener(topics = "notificationTopic")
    public void handleNotification(OrderPlacedEvent orderPlacedEvent) {
        // Obtain the user's email using the user ID from the order service
        Long userId = orderPlacedEvent.getUserId();
        String userEmail = getUserEmail(userId);

        // Send out an email notification
        sendEmail(userEmail, orderPlacedEvent.getOrderId());

        log.info("Received notification for the order - {}", orderPlacedEvent.getOrderId());
    }

    private String getUserEmail(Long userId) {

       return webClientBuilder.build().get()
                .uri("http://user-service/users/by-id/" + userId)
                .retrieve()
                .bodyToMono(User.class)
                .map(User::getEmail)
                .block();
    }


    private void sendEmail(String recipientEmail, Long orderId) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(recipientEmail);
            helper.setSubject("Order Notification");
            helper.setText("Your order with number " + orderId + " has been placed successfully.");

            mailSender.send(message);
        } catch (Exception e) {
            log.error("Failed to send email notification", e);
        }
    }
}