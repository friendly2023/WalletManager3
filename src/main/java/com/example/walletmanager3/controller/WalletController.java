package com.example.walletmanager3.controller;

import com.example.walletmanager3.dto.WalletRequestDto;
import com.example.walletmanager3.entity.Wallet;
import com.example.walletmanager3.entity.WalletTransaction;
import com.example.walletmanager3.service.WalletService;
import com.example.walletmanager3.service.WalletTransactionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/v1")
public class WalletController {
    private static final Logger log = LoggerFactory.getLogger(WalletController.class);
    @Autowired
    private WalletService walletService;
    @Autowired
    private WalletTransactionService walletTransactionService;

    public WalletController() {
    }

    @PostMapping(value = "/new_wallet")
    public void createNewWallet() {

        log.info("Получен запрос на создание нового кошелька");
        walletService.createNewWallet();
        log.info("Выполнен запрос на создание нового кошелька");
    }

    @GetMapping(value = "/wallets/{walletId}")
    public Wallet getWalletByWalletId(@PathVariable @Pattern
            (regexp = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}")
                                      String walletId) {

        log.info("Получен запрос на предоставление кошелька");
        log.debug("ID кошелька: {}", walletId);

        Wallet wallet = walletService.getWalletByWalletId(walletId);

        log.info("Выполнен запрос на предоставление кошелька");

        return wallet;
    }

    @PostMapping(value = "/wallet")
    public void updateWalletBalance(@Valid @RequestBody WalletRequestDto walletRequestDto) {

        log.info("Получен запрос на изменение баланса кошелька");
        log.debug("{}", walletRequestDto);

        walletService.updateWalletBalance(walletRequestDto);

        log.info("Выполнен запрос на изменение баланса кошелька");
    }

    @DeleteMapping(value = "/wallets/{walletId}/delete")
    public void deleteWalletByWalletId(@PathVariable @Pattern
            (regexp = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}")
                                       String walletId) {

        log.info("Получен запрос на удаление кошелька");
        log.debug("ID кошелька: {}", walletId);

        walletService.deleteWalletByWalletId(walletId);

        log.info("Кошелек удален");
    }

    @GetMapping("/wallets/{walletId}/transactions")
    public Page<WalletTransaction> getAllWalletTransactions(
            @PathVariable @Pattern
                    (regexp = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}")
            String walletId,
            @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page,
            @RequestParam(value = "size", defaultValue = "10") @Min(1) @Max(100) Integer size
    ) {
        log.info("Получен запрос на предоставление истории транзакций по кошельку");
        log.debug("walletId = {}, page = {}, size = {}", walletId, page, size);

        Page<WalletTransaction> walletTransactionPage = walletTransactionService.getWalletTransactionHistory(walletId, page, size);

        log.info("Выполнен запрос на предоставление истории транзакций по кошельку");

        return walletTransactionPage;
    }
}