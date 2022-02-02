package com.example.mailsender.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.data.tables.TableClient;
import com.azure.data.tables.TableServiceClient;
import com.azure.data.tables.TableServiceClientBuilder;

@Configuration
public class AzureConfig {

    @Value("${azure.storage.data-table-conn-str}")
    private String connectionString;

    @Bean
    public TableServiceClient tableServiceClient() {
        return new TableServiceClientBuilder().connectionString(connectionString).buildClient();
    }

    @Bean
    public TableClient userTableClient(TableServiceClient tableServiceClient) {
        return tableServiceClient.getTableClient("user");
    }

    @Bean
    public TableClient consumerTableClient(TableServiceClient tableServiceClient) {
        return tableServiceClient.getTableClient("concern");
    }

}