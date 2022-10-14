package com.example.bank.transaction;

import com.example.bank.account.Account;
import com.example.bank.account.AccountService;
import com.example.bank.balance.Balance;
import com.example.bank.balance.BalanceRepository;
import com.example.bank.balance.BalanceService;
import com.example.bank.exception.ApplicationCustomException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final BalanceRepository balanceRepository;
    private final BalanceService balanceService;
    private final AccountService accountService;

    public TransactionCreationResponse createTransaction(TransactionRequest request) throws ApplicationCustomException {
        if(request.getAmount() <= 0){
            throw new ApplicationCustomException("Amount should larger than zero!");
        }

        Account account = accountService.getAccount(request.getAccountId());
        Balance balance = balanceService.getBalance(account, request.getCurrency());
        float newAmount = request.getDirection() == TransactionDirection.IN
                ? balance.getAmount() + request.getAmount()
                : balance.getAmount() - request.getAmount();

        if(newAmount < 0){
            throw new ApplicationCustomException("Insufficient funds!");
        }

        balance.setAmount(newAmount);
        balanceRepository.saveAndFlush(balance);

        Transaction transaction = Transaction.builder()
                .direction(request.getDirection())
                .amount(request.getAmount())
                .balance(balance)
                .account(account)
                .description(request.getDescription())
                .build();
        transactionRepository.saveAndFlush(transaction);

        TransactionCreationResponse transactionCreationResponse = new TransactionCreationResponse(
                account.getId(),
                transaction.getId(),
                transaction.getAmount(),
                transaction.getDirection(),
                transaction.getDescription(),
                balance.getAmount()
        );

        return transactionCreationResponse;
    }
}
