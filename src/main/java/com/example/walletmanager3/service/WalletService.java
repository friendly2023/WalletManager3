package com.example.walletmanager3.service;

import com.example.walletmanager3.dto.WalletRequestDto;
import com.example.walletmanager3.entity.Wallet;
import com.example.walletmanager3.enums.OperationType;
import com.example.walletmanager3.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private WalletTransactionService walletTransactionService;

    public WalletService() {
    }

    public void createNewWallet() {

        walletRepository.save(new Wallet());
    }

    public Wallet getWalletByWalletId(String walletId) {

        return walletRepository.getWalletByUUID(UUID.fromString(walletId));
    }

    public void updateWalletBalance(WalletRequestDto walletRequestDto) {

        Wallet walletForChanges = getWalletByWalletId(walletRequestDto.walletId());

        BigDecimal newBalance;

        OperationType type = OperationType.valueOf(walletRequestDto.operationType());

        if (type == OperationType.DEPOSIT) {
            newBalance = (walletForChanges.getBalance()).add(walletRequestDto.amount());
        } else {
            newBalance = (walletForChanges.getBalance()).subtract(walletRequestDto.amount());

            checkingOperation(newBalance, walletForChanges.getWalletId());
        }

        walletForChanges.setBalance(newBalance);

        walletTransactionService.recordWalletTransaction(
                walletForChanges.getWalletId(),
                type,
                walletRequestDto.amount(),
                newBalance);

        walletRepository.save(walletForChanges);
    }

    public void deleteWalletByWalletId(String walletId) {

        getWalletByWalletId(walletId);
        walletRepository.deleteById(UUID.fromString(walletId));
    }

    private void checkingOperation(BigDecimal newBalance, UUID walletId) {
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("На кошельке '" + walletId + "' недостаточно средств");
        }
    }
}
