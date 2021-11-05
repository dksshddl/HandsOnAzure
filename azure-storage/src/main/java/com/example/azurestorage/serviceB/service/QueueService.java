package com.example.azurestorage.serviceB.service;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.QueueClientBuilder;
import com.azure.storage.queue.models.QueueMessageItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("serviceB.queueService")
public class QueueService {

    @Value("${azure.storage.queue-endpoint}")
    String queueEndpoint;
    @Value("${azure.storage.sas-token}")
    String sasToken;
    @Value("${azure.storage.accesskey}")
    String accessKey;
    @Value("${azure.storage.queue-name}")
    String queueName;

    Logger log = LoggerFactory.getLogger(QueueService.class);

    List<QueueMessageItem> popQueueMessage(int count) {
        count = count < -1 ? 32 : count;

        QueueClient queueClient = new QueueClientBuilder()
                                                .endpoint(queueEndpoint)
                                                .sasToken(sasToken)
                                                .queueName(queueName)
                                                .buildClient();

        List<QueueMessageItem> queueMsgItem = queueClient.receiveMessages(count).stream().collect(Collectors.toList());
        
        queueMsgItem.forEach(msg -> queueClient.deleteMessage(msg.getMessageId(), msg.getPopReceipt()));
        
        return queueMsgItem.stream().collect(Collectors.toList());
    }
}
