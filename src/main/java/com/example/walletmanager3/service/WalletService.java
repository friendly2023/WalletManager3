package com.example.walletmanager3.service;

import com.example.walletmanager3.dto.WalletRequestDto;
import com.example.walletmanager3.entity.Wallet;
import com.example.walletmanager3.enums.OperationType;
import com.example.walletmanager3.repository.WalletRepository;
import com.example.walletmanager3.repository.WalletTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class WalletService {
    private static final Logger log = LoggerFactory.getLogger(WalletService.class);
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private WalletTransactionFactory walletTransactionFactory;
    @Autowired
    private WalletTransactionRepository walletTransactionRepository;

    public WalletService() {
    }

    public void createNewWallet() {

        log.debug("Начат процесс создания кошелька");

        Wallet wallet;
        int attempts = 0;

        do {
            wallet = new Wallet();

            attempts++;
            log.debug("Сгенерирован кошелек с ID: {}", wallet.getWalletId());
        } while (walletRepository.existsById(wallet.getWalletId())
                || walletTransactionRepository.existsByWalletId(wallet.getWalletId()));

        log.debug("Уникальный ID кошелька найден после {} попыток: {}", attempts, wallet.getWalletId());

        walletRepository.save(wallet);

        log.info("Новый кошелек успешно сохранен с ID: {}", wallet.getWalletId());
    }

    public Wallet getWalletByWalletId(String walletId) {

        log.info("Отправлен запрос в DB на получение кошелька");

        Wallet wallet = walletRepository.getWalletByUUID(UUID.fromString(walletId));

        log.info("Выполнен запрос в DB на получение кошелька");

        return wallet;
    }

    public void updateWalletBalance(WalletRequestDto walletRequestDto) {

        log.info("Начат процесс изменения баланса кошелька");
        Wallet walletForChanges = getWalletByWalletId(walletRequestDto.walletId());

        BigDecimal newBalance;

        OperationType type = OperationType.valueOf(walletRequestDto.operationType());

        if (type == OperationType.DEPOSIT) {
            newBalance = (walletForChanges.getBalance()).add(walletRequestDto.amount());
        } else {
            newBalance = (walletForChanges.getBalance()).subtract(walletRequestDto.amount());

            checkingOperation(newBalance, walletForChanges.getWalletId());
            log.debug("Новый баланс: {}", newBalance);
            log.debug("Средств для проведения операции достаточно");
        }

        walletForChanges.setBalance(newBalance);

        walletTransactionFactory.recordWalletTransaction(
                walletForChanges.getWalletId(),
                type,
                walletRequestDto.amount(),
                newBalance);

        walletRepository.save(walletForChanges);
        log.info("Завершен процесс изменения баланса кошелька");
    }

    public void deleteWalletByWalletId(String walletId) {

        log.info("Начат процесс удаления кошелька");
        getWalletByWalletId(walletId);
        walletRepository.deleteById(UUID.fromString(walletId));
        log.info("Закончен процесс удаления кошелька");
    }

    private void checkingOperation(BigDecimal newBalance, UUID walletId) {
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("На кошельке '" + walletId + "' недостаточно средств");
        }
    }
}
