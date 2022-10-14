package com.example.bank.balance;

import com.example.bank.account.Account;
import com.example.bank.exception.ApplicationCustomException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BalanceService {
    private final BalanceRepository balanceRepository;

    public Balance getBalance(Account account, BalanceCurrency currency) throws ApplicationCustomException {
        List<Balance> balances = balanceRepository.findByCurrencyAndAccountId(currency, account.getId());

        if(balances.size() == 0){
            throw new ApplicationCustomException("Balance cannot be found!");
        }

        return balances.get(0);
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
