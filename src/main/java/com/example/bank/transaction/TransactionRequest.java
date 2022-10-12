package com.example.bank.transaction;

public record TransactionRequest(
        Integer accountId,
        String description,
        String currency,
        TransactionDirection direction,
        Float amount) {
}
