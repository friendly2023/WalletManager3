package com.example.walletmanager3.repository;

import com.example.walletmanager3.entity.Wallet;
import com.example.walletmanager3.exception.WalletNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID> {

    default Wallet getWalletByUUID(UUID walletId) {
        return findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId));
    }
}
