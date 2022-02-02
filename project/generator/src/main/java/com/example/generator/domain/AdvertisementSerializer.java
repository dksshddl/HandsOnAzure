package com.example.generator.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

@Slf4j
public class AdvertisementSerializer implements Serializer<Advertisement> {

    private final ObjectMapper objMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String topic, Advertisement data) {
        try {
            if (data == null) {
                log.warn("AdvertisementSerializer get null data with topic={}", topic);
                return null;
            }
            return objMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new SerializationException("AdvertisementSerializer fail to serializing Advertisement to byte array");
        }
    }

}
