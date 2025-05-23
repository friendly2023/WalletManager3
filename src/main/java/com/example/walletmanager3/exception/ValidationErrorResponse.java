package com.example.walletmanager3.exception;

import java.util.List;

public record ValidationErrorResponse(
        List<Violation> violations
) {
}
