package com.example.azurestorage.serviceB.tasklet;

import com.example.azurestorage.serviceB.service.BatchService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class QueueReaderTasklet implements Tasklet, StepExecutionListener {

    @Autowired
    BatchService batchService;

    Logger log = LoggerFactory.getLogger(QueueReaderTasklet.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("before step");
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        batchService.run(3);
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("after step");
        return ExitStatus.COMPLETED;
    }
}
