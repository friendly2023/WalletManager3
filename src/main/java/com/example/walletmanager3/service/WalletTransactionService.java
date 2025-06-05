package com.example.walletmanager3.service;

import com.example.walletmanager3.entity.WalletTransaction;
import com.example.walletmanager3.repository.WalletTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WalletTransactionService {
    private static final Logger log = LoggerFactory.getLogger(WalletTransactionService.class);

    @Autowired
    private WalletTransactionRepository walletTransactionRepository;

    public WalletTransactionService() {
    }

    public Page<WalletTransaction> getWalletTransactionHistory(String walletId, Integer page, Integer size) {
        log.info("Отправлен запрос в БД на получение истории транзакций");


        PageRequest pageRequest = PageRequest.of(page, size);
        Page<WalletTransaction> walletTransactionPage = walletTransactionRepository.findByWalletId(
                UUID.fromString(walletId),
                pageRequest);

        log.debug("Получено {} транзакций, всего страниц: {}",
                walletTransactionPage.getNumberOfElements(),
                walletTransactionPage.getTotalPages());
        log.info("Выполнен запрос в БД на получение истории транзакций");

        return walletTransactionPage;
    }
}
