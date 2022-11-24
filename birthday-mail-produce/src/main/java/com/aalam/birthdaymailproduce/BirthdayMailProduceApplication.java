package com.aalam.birthdaymailproduce;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class BirthdayMailProduceApplication {


	public static void main(String[] args) {
		SpringApplication.run(BirthdayMailProduceApplication.class, args);
	}

}
