package com.example.kafka.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

@Slf4j
public class EmailSerializer implements Serializer<Email> {

    private final ObjectMapper objMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String topic, Email data) {
        try {
            if (data == null) {
                log.warn("EmailSerializer get null data with topic={}", topic);
                return null;
            }
            return objMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new SerializationException("EmailSerializer fail to serializing Email to byte array");
        }
    }

}
