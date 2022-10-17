package com.example.bank.transaction;

import com.example.bank.account.Account;
import com.example.bank.account.AccountService;
import com.example.bank.balance.Balance;
import com.example.bank.balance.BalanceRepository;
import com.example.bank.balance.BalanceService;
import com.example.bank.exception.ApplicationCustomException;
import com.example.bank.rabbitmq.MessagePublisher;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final BalanceRepository balanceRepository;
    private final BalanceService balanceService;
    private final AccountService accountService;
    private final MessagePublisher messagePublisher;

    public List<TransactionResponse> getAccountTransactions(Integer accountId) throws ApplicationCustomException {
        accountService.getAccount(accountId);

        return transactionRepository.findByAccountId(accountId)
                .stream()
                .map((Transaction transaction) -> new TransactionResponse(
                                transaction.getAccount().getId(),
                                transaction.getId(),
                                transaction.getAmount(),
                                transaction.getDirection(),
                                transaction.getDescription()
                        )
                ).toList();
    }

    public TransactionCreationResponse createTransaction(Integer accountId, TransactionRequest request) throws ApplicationCustomException {
        if (request.getAmount() <= 0) {
            throw new ApplicationCustomException("Amount should larger than zero!");
        }

        Account account = accountService.getAccount(accountId);
        Balance balance = balanceService.getBalance(account, request.getCurrency());
        float newAmount = calculateNewAmount(request.getDirection(), request.getAmount(), balance.getAmount());

        if (newAmount < 0) {
            throw new ApplicationCustomException("Insufficient funds!");
        }

        balance.setAmount(newAmount);
        balanceRepository.saveAndFlush(balance);

        Transaction transaction = saveTransaction(request, balance, account);

        messagePublisher.publishMessage(request);

        return new TransactionCreationResponse(
                account.getId(),
                transaction.getId(),
                transaction.getAmount(),
                transaction.getDirection(),
                transaction.getDescription(),
                balance.getAmount()
        );
    }

    private Float calculateNewAmount(TransactionDirection direction, Float requestAmount, Float currentAmount) {
        return direction == TransactionDirection.IN
                ? currentAmount + requestAmount
                : currentAmount - requestAmount;
    }

    private Transaction saveTransaction(TransactionRequest request, Balance balance, Account account) {
        Transaction transaction = Transaction.builder()
                .direction(request.getDirection())
                .amount(request.getAmount())
                .balance(balance)
                .account(account)
                .description(request.getDescription())
                .build();

        transactionRepository.saveAndFlush(transaction);

        return transaction;
    }
}
