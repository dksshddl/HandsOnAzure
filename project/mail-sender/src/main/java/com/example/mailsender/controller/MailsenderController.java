package com.example.mailsender.controller;

import com.example.mailsender.domain.Email;
import com.example.mailsender.service.MailsenderConsumer;
import com.example.mailsender.service.MailsenderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
@Slf4j
public class MailsenderController {

    private final MailsenderService kafkaService;

    @PostMapping("/sendMail")
    String insert(@RequestBody Email data) {

        return kafkaService.insert(data);
    }
}
