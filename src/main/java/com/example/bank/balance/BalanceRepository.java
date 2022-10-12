package com.example.bank.balance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BalanceRepository extends JpaRepository<Balance, Integer> {
    List<Balance> findByCurrencyAndAccountId(String currency, Integer accountId);
}
