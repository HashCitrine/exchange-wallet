package com.exchange.postgres.service;

import com.exchange.Constants;
import com.exchange.postgres.entity.Bankstatement;
import com.exchange.postgres.repository.BankstatementRepository;
import com.exchange.postgres.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class BankstatementService {
    private BankstatementRepository bankstatementRepository;
    private WalletRepository walletRepository;

    public void updateStatus(String transactionId, Constants.STATUS status) {
        bankstatementRepository.updateStatus(transactionId, status.toString());
    };

    public String depositAndWithdraw(Bankstatement bankStatement){
        // 이유 없는(?) 단순 에러 발생시 예외처리
        saveBank(bankStatement);
        saveWallet(bankStatement);

        return "success deposit or withdraw";
    }

    private void saveBank(Bankstatement bankStatement) {
        bankStatement.setTransactionDate(LocalDateTime.now());
        bankstatementRepository.save(bankStatement);
    }

    private void saveWallet(Bankstatement bankStatement) {
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
