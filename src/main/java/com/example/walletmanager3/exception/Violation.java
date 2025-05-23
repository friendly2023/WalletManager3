package com.example.walletmanager3.exception;

public record Violation(
        String fieldName,
        String message
) {
}
