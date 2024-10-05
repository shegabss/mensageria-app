package com.mensageria.app.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final List<String> lowPriorityMessages = new ArrayList<>();
    private final List<String> mediumPriorityMessages = new ArrayList<>();
    private final int LOW_PRIORITY_LIMIT = 5; // Limite de mensagens de baixa prioridade
    private final int MEDIUM_PRIORITY_LIMIT = 3; // Limite de mensagens de média prioridade

    public NotificationProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotification(String message, String priority) {
        System.out.println("Chamando sendNotification com mensagem: " + message + ", prioridade: " + priority);

        if (priority.equals("alta")) {
            kafkaTemplate.send("notifications", priority, message);
        } else if (priority.equals("média")) {
            mediumPriorityMessages.add(message);
            if (mediumPriorityMessages.size() >= MEDIUM_PRIORITY_LIMIT) {
                sendBatchNotifications(mediumPriorityMessages, priority);
                mediumPriorityMessages.clear(); // Limpar após enviar
            }
        } else if (priority.equals("baixa")) {
            lowPriorityMessages.add(message);
            if (lowPriorityMessages.size() >= LOW_PRIORITY_LIMIT) {
                sendBatchNotifications(lowPriorityMessages, priority);
                lowPriorityMessages.clear(); // Limpar após enviar
            }
        }
    }

    public void sendBatchNotifications(List<String> messages, String priority) {
        String batchMessage = String.join(", ", messages);
        kafkaTemplate.send("notifications", priority, batchMessage);
    }
}
