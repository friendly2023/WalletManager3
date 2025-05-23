package com.example.walletmanager3.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Wallet {
    @Id
    private UUID walletId;
    private BigDecimal balance;

    public Wallet() {
        this.walletId = UUID.randomUUID();
        this.balance = BigDecimal.valueOf(0);
    }

    public UUID getWalletId() {
        return walletId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
