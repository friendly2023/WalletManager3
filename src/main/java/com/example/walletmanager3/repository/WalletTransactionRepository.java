package com.example.walletmanager3.repository;

import com.example.walletmanager3.entity.WalletTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Integer> {
    boolean existsByWalletId(UUID walletId);

    Page<WalletTransaction> findByWalletId(UUID walletId, Pageable pageable);
}
