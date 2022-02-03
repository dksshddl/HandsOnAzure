package com.example.generator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest
class GeneratorApplicationTests {

	@Autowired
	Scheduler scheduler;

	@Test
	void contextLoads() {
	}

	@Test
	void kafka_test() {
	}
}
