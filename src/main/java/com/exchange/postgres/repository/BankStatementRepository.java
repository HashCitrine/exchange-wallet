package com.exchange.postgres.repository;

import com.exchange.postgres.entity.BankStatement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BankStatementRepository extends JpaRepository<BankStatement, Long> {
    @Query(value = "select * from bank_statement where transaction_id=?1", nativeQuery = true)
    BankStatement findByTransactionId(Long transactionId);

    @Modifying
    @Query(value = "update bank_statement set status = ?2 where transaction_id = ?1", nativeQuery = true)
    void updateStatus(Long transactionId, String status);
}
