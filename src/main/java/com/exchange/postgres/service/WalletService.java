package com.exchange.postgres.service;

import com.exchange.Constants;
import com.exchange.postgres.entity.BankStatement;
import com.exchange.postgres.repository.BankStatementRepository;
import com.exchange.postgres.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class WalletService {
    private final BankStatementRepository bankStatementRepository;
    private final WalletRepository walletRepository;

    public String depositAndWithdraw(String transactionId){
        saveBankStatement(transactionId);
        saveWallet(bankStatementRepository.findByTransactionId(Long.parseLong(transactionId)));

        return "success deposit or withdraw";
    }

    private void saveBankStatement(String transactionId) {
        try {
            updateBankStatementStatus(transactionId, Constants.STATUS.SUCC);
        }catch(Exception e){
            log.info("error save bank statement");
            updateBankStatementStatus(transactionId, Constants.STATUS.FAIL);
        }
    }

    private void updateBankStatementStatus(String transactionId, Constants.STATUS status) {
        bankStatementRepository.updateStatus(transactionId, status.toString());
    };

    private void saveWallet(BankStatement bankStatement) {
        walletRepository.updateWallet(checkDepositAndWithdraw(
                bankStatement.getKrw(), bankStatement.getTransactionType()),
                bankStatement.getMemberId());
    }

    private Long checkDepositAndWithdraw(Long krw, Constants.TRANSACTION_TYPE type) {
        if (type == Constants.TRANSACTION_TYPE.WITHDRAW) {
            return -krw;
        }
        return krw;
    }
}
