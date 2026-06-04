package com.example.energy_community_user;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EnergyCommunityUserApplication {

	@Bean
	public Queue echoOutQueue() {
		return new Queue("community_energy_user_out", true);
	}

	public static void main(String[] args) {
		SpringApplication.run(EnergyCommunityUserApplication.class, args);
	}

}
