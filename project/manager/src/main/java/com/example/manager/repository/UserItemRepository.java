package com.example.manager.repository;

import com.azure.data.tables.TableClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class UserItemRepository {
    @Autowired
    @Qualifier("itemTableClient")
    private TableClient tableClient;

    public void save() {

    }

    public void delete() {

    }
}
