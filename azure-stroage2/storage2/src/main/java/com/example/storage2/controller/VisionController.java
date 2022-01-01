package com.example.storage2.controller;

import java.io.File;
import java.io.InputStream;

import com.example.storage2.service.VisionService;
import com.example.storage2.vo.PredictionVO;
import com.example.storage2.vo.RequestVO;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/vision", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class VisionController {
    
    public final VisionService visionService;

    @PostMapping("/url")
    public ResponseEntity<PredictionVO> predictionByUrl(@RequestBody RequestVO req) {
        PredictionVO vo = visionService.predictByUrl(req);
        return new ResponseEntity<>(vo, HttpStatus.OK);
    }

    @PostMapping("/image")
    public ResponseEntity<PredictionVO> predictionByImage(@RequestBody String file) {
        PredictionVO vo = visionService.predictByImage(file);
        return new ResponseEntity<>(vo, HttpStatus.OK);
    }
}
