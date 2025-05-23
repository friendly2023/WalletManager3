package com.example.walletmanager3.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record WalletRequestDto(
        @Pattern(regexp = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}")
        String walletId,

        @Pattern(regexp = "DEPOSIT|WITHDRAW")
        String operationType,

        @DecimalMin(value = "0.01", inclusive = true)
        @Digits(integer = 100, fraction = 2)
        BigDecimal amount
) {
}