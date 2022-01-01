package com.example.kafka.controller;

import com.example.kafka.domain.Email;
import com.example.kafka.service.KafkaConsumer;
import com.example.kafka.service.KafkaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
@Slf4j
public class KafkaController {

    private final KafkaService kafkaService;

    @PostMapping("/sendMail")
    String insert(@RequestBody Email data) {

        return kafkaService.insert(data);
    }
}
