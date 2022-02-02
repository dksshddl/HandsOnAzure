package com.example.generator.config;

import com.azure.data.tables.TableClient;
import com.azure.data.tables.TableServiceClient;
import com.azure.data.tables.TableServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureStorageConfig {

    @Value("${azure.storage.data-table-conn-str}")
    private String connectionString;

    @Bean
    public TableServiceClient tableServiceClient() {
        return new TableServiceClientBuilder().connectionString(connectionString).buildClient();
    }

    @Bean
    public TableClient advertisementTableClient(TableServiceClient tableServiceClient) {
        return tableServiceClient.getTableClient("advertisement");
    }
}
