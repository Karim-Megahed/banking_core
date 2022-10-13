package com.example.bank.transaction;

import com.example.bank.account.Account;
import com.example.bank.account.AccountService;
import com.example.bank.balance.Balance;
import com.example.bank.balance.BalanceRepository;
import com.example.bank.balance.BalanceService;
import com.example.bank.exception.ModelNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final BalanceRepository balanceRepository;
    private final BalanceService balanceService;
    private final AccountService accountService;

    public Transaction createTransaction(TransactionRequest request) throws ModelNotFoundException {
        Account account = accountService.getAccount(request.getAccountId());
        Balance balance = balanceService.getBalance(account, request.getCurrency());
        Float newAmount = request.getDirection() == TransactionDirection.IN
                ? balance.getAmount() + request.getAmount()
                : balance.getAmount() - request.getAmount();

        balance.setAmount(newAmount);
        balanceRepository.saveAndFlush(balance);

        if (balance.getId() == null){
            System.out.println("Balance is empty");
        }

        Transaction transaction = Transaction.builder()
                .direction(request.getDirection())
                .amount(request.getAmount())
                .balance(balance)
                .description(request.getDescription())
                .build();
        transactionRepository.saveAndFlush(transaction);

        return transaction;
    }

    public Transaction getTransaction(Integer id) {
        return transactionRepository.findById(id).get();
    }
}
