package com.example.bank.transaction;

import com.example.bank.account.Account;
import com.example.bank.account.AccountService;
import com.example.bank.balance.Balance;
import com.example.bank.balance.BalanceRepository;
import com.example.bank.balance.BalanceService;
import com.example.bank.exception.AccountNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final BalanceRepository balanceRepository;
    private final BalanceService balanceService;
    private final AccountService accountService;

    public Transaction createTransaction(TransactionRequest request) throws AccountNotFoundException {
        Account account = accountService.getAccount(request.accountId());
        Balance balance = balanceService.getBalance(account, request.currency());
        Float newAmount = request.direction() == TransactionDirection.IN
                ? balance.getAmount() + request.amount()
                : balance.getAmount() - request.amount();

        balance.setAmount(newAmount);
        balanceRepository.saveAndFlush(balance);

        if (balance.getId() == null){
            System.out.println("Balance is empty");
        }

        Transaction transaction = Transaction.builder()
                .direction(request.direction())
                .amount(request.amount())
                .balance(balance)
                .description(request.description())
                .build();
        transactionRepository.saveAndFlush(transaction);

        return transaction;
        }

    public Transaction getTransaction(Integer id) {
        return transactionRepository.findById(id).get();
    }
}
