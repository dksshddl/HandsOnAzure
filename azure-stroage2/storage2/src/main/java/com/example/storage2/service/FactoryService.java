package com.example.storage2.service;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import com.example.storage2.vo.FactoryVO;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import com.microsoft.azure.storage.table.TableOperation;

import org.springframework.stereotype.Service;

@Service
public class FactoryService {

    String connString = "DefaultEndpointsProtocol=https;AccountName=dksshddl;AccountKey=7XkL2oaYG+vMPLX7WKyQPWniUFcTf5c6k9n1uy1tvn2I7n5d1KHSM//lNgZUk7QA8UK27UrHFoM91D2tTE2iBQ==;EndpointSuffix=core.windows.net";

    public FactoryVO getFactory(String id) {
        CloudStorageAccount storageAccount;
        try {
            storageAccount = CloudStorageAccount.parse(connString);

            CloudTableClient tableClient = storageAccount.createCloudTableClient();
            
            CloudTable cloudTable = tableClient.getTableReference("dksshddl");
            TableOperation retrieve = TableOperation.retrieve(id, id, FactoryVO.class);

            return cloudTable.execute(retrieve).getResultAsType();

        } catch (InvalidKeyException | URISyntaxException | StorageException e) {
            throw new RuntimeException("error occur", e);
        }
    }

    public String insertFactory(FactoryVO vo) {

        CloudStorageAccount storageAccount;
        try {
            storageAccount = CloudStorageAccount.parse(connString);

            CloudTableClient tableClient = storageAccount.createCloudTableClient();
            
            CloudTable cloudTable = tableClient.getTableReference("dksshddl");
            vo.setPartitionKey(vo.getId()); 
            vo.setRowKey(vo.getId());

            TableOperation insertCustomer1 = TableOperation.insertOrReplace(vo);

            cloudTable.execute(insertCustomer1);
            return vo.getId();

        } catch (InvalidKeyException | URISyntaxException | StorageException e) {
            throw new RuntimeException("error occur", e);
        }
    } 

    public String updateFactory(FactoryVO vo) {

        CloudStorageAccount storageAccount;
        try {
            storageAccount = CloudStorageAccount.parse(connString);

            CloudTableClient tableClient = storageAccount.createCloudTableClient();
            
            CloudTable cloudTable = tableClient.getTableReference("dksshddl");
            vo.setPartitionKey(vo.getId()); 
            vo.setRowKey(vo.getId());

            TableOperation insertCustomer1 = TableOperation.insertOrReplace(vo);

            cloudTable.execute(insertCustomer1);
            return vo.getId();

        } catch (InvalidKeyException | URISyntaxException | StorageException e) {
            throw new RuntimeException("error occur", e);
        }
    }

    public String deleteFactory(String id) {
        CloudStorageAccount storageAccount;
        try {
            storageAccount = CloudStorageAccount.parse(connString);

            CloudTableClient tableClient = storageAccount.createCloudTableClient();
            
            CloudTable cloudTable = tableClient.getTableReference("dksshddl");
            TableOperation retrieve = TableOperation.retrieve(id, id, FactoryVO.class);
            
            FactoryVO entity = cloudTable.execute(retrieve).getResultAsType();
            TableOperation delete = TableOperation.delete(entity);
            
            cloudTable.execute(delete);
            return id;

        } catch (InvalidKeyException | URISyntaxException | StorageException e) {
            throw new RuntimeException("error occur", e);
        }
    }
}
