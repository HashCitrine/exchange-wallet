package com.exchange.kafka;

import com.exchange.postgres.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Log4j2
public class WalletConsumer {

    private final WalletService walletService;

    @KafkaListener(topics = "submitDw", groupId = "exchange")
    public String depositAndWithdraw(String message) throws IOException {
        log.info("submitDw message : {}", message);
        return walletService.depositAndWithdraw(message);
    }
}
