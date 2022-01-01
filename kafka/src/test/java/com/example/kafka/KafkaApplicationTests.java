package com.example.kafka;

import com.example.kafka.domain.Email;
import com.example.kafka.service.KafkaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KafkaApplicationTests {

	@Autowired
	KafkaService kafkaService;

	Email data;

	@BeforeEach
	void set_data() {
//		data = Email.builder().address(["dksshddl@naver.com", "dksshddl@kakao.com"]).body("hello world").subject("[중요] 안내드립니다.").build();
	}

	@Test
	void contextLoads() {
	}

	@Test
	void insetTest() {
		kafkaService.insert(data);
	}
}
