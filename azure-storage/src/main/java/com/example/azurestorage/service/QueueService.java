package com.example.azurestorage.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.azure.core.credential.TokenCredential;
import com.azure.storage.common.StorageSharedKeyCredential;
import com.azure.storage.common.implementation.connectionstring.StorageConnectionString;
import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.QueueClientBuilder;
import com.azure.storage.queue.QueueServiceClient;
import com.azure.storage.queue.QueueServiceClientBuilder;
import com.azure.storage.queue.models.PeekedMessageItem;
import com.azure.storage.queue.models.SendMessageResult;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class QueueService {

    String endpoint = "https://dksshddlstorage.queue.core.windows.net/";
    String sasToken = "?sv=2020-08-04&ss=bfqt&srt=sco&sp=rwdlacupitfx&se=2021-11-02T14:12:55Z&st=2021-11-02T06:12:55Z&spr=https&sig=KjLavEqhhbcU9NqAY%2FIW9IwoAafrsHfTcbORq4xwWqA%3D";
    
    public String insertQueue(String msg) {
          QueueClient queueClient = new QueueClientBuilder()
                                        .endpoint(endpoint)
                                        .sasToken(sasToken)
                                        .buildClient();

          SendMessageResult resultMsg = queueClient.sendMessage(LocalDateTime.now() + "_" + msg);

          return resultMsg.getMessageId();
    }

    public String getListQueue() {
        QueueServiceClient serviceClient = new QueueServiceClientBuilder()
                                                    .endpoint(endpoint)
                                                    .sasToken(sasToken)
                                                    .buildClient();

        List<String> queueNameLists = serviceClient.listQueues().stream().map(queue -> {
                                                System.out.println("queue name: " + queue.getName());
                                                return queue.getName();
                                                }).collect(Collectors.toList());
        
        return String.join("\n", queueNameLists);
    }

    public String peekQueue() {
        QueueClient queueClient = new QueueClientBuilder()
                                            .endpoint(endpoint)
                                            .sasToken(sasToken)
                                            .buildClient();
        PeekedMessageItem item = queueClient.peekMessage();
        
        System.out.println("peek: " + item.getBody().toString() 
        + " . count: " + item.getDequeueCount() 
        + " id: " +  item.getMessageId()
        + " time: " +  item.getInsertionTime());

        return item.getMessageId();
    }

    public int getMessageQueue(int count) {
		QueueClient queueClient = new QueueClientBuilder()
				.endpoint(endpoint)
				.sasToken(sasToken)
				.buildClient();

		List<String> msgIdList = queueClient.receiveMessages(count).stream()
				.map( message -> {
			
					System.out.println("messageId: " + message.getMessageId() 
						+ ", msg: " + message.getBody().toString() 
						+ ", popReceipt: " + message.getPopReceipt());
					return message.getMessageId();
				})
				.collect(Collectors.toList());		
		
		return msgIdList.size();
	}
	
//	@Test
	public int getMessageAndDeleteQueue(int count) {

		QueueClient queueClient = new QueueClientBuilder()
				.endpoint(endpoint)
				.sasToken(sasToken)
				.buildClient();

		List<String> msgIdList = queueClient.receiveMessages(count).stream()
				.map( message -> {
			
					System.out.println("messageId: " + message.getMessageId() 
						+ ", msg: " + message.getBody().toString() 
						+ ", popReceipt: " + message.getPopReceipt());
					
					queueClient.deleteMessage(message.getMessageId(), message.getPopReceipt()); // 삭제
					
					return message.getMessageId();
				})
				.collect(Collectors.toList());		
		
		return msgIdList.size();
	
	}
}
