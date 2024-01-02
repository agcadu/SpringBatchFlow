package com.SpringBatchFlow.batch;

import com.SpringBatchFlow.repositories.TransferPaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class ProcessPaymentTasklet implements Tasklet {

    @Autowired
    private TransferPaymentRepository transferPaymentRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        String transactionId = chunkContext.getStepContext().getJobParameters().get("transactionId").toString();

        log.info("Payment processed {} successfully", transactionId);

        transferPaymentRepository.updateTransactionStatus(true, transactionId);
        return RepeatStatus.FINISHED;
    }
}
