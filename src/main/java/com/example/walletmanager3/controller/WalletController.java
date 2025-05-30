package com.example.walletmanager3.controller;

import com.example.walletmanager3.dto.WalletRequestDto;
import com.example.walletmanager3.entity.Wallet;
import com.example.walletmanager3.service.WalletService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1")
public class WalletController {
    private static final Logger log = LoggerFactory.getLogger(WalletController.class);
    @Autowired
    private WalletService walletService;

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

    @DeleteMapping(value = "/wallets/del_{walletId}")
    public void deleteWalletByWalletId(@PathVariable @Pattern
            (regexp = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}")
                                       String walletId) {

        log.info("Получен запрос на удаление кошелька");
        log.debug("ID кошелька: {}", walletId);

        walletService.deleteWalletByWalletId(walletId);

        log.info("Кошелек удален");
    }
}
