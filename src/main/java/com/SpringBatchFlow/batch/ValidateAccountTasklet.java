package com.SpringBatchFlow.batch;

import com.SpringBatchFlow.model.TransferPaymentEntity;
import com.SpringBatchFlow.repositories.TransferPaymentRepository;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidateAccountTasklet implements Tasklet {

    @Autowired
    private TransferPaymentRepository transferPaymentRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        Boolean filterIsAproved = true;

        String transactionId = chunkContext.getStepContext().getJobParameters().get("transactionId").toString();

        TransferPaymentEntity transferPayment = transferPaymentRepository.findById(transactionId).orElseThrow();

        if (!transferPayment.getIsEnabled()) {
            //error porque la cuenta esta inactiva
            chunkContext.getStepContext()
                    .getStepExecution()
                    .getJobExecution()
                    .getExecutionContext()
                    .put("message", "Error, la cuenta esta inactiva");
            filterIsAproved = false;
        }

        if (transferPayment.getAmountPaid() > transferPayment.getAvailableBalance()) {
            //error porque no hay fondos suficientes
            chunkContext.getStepContext()
                    .getStepExecution()
                    .getJobExecution()
                    .getExecutionContext()
                    .put("message", "Error, no hay fondos suficientes");
            filterIsAproved = false;
        }

        ExitStatus exitStatus = null;
        if (filterIsAproved) {
            exitStatus = new ExitStatus("VALID");
            contribution.setExitStatus(exitStatus);
        }else {
            exitStatus = new ExitStatus("INVALID");
            contribution.setExitStatus(exitStatus);
        }
        return RepeatStatus.FINISHED;
    }
}
