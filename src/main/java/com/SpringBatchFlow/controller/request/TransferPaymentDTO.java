package com.SpringBatchFlow.controller.request;

import lombok.Data;

@Data
public class TransferPaymentDTO {

    private Double availableBalance;

    private Double amountPaid;

    private Boolean isEnabled;
}
