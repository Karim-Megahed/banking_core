package com.example.bank.transaction;

public class TransactionCreationResponse extends TransactionResponse {
    private Float balanceAfterTransaction;

    public TransactionCreationResponse(Integer accountId, Integer transactionId, Float amount, TransactionDirection direction, String description, Float balanceAfterTransaction) {
        super(accountId, transactionId, amount, direction, description);
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    public Float getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    public void setBalanceAfterTransaction(Float balanceAfterTransaction) {
        this.balanceAfterTransaction = balanceAfterTransaction;
    }
}
