package com.mensageria.app.controller;

import com.mensageria.app.producer.NotificationProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationProducer notificationProducer;

    @Autowired
    public NotificationController(NotificationProducer notificationProducer) {
        this.notificationProducer = notificationProducer;
    }

    @PostMapping
    public void sendNotification(@RequestParam String message, @RequestParam String priority) {
        // Direcionar mensagens de acordo com a prioridade
        notificationProducer.sendNotification(message, priority);
    }
}
