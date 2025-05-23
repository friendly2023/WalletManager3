package com.example.walletmanager3.exception;

import java.util.UUID;

public class WalletNotFoundException extends RuntimeException {
    public WalletNotFoundException(UUID walletId) {
        super("Кошелёк '" + walletId + "' не найден");
    }
}