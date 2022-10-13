package com.example.bank.account;

import com.example.bank.balance.Balance;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AccountResponse {
    private Integer accountId;
    private Integer customerId;
    private List<Balance> balances;
}
