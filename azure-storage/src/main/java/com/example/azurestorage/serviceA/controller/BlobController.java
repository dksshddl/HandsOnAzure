package com.example.azurestorage.serviceA.controller;

import java.io.IOException;

import com.example.azurestorage.serviceA.service.BlobService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blob")
public class BlobController {
    
    @Value("${azure.storage.container-name}")
    String containerName;

    @Autowired
    BlobService blobService;

    @GetMapping("/read")
    public String readFromBlob(@RequestParam String blobName) {
        return blobService.readBlobBySasCredential(containerName, blobName);
    }
}
