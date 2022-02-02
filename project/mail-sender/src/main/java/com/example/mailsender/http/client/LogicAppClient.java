package com.example.mailsender.http.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.mailsender.domain.Email;

@FeignClient(name="logic", url="${feign.url}")
public interface LogicAppClient {
    @PostMapping
    ResponseEntity<String> sendMailApp(@RequestBody Email body);
}