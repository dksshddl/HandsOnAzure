package com.example.manager.repository;

import com.azure.data.tables.TableClient;
import com.azure.data.tables.models.ListEntitiesOptions;
import com.azure.data.tables.models.TableEntity;
import com.example.manager.domain.item.ItemType;
import com.example.manager.domain.item.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.azure.data.tables.implementation.TablesConstants.PARTITION_KEY;

@Repository
public class UserItemRepository {
    @Autowired
    @Qualifier("itemTableClient")
    private TableClient tableClient;

    public void insert(String userId, String itemId, ItemType type) {
        String partitionKey = type.getSymbol();
        String rowKey = userId+"_"+itemId;

        Map<String, Object> entityInfo = new HashMap<>();
        entityInfo.put("ItemId", itemId);
        entityInfo.put("UserId", userId);

        TableEntity entity = new TableEntity(partitionKey, rowKey).setProperties(entityInfo);

        tableClient.createEntity(entity);
    }

    public void delete(String userId, String itemId, ItemType type) {
        String partitionkey = type.getSymbol();
        String rowKey = userId+"_"+itemId;

        TableEntity entity = tableClient.getEntity(partitionkey, rowKey);

        tableClient.deleteEntity(entity);
    }

    public List<ItemVO> findAll(String userId, ItemType type) {
        String filter = String.format("%s eq '%s' and UserId eq '%s'", PARTITION_KEY, type.getSymbol(), userId);
        ListEntitiesOptions options = new ListEntitiesOptions().setFilter(filter);
        return tableClient.listEntities(options, null, null)
                .stream().map((entity)-> {
                    String enItemId = (String) entity.getProperty("ItemId");
                    String enUserId = (String) entity.getProperty("UserId");
                    ItemType enItemType = ItemType.valueOf((String) entity.getProperty("PartitionKey"));
                    return ItemVO.builder().itemId(enItemId).userId(enUserId).itemType(enItemType).build();
                }).collect(Collectors.toList());
    }

    public ItemVO findByItemId(String userId, String itemId) {
        String filter = String.format("UserId eq '%s'", userId);
        ListEntitiesOptions options = new ListEntitiesOptions().setFilter(filter);
        TableEntity entity = tableClient.listEntities(options, null, null).stream().findAny().orElseThrow();
        String enItemId = (String) entity.getProperty("ItemId");
        String enUserId = (String) entity.getProperty("UserId");
        ItemType enItemType = ItemType.valueOf((String) entity.getProperty("PartitionKey"));
        return ItemVO.builder().itemId(enItemId).userId(enUserId).itemType(enItemType).build();
    }
}
