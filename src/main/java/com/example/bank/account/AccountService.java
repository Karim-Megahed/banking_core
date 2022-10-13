package com.example.bank.account;

import com.example.bank.balance.Balance;
import com.example.bank.balance.BalanceCurrency;
import com.example.bank.balance.BalanceService;
import com.example.bank.customer.Customer;
import com.example.bank.customer.CustomerRepository;
import com.example.bank.exception.ModelNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final BalanceService balanceService;

    public Account getAccount(Integer id) throws ModelNotFoundException {
        return accountRepository.findById(id)
                .orElseThrow(() ->  new ModelNotFoundException("Account not found!"));
    }

    public Account createAccount(AccountRequest request) throws ModelNotFoundException {
        List<Balance> balances = new ArrayList<>();
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() ->  new ModelNotFoundException("Customer not found!"));
        Account account = Account.builder()
                .customer(customer)
                .country(request.getCountry())
                .build();

        accountRepository.saveAndFlush(account);

        for (BalanceCurrency currency : request.getCurrencies()) {
            balances.add(balanceService.createBalance(account, currency));
        }

        account.setBalances(balances);

        return account;
    }
}
