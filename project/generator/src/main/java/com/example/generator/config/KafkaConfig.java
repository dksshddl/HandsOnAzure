package com.example.generator.config;

import com.example.generator.domain.Advertisement;
import com.example.generator.domain.AdvertisementDeserializer;
import com.example.generator.domain.AdvertisementSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

//@Configuration
public class KafkaConfig {

    private Map<String, Object> kafkaSecurity() {
        Map<String, Object> props = new HashMap<>();
        props.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"$ConnectionString\" password=\"Endpoint=sb://dksshddl.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=NZM7HsZWxXgY8uWUhzoPyZF3VffExWs+q01pI9nPLXI=\";");
        props.put("sasl.mechanism", "PLAIN");
        props.put("security.protocol", "SASL_SSL");
        return props;
    }

    @Bean
    public ConsumerFactory<String, Advertisement> consumer() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, AdvertisementDeserializer.class);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "dksshddl.servicebus.windows.net:9093");
        ConsumerFactory<String, Advertisement> consumerFactory = new DefaultKafkaConsumerFactory<>(props);
        consumerFactory.updateConfigs(kafkaSecurity());
        return consumerFactory;
    }

    @Bean
    public ProducerFactory<String, Advertisement> producer() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AdvertisementSerializer.class);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "dksshddl.servicebus.windows.net:9093");
        props.put(ProducerConfig.LINGER_MS_CONFIG, 3);
        ProducerFactory<String, Advertisement> producerFactory = new DefaultKafkaProducerFactory<>(props);
        producerFactory.updateConfigs(kafkaSecurity());
        return producerFactory;
    }

    //  for sending message
    @Bean
    public KafkaTemplate<String, Advertisement> kafkaTemplate() {
        return new KafkaTemplate<>(producer());
    }

    //  for receiving message
    @Bean
    public KafkaListenerContainerFactory<?> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Advertisement> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumer());
        factory.setBatchListener(true);
        return factory;
    }
}
