package com.SpringBatchFlow.config;

import com.SpringBatchFlow.batch.CancelTransactionTasklet;
import com.SpringBatchFlow.batch.ProcessPaymentTasklet;
import com.SpringBatchFlow.batch.SendNotificationTasklet;
import com.SpringBatchFlow.batch.ValidateAccountTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public ValidateAccountTasklet validateAccountTasklet() {
        return new ValidateAccountTasklet();
    }

    @Bean
    public ProcessPaymentTasklet processPaymentTasklet() {
        return new ProcessPaymentTasklet();
    }

    @Bean
    public CancelTransactionTasklet cancelTransactionTasklet() {
        return new CancelTransactionTasklet();
    }

    @Bean
    public SendNotificationTasklet sendNotificationTasklet() {
        return new SendNotificationTasklet();
    }

    @Bean
    @JobScope
    public Step validateAccountStep(){
        return new StepBuilder("validateAccountStep", jobRepository)
                .tasklet(validateAccountTasklet(), transactionManager)
                .build();
    }


    @Bean
    public Step processPaymentStep() {
        return new StepBuilder("processPaymentStep", jobRepository)
                .tasklet(processPaymentTasklet(), transactionManager)
                .build();
    }

    @Bean
    public Step cancelTransactionStep() {
        return new StepBuilder("cancelTransactionStep", jobRepository)
                .tasklet(cancelTransactionTasklet(), transactionManager)
                .build();
    }

    @Bean
    public Step sendNotificationStep() {
        return new StepBuilder("sendNotificationStep", jobRepository)
                .tasklet(sendNotificationTasklet(), transactionManager)
                .build();
    }

    @Bean
    public Job transactionPaymentsJob() {
        return new JobBuilder("transactionPaymentsJob",jobRepository)
                .start(validateAccountStep())
                    .on("VALID").to(processPaymentStep())
                    .next(sendNotificationStep())
                .from(validateAccountStep())
                    .on("INVALID").to(cancelTransactionStep())
                    .next(sendNotificationStep())
                .end()
                .build();
    }
}
