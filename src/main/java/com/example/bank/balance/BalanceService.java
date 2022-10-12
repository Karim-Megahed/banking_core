package com.example.bank.balance;

import com.example.bank.account.Account;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BalanceService {
    private final BalanceRepository balanceRepository;

    public Balance createBalance(Account account, String currency){
        Balance balance = Balance.builder()
                .account(account)
                .currency(currency)
                .amount(0L)
                .build();

        balanceRepository.saveAndFlush(balance);

        return balance;
    }

}
