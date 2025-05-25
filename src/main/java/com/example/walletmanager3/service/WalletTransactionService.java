package com.example.walletmanager3.service;

import com.example.walletmanager3.entity.WalletTransaction;
import com.example.walletmanager3.enums.OperationType;
import com.example.walletmanager3.repository.WalletTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class WalletTransactionService {

    @Autowired
    private WalletTransactionRepository walletTransactionRepository;

    public WalletTransactionService() {
    }

    public void recordWalletTransaction(UUID walletId,
                                        OperationType operationType,
                                        BigDecimal amount,
                                        BigDecimal balanceAfter) {

        WalletTransaction walletTransaction = buildWalletTransaction(
                walletId,
                operationType,
                amount,
                balanceAfter);

        walletTransactionRepository.save(walletTransaction);
    }

    private WalletTransaction buildWalletTransaction(UUID walletId,
                                                     OperationType operationType,
                                                     BigDecimal amount,
                                                     BigDecimal balanceAfter) {

        WalletTransaction walletTransaction = new WalletTransaction();
        walletTransaction.setWalletId(walletId);
        walletTransaction.setOperationType(operationType);
        walletTransaction.setAmount(amount);
        walletTransaction.setBalanceAfter(balanceAfter);

        return walletTransaction;
    }
}
