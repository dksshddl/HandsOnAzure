package com.example.azurestorage.controller;

import com.example.azurestorage.service.QueueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/queue")
public class QueueController {

    @Autowired
    QueueService queueService;

    @PostMapping("/insert")
    public String insertQueue(String msg) {
        return queueService.insertQueue(msg);   
    }

    @GetMapping("/peek")
    public String peekQueue() {
        return queueService.peekQueue();
    }

    @GetMapping("/getMsg")
    public int getMessage(@RequestParam int count) {
        return queueService.getMessageQueue(count);
    }

    @GetMapping("/getMsgAndDelete")
    public int getMessageAndDelete(@RequestParam int count) {
        return queueService.getMessageAndDeleteQueue(count);
    }

    @GetMapping("/getListMsg")
    public String getListMessage() {
        return queueService.getListQueue();
    }
    

}
