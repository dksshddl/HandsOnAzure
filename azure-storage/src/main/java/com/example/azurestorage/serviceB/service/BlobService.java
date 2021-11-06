package com.example.azurestorage.serviceB.service;

import java.util.List;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.queue.models.QueueMessageItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("serviceB.blobService")
public class BlobService {
 
    @Value("${azure.storage.blob-endpoint}")
    String blobEndpoint;
    @Value("${azure.storage.sas-token}")
    String sasToken;
    @Value("${azure.storage.accesskey}")
    String accessKey;
    @Value("${azure.storage.container-name}")
    String containerName;

    Logger log = LoggerFactory.getLogger(BlobService.class);

    int uploadBlobByAccessKeyCredential(List<QueueMessageItem> item) {
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                                                        .endpoint(blobEndpoint)
                                                        .connectionString(accessKey)
                                                        .buildClient();
        return uploadBlob(blobServiceClient, item);
    }

    int uploadBlobBySasCredential(List<QueueMessageItem> item) {
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                                                        .endpoint(blobEndpoint)
                                                        .sasToken(sasToken)
                                                        .buildClient();
        return uploadBlob(blobServiceClient, item);
    }

    private int uploadBlob(BlobServiceClient blobServiceClient, List<QueueMessageItem> item) {
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        
        item.stream().forEach((msg) -> {
            BlobClient blobClient = containerClient.getBlobClient(msg.getMessageId());

            log.info("msg body :"  + msg.getBody());
            log.info("msg toString :"  + msg);

            blobClient.upload(msg.getBody());
        });
        return item.size();
    }
}
