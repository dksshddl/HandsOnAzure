package com.example.azurestorage.serviceA.controller;

import java.util.List;

import com.example.azurestorage.serviceA.service.BlobService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlobController {
    
    @Value("${azure.storage.container-name}")
    String containerName;

    @Autowired
    BlobService blobService;

    @GetMapping("/blob/{blobName}")
    public String readFromBlob(@PathVariable String blobName) {
        return blobService.readBlobBySasCredential(containerName, blobName);
    }

    @GetMapping("/blob")
    public List<String> readBlobList() {
        return blobService.readBlobListBySasCredential(containerName);
    }

    @DeleteMapping("/blob/{blobName}")
    public String deleteFromBlob(@PathVariable String blobName) {
        return blobService.deleteBlobBySasCredential(containerName, blobName);
    }
}
