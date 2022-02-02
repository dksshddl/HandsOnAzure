package com.example.generator.service;

import com.example.generator.domain.Advertisement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.List;

@Service
@Slf4j
public class KafkaService {

    @Autowired
    private KafkaTemplate<String, Advertisement> kafkaTemplate;

    @Value("${project.kafka.topic}")
    private String topic;

    public String insert(Advertisement data) {
        ListenableFuture<SendResult<String, Advertisement>> future = kafkaTemplate.send(topic, data);
        future.addCallback(successCallback -> log.info("[producer] successCallback. offset: " + successCallback.getRecordMetadata().offset()),
                errCallback -> log.error("[producer] errorCallback. msg: " + errCallback.getMessage()));
        return "success";
    }

}