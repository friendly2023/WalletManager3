package com.example.walletmanager3.service;

import com.example.walletmanager3.entity.Wallet;
import com.example.walletmanager3.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    public WalletService() {
    }

    public void createNewWallet() {

        walletRepository.save(new Wallet());
    }

    public Wallet getWalletByWalletId(String walletId) {

        return walletRepository.getWalletByUUID(UUID.fromString(walletId));
    }
}
