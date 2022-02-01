package com.example.manager.repository;

import com.azure.data.tables.TableClient;
import com.azure.data.tables.models.TableEntity;
import com.example.manager.domain.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Repository
public class UserInfoRepository {

    @Autowired
    @Qualifier("userTableClient")
    private TableClient tableClient;

    @SuppressWarnings("unchecked")
    public void upsert(User user) throws IOException {
        String partitionKey = "user";
        String rowKey = user.getUserId();
        Map<String, Object> entityInfo;

        byte[] bytes = new ObjectMapper().writeValueAsBytes(user);  // JsonProcessingException
        entityInfo = new ObjectMapper().readValue(bytes, Map.class); // IOException

        TableEntity entity = new TableEntity(partitionKey, rowKey).setProperties(entityInfo);

        tableClient.upsertEntity(entity);
    }

    public void delete(String userId) {
        TableEntity entity = tableClient.getEntity("user", userId);
        tableClient.deleteEntity(entity);
    }

    public TableEntity get(String userId) {
        return tableClient.getEntity("user", userId);
    }
}
