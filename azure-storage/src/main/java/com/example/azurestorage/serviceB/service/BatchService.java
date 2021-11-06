package com.example.azurestorage.serviceB.service;

import java.util.List;

import com.azure.storage.queue.models.QueueMessageItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BatchService {

    Logger log = LoggerFactory.getLogger(BatchService.class);

    @Autowired
    @Qualifier("serviceB.queueService")
    QueueService queueService;

    @Autowired
    @Qualifier("serviceB.blobService")
    BlobService blobService;

    public String run(int count) {
        log.info("start batch");
        List<QueueMessageItem> item = queueService.popQueueMessage(count);
        int num = blobService.uploadBlobBySasCredential(item);
        return num + "";
    }
}
