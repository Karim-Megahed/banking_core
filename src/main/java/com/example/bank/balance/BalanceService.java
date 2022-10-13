package com.example.bank.balance;

import com.example.bank.account.Account;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BalanceService {
    private final BalanceRepository balanceRepository;

    public Balance getBalance(Account account, String currency){
        return balanceRepository.findByCurrencyAndAccountId(currency, account.getId()).get(0);
    }

    public Balance createBalance(Account account, BalanceCurrency currency){
        Balance balance = Balance.builder()
                .account(account)
                .currency(currency)
                .amount((float) 0)
                .build();

        balanceRepository.saveAndFlush(balance);

        return balance;
    }
}
