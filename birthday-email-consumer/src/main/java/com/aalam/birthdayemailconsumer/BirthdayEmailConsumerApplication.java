package com.aalam.birthdayemailconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class BirthdayEmailConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BirthdayEmailConsumerApplication.class, args);
	}

}
