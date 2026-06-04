package com.example.energy_community_user;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessagingService {
    private final RabbitTemplate rabbit;

    public MessagingService(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    public void sendMessage(String message) {
        System.out.println("Message: " + message);
        this.rabbit.convertAndSend("community_energy_user_out", message);
    }
}
