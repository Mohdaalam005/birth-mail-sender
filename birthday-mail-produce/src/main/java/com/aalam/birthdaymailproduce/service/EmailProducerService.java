package com.aalam.birthdaymailproduce.service;

import com.aalam.birthdaymailproduce.entity.EmployeeEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Objects;

@Service
@Log4j2
public class EmailProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final RestTemplate restTemplate;
    @Value("${spring.kafka.topic.name}")
    private String topicName;
    @Value("${url}")
    private String url;


    @Autowired
    public EmailProducerService(KafkaTemplate<String, String> kafkaTemplate, RestTemplate restTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.restTemplate = restTemplate;
    }



    @Scheduled(cron = "*/5 * * * * * ")
    public void produceMail() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<HttpHeaders> httpEntity = new HttpEntity<>(httpHeaders);
        EmployeeEntity[] entities = restTemplate.exchange(url, HttpMethod.GET, httpEntity, EmployeeEntity[].class).getBody();
        LocalDate date = LocalDate.now();
        for (EmployeeEntity employeeEntity : entities) {

            LocalDate dateOfBirth = LocalDate.parse(employeeEntity.getDob());
            if (dateOfBirth.getMonth() == date.getMonth() && dateOfBirth.getDayOfMonth() == date.getDayOfMonth()) {
                log.info(employeeEntity);
                kafkaTemplate.send(topicName, employeeEntity.getEmail());
                log.info("birthday email is matched ==>" + employeeEntity.getEmail());

            }
        }

    }

}
