package com.example.azurestorage.serviceB.config;

import com.example.azurestorage.serviceB.tasklet.QueueReaderTasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    Logger log = LoggerFactory.getLogger(BatchConfig.class);

    @Autowired 
    public JobBuilderFactory jobBuilderFactory;
    @Autowired 
    public StepBuilderFactory stepBuilderFactory;
    @Autowired
    public QueueReaderTasklet queueReaderTasklet;

    @Bean
    public Job queueReaderJob(){

        return jobBuilderFactory.get("queueReaderJob")
                                .start(queueReaderStep())
                                .build();
    }

    @Bean
    public Step queueReaderStep() {
        return stepBuilderFactory.get("queueReaderStep")
                                 .tasklet(queueReaderTasklet)
                                 .allowStartIfComplete(true)
                                 .build();
    }
}
