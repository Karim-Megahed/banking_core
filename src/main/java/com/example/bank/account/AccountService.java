package com.example.bank.account;

import com.example.bank.balance.Balance;
import com.example.bank.balance.BalanceService;
import com.example.bank.customer.Customer;
import com.example.bank.customer.CustomerRepository;
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

    public Account getAccount(Integer id){
        return accountRepository.findById(id).get();
    }

    public Account createAccount(AccountRequest request){
        Customer customer = customerRepository.findById(request.customerId()).get();
        List<Balance> balances = new ArrayList<>();

        if(customer.getId() == null){
            throw new IllegalStateException("Customer not found!");
        }

        Account account = Account.builder()
                .customer(customer)
                .country(request.country())
                .build();

        accountRepository.saveAndFlush(account);

        for (String currency : request.currencies()) {
            balances.add(balanceService.createBalance(account, currency));
        }

        account.setBalances(balances);

        return account;
    }
}
