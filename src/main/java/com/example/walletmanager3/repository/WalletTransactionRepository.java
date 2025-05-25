package com.example.walletmanager3.repository;

import com.example.walletmanager3.entity.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Integer> {
    boolean existsByWalletId(UUID walletId);
}
