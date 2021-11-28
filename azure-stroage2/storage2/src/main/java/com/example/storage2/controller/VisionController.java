package com.example.storage2.controller;

import com.example.storage2.service.VisionService;
import com.example.storage2.vo.PredictionVO;
import com.example.storage2.vo.RequestVO;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/vision", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class VisionController {
    
    public final VisionService visionService;

    @PostMapping
    public ResponseEntity<PredictionVO> prediction(@RequestBody RequestVO req) {
        PredictionVO vo = visionService.predict(req);
        return new ResponseEntity<>(vo, HttpStatus.OK);
    }
}
