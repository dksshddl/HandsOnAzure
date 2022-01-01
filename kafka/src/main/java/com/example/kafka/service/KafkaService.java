package com.example.kafka.service;

import com.example.kafka.domain.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaService {

    private final KafkaTemplate<String, Email> kafkaTemplate;

    public String insert(Email data) {
        ListenableFuture<SendResult<String, Email>> future = kafkaTemplate.send("dksshddl-hub", data);
        future.addCallback(successCallback -> log.info("[producer] successCallback. offset: " + successCallback.getRecordMetadata().offset()),
                errCallback -> log.error("[producer] errorCallback. msg: " + errCallback.getMessage()));
        return "success";
    }
}
