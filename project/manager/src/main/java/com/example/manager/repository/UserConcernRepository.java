package com.example.manager.repository;

import com.azure.data.tables.TableClient;
import com.azure.data.tables.models.ListEntitiesOptions;
import com.azure.data.tables.models.TableEntity;
import com.azure.data.tables.models.TableTransactionAction;
import com.azure.data.tables.models.TableTransactionActionType;
import com.example.manager.domain.concern.ConcernVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

import static com.azure.data.tables.implementation.TablesConstants.PARTITION_KEY;

@Repository
public class UserConcernRepository {

    @Autowired
    @Qualifier("concernTableClient")
    private TableClient tableClient;

    public void save(String userId, ConcernVO concernVO) {
        List<TableTransactionAction> transaction = concernVO.getCategories().stream().map((category) -> {
            Map<String, Object> concern = new HashMap<>();
            concern.put("category", category);
            String rowKey = userId + "_" + category;
            return new TableTransactionAction(TableTransactionActionType.UPSERT_MERGE,
                    new TableEntity(userId, rowKey).setProperties(concern));
        }).collect(Collectors.toList());
        tableClient.submitTransaction(transaction);
    }

    public List<TableEntity> getConcernList(String userId) {
        String filter = PARTITION_KEY + " eq '" + userId + "'";
        ListEntitiesOptions options = new ListEntitiesOptions().setFilter(filter);
        return tableClient.listEntities(options, null, null).stream().collect(Collectors.toList());
    }

    public void deleteConcern(String userId, ConcernVO concernVO) {
        List<TableTransactionAction> transaction = concernVO.getCategories().stream().map((category) -> {
            String rowKey = userId + "_" + category;
            TableEntity entity = tableClient.getEntity(userId, rowKey);
            return new TableTransactionAction(TableTransactionActionType.DELETE, entity);
        }).collect(Collectors.toList());
        tableClient.submitTransaction(transaction);
    }
}
