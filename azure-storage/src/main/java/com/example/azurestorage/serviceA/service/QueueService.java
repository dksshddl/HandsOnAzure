package com.example.azurestorage.serviceA.service;

import java.util.List;
import java.util.stream.Collectors;

import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.QueueClientBuilder;
import com.azure.storage.queue.QueueServiceClient;
import com.azure.storage.queue.QueueServiceClientBuilder;
import com.azure.storage.queue.models.PeekedMessageItem;
import com.azure.storage.queue.models.SendMessageResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service("serviceA.queueService")
public class QueueService {

    Logger log = LoggerFactory.getLogger(QueueService.class);

    @Value("${azure.storage.queue-endpoint}")
    String queueEndpoint;
    @Value("${azure.storage.sas-token}")
    String sasToken;
    @Value("${azure.storage.queue-name}")
    String queueNmae;
    
    public String insertQueue(String msg) {
        QueueClient queueClient = new QueueClientBuilder()
                                        .endpoint(queueEndpoint)
                                        .sasToken(sasToken)
                                        .queueName(queueNmae)
                                        .buildClient();

        SendMessageResult resultMsg = queueClient.sendMessage(msg);
        
        log.info("insert queue " + "\n msg : " + msg
                                + "\n msg id: " + resultMsg.getMessageId()
                                + "\n insert time: " + resultMsg.getInsertionTime());
        return resultMsg.getMessageId();
    }

    public String getListQueue() {
        QueueServiceClient serviceClient = new QueueServiceClientBuilder()
                                                    .endpoint(queueEndpoint)
                                                    .sasToken(sasToken)
                                                    .buildClient();

        List<String> queueNameLists = serviceClient.listQueues().stream().map(queue -> {
                                                System.out.println("queue name: " + queue.getName());
                                                return queue.getName();
                                                }).collect(Collectors.toList());
        log.info("get list queue");
        return String.join("\n", queueNameLists);
    }

    public String peekQueue() {
        QueueClient queueClient = new QueueClientBuilder()
                                            .endpoint(queueEndpoint)
                                            .queueName(queueNmae)
                                            .sasToken(sasToken)
                                            .buildClient();
        PeekedMessageItem item = queueClient.peekMessage();
        
        log.info( "peek"  + "\n peek: " + item.getBody().toString() 
                          + "\n count: " + item.getDequeueCount() 
                          + "\n id: " +  item.getMessageId()
                          + "\n time: " +  item.getInsertionTime()
                          + "\n body: " + item.getBody());
        
        return item.getMessageId();
    }

    public List<String> getMessageQueue(int count) {
		QueueClient queueClient = new QueueClientBuilder()
                                            .endpoint(queueEndpoint)
                                            .queueName(queueNmae)
                                            .sasToken(sasToken)
                                            .buildClient();

		List<String> msgList = queueClient.receiveMessages(count).stream()
				.map( message -> {
                    
                    log.info("get message queue" + "\n messageId: " + message.getMessageId() 
                                                 + "\n msg: " + message.getBody().toString() 
                                                 + "\n opReceipt: " + message.getPopReceipt());
					
                    return message.getBody().toString();
				})
				.collect(Collectors.toList());		
		
		return msgList;
	}
	
	public List<String> getMessageAndDeleteQueue(int count) {

		QueueClient queueClient = new QueueClientBuilder()
                                            .endpoint(queueEndpoint)
                                            .sasToken(sasToken)
                                            .queueName(queueNmae)
                                            .buildClient();
		List<String> msgList = queueClient.receiveMessages(count).stream()
				.map( message -> {
                    log.info("messageId: " + message.getMessageId() 
                           + ", msg: " + message.getBody().toString() 
                           + ", popReceipt: " + message.getPopReceipt());
					
					queueClient.deleteMessage(message.getMessageId(), message.getPopReceipt()); // 삭제
					return message.getBody().toString();
	
				})
				.collect(Collectors.toList());		
		
		return msgList;
	}
}
