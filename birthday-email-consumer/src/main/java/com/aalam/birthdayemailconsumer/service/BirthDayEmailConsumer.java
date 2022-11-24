package com.aalam.birthdayemailconsumer.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Log4j2
public class BirthDayEmailConsumer {

    @Value("${sendKey}")
    private String key ;
    @Autowired
    private SendGrid sendGrid;
    @Value("${spring.kafka.topic.name}")
    private String topicName ;

    @KafkaListener(topics = "${spring.kafka.topic.name}"
            , groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(ConsumerRecord<String, String> record) {
        log.info("Topic: {}", topicName);
        log.info("key: {}", record.key());
        log.info("Headers: {}", record.headers());
        log.info("Partition: {}", record.partition());
        log.info("Order: {}", record.value());
        log.info("----------------------------------");
        String email = record.value();
        sendMail(email);

    }

    public Response sendMail(String email){
        Mail mail = new Mail(new Email("mohdaalam005@gmail.com"),"happy birthday",new Email(email),new Content("text/plain","Wish you a many many return of the day" +
                "from our company have a great day !!"));
        mail.setReplyTo( new Email("mohdaalam005@gmail.com"));
        Request request = new Request();

        Response response = new Response();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            response = sendGrid.api(request);
            log.info(response.getBody());
            log.info("birthday message has been successfully send " +response.getBody());
        } catch (IOException e) {
            log.info("birthday main failed ");
        }
        return response ;


    }



}
