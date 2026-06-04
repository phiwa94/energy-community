package com.example.energy_community_producer;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EnergyCommunityProducerApplication {

	@Bean
	public Queue echoOutQueue() {
		return new Queue("community_energy_producer_out", true);
	}

	public static void main(String[] args) {
		SpringApplication.run(EnergyCommunityProducerApplication.class, args);
	}

}
