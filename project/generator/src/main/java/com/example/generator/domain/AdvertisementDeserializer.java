package com.example.generator.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
public class AdvertisementDeserializer implements Deserializer<Advertisement> {

    private final ObjectMapper objMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public Advertisement deserialize(String topic, byte[] data) {
        try {
            if (data == null) {
                log.warn("EmailDeSerializer get null data with topic={}", topic);
                return null;
            }
            log.info("data " + new String(data, StandardCharsets.UTF_8));
            return objMapper.readValue(new String(data, StandardCharsets.UTF_8), Email.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SerializationException("EmailDeSerializer fail to deserializing byte array to Email");
        }
    }
}
