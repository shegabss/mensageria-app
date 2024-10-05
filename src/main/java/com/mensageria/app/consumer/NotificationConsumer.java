package com.mensageria.app.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationConsumer {
    @KafkaListener(topics = "notifications", groupId = "notification-group", containerFactory = "batchListenerFactory")
    public void listen(List<String> messages) {
        for (String message : messages) {
            processNotification(message);
        }
    }

    private void processNotification(String message) {
        // Lógica de envio da notificação para os usuários
        System.out.println("Notificação recebida: " + message);
    }
}

