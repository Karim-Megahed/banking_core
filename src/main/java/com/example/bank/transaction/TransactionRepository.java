package com.example.bank.transaction;

import com.example.bank.balance.Balance;
import com.example.bank.balance.BalanceCurrency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByAccountId(Integer accountId);
}
