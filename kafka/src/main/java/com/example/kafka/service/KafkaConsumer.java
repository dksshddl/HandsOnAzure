package com.example.kafka.service;

import com.example.kafka.domain.Email;
import com.example.kafka.http.client.LogicAppClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final LogicAppClient client;

    @KafkaListener(topics = "dksshddl-hub", groupId = "myGroup1")
    public void consume(Email email) {
        log.info("\n[consumer] Consumed email subject  : {}\n" +
                 "                    email address  : {}\n" +
                 "                    email body     : {}", email.getSubject(), email.getAddress(), email.getBody());
        ResponseEntity<String> resp = client.sendMailApp(email);
        if (resp.getStatusCodeValue() % 100 == 2) {
            log.info("[consumer] success request to LogicApp");
        }
    }
}
