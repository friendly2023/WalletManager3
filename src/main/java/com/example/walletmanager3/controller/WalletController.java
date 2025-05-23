package com.example.walletmanager3.controller;

import com.example.walletmanager3.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
