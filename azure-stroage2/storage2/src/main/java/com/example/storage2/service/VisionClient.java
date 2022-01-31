package com.example.storage2.service;

import java.io.File;

import com.example.storage2.vo.PredictionVO;
import com.example.storage2.vo.RequestVO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name="vision-client", url="${azure.customvision.inferenceUrl}")
public interface VisionClient {
 
    @PostMapping(value="url", headers = "Prediction-Key=bed8370e619645c88f43b4b079ae1478", consumes = "application/json")
    public PredictionVO predictionByUrl(@RequestBody RequestVO vo);

    @PostMapping(value="image", headers = "Prediction-Key=bed8370e619645c88f43b4b079ae1478", consumes = "application/octet-stream")
    public PredictionVO predictionByImage(@RequestBody byte[] image);
}