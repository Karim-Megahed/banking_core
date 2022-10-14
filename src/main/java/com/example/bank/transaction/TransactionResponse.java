package com.example.bank.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionResponse {
    private Integer accountId;
    private Integer transactionId;
    private Float amount;
    private TransactionDirection direction;
    private String description;
}
