package com.example.walletmanager3.controller;

import com.example.walletmanager3.dto.WalletRequestDto;
import com.example.walletmanager3.entity.Wallet;
import com.example.walletmanager3.service.WalletService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
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
    @Autowired
    private WalletService walletService;

    public WalletController() {
    }

    @PostMapping(value = "/new_wallet")
    public void createNewWallet() {

        walletService.createNewWallet();
    }

    @GetMapping(value = "/wallets/{walletId}")
    public Wallet getWalletByWalletId(@PathVariable @Pattern
            (regexp = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}")
                                      String walletId) {

        return walletService.getWalletByWalletId(walletId);
    }

    @PostMapping(value = "/wallet")
    public void updateWalletBalance(@Valid @RequestBody WalletRequestDto walletRequestDto) {

        walletService.updateWalletBalance(walletRequestDto);
    }

    @DeleteMapping(value = "wallets/del_{walletId}")
    public void deleteWalletByWalletId(@PathVariable @Pattern
            (regexp = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}")
                                       String walletId) {

        walletService.deleteWalletByWalletId(walletId);
    }
}
