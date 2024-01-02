package com.SpringBatchFlow.repositories;

import com.SpringBatchFlow.model.TransferPaymentEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TransferPaymentRepository extends CrudRepository<TransferPaymentEntity, String> {

    @Modifying
    @Transactional
    @Query("UPDATE TransferPaymentEntity t SET t.isProcessed = ?1 WHERE t.transactionId = ?2")
    void updateTransactionStatus(Boolean newValor, String transactionId);

    @Modifying
    @Transactional
    @Query("UPDATE TransferPaymentEntity t SET t.isProcessed = ?1, t.error = ?2 WHERE t.transactionId = ?3")
    void updateTransactionStatusError(Boolean newValor, String error, String transactionId);
}
