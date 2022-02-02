package com.example.mailsender.service;

import com.azure.data.tables.TableClient;
import com.azure.data.tables.models.ListEntitiesOptions;
import com.azure.data.tables.models.TableEntity;
import com.example.mailsender.domain.Advertisement;
import com.example.mailsender.domain.Email;
import com.example.mailsender.http.client.LogicAppClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class MailsenderConsumer {

    @Autowired
    LogicAppClient client;

    @Autowired
    @Qualifier("userTableClient")
    TableClient tableClient;

    @Autowired
    @Qualifier("consumerTableClient")
    TableClient concernClient;

    @Value("${manager.url}")
    private String managerurl;

    @KafkaListener(topics = "advertisement", groupId = "myGroup1")
    public void consume(Advertisement ad) {

        log.info("successfully get ad : " + ad.getId());

        String filter = String.format("category eq '%s'", ad.getCategoryKo());
        ListEntitiesOptions options = new ListEntitiesOptions().setFilter(filter);
        concernClient.listEntities(options, null, null).stream().forEach((entity) -> {

            String category = (String) entity.getProperty("category");
            String userid = (String) entity.getProperty("PartitionKey");

            TableEntity userEntity = tableClient.getEntity("user", userid);
            String email = (String) userEntity.getProperty("emailAddr");

            String like = String.format("%s/v1/api/item/%s/like/%s", managerurl, userid, ad.getId());
            String hate = String.format("%s/v1/api/item/%s/hate/%s", managerurl, userid, ad.getId());

            Email body = Email.builder().email_addr(email).hateLink(hate).likeLink(like).itemLink(ad.getItemLink()).itemCategory(ad.getCategoryKo()).itemName(ad.getName()).itemPrice(ad.getPrice()).imgLink(ad.getImgLink()).build();

            ResponseEntity<String> resp = client.sendMailApp(body);

            if (resp.getStatusCodeValue() % 100 == 2 || resp.getStatusCodeValue() % 100 == 3) {
                log.info("[consumer] success request to LogicApp");
            }
        });
    }
}
