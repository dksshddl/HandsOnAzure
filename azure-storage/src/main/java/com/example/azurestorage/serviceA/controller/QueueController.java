package com.example.azurestorage.serviceA.controller;

import java.util.List;

import com.example.azurestorage.serviceA.service.QueueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class QueueController {

    @Autowired
    @Qualifier("serviceA.queueService")
    QueueService queueService;

    @PostMapping("/queue")
    public String insertQueue(@RequestBody String msg) {
        return queueService.insertQueue(msg);   
    }

    @GetMapping("/queue/peek")
    public String peekQueue() {
        return queueService.peekQueue();
    }

    @GetMapping("/queue/{count}")
    public List<String> getMessage(@PathVariable int count) {
        return queueService.getMessageQueue(count);
    }

    @DeleteMapping("/queue/{count}")
    public List<String> getMessageAndDelete(@PathVariable int count) {
        return queueService.getMessageAndDeleteQueue(count);
    }

    @GetMapping("/queuelist")
    public String getListMessage() {
        return queueService.getListQueue();
    }
}
