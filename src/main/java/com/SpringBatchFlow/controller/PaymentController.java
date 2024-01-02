package com.SpringBatchFlow.controller;

import com.SpringBatchFlow.controller.request.TransferPaymentDTO;
import com.SpringBatchFlow.model.TransferPaymentEntity;
import com.SpringBatchFlow.repositories.TransferPaymentRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Autowired
    private TransferPaymentRepository transferPaymentRepository;

    @PostMapping("/transfer")
    public ResponseEntity<?> transferPayment(@RequestBody TransferPaymentDTO transferPaymentDTO) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        String transactionId = UUID.randomUUID().toString();

        TransferPaymentEntity transferPaymentEntity = TransferPaymentEntity.builder()
                .transactionId(transactionId)
                .availableBalance(transferPaymentDTO.getAvailableBalance())
                .amountPaid(transferPaymentDTO.getAmountPaid())
                .isEnabled(transferPaymentDTO.getIsEnabled())
                .isProcessed(false)
                .build();

        transferPaymentRepository.save(transferPaymentEntity);

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("id", UUID.randomUUID().toString())
                .addString("transactionId", transactionId)
                .toJobParameters();

        jobLauncher.run(job, jobParameters);

        Map<String, Object> httpResponse = new HashMap<>();
        httpResponse.put("transactionId", transactionId);
        httpResponse.put("message", "Payment transfer request received successfully");

        return ResponseEntity.ok(httpResponse);
    }
}
