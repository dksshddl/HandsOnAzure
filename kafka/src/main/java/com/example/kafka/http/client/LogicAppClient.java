package com.example.kafka.http.client;

import com.example.kafka.domain.Email;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="logic", url="${feign.url}")
public interface LogicAppClient {
    @PostMapping
    ResponseEntity<String> sendMailApp(@RequestBody Email body);
}