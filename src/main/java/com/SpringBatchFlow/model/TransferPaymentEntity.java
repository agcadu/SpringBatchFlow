package com.SpringBatchFlow.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="transfer_payment_history")
public class TransferPaymentEntity {

    @Id
    @Column(name="transaction_id", nullable = false)
    private String transactionId;

    @Column(name="available_balance", nullable = false)
    private Double availableBalance;

    @Column(name="amount_paid", nullable = false)
    private Double amountPaid;

    @Column(name="amount_received", nullable = false)
    private Boolean isEnabled;

    @Column(name="is_processed", nullable = false)
    private Boolean isProcessed;

    private String error;
}
