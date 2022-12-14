package com.example.bank.account;

import com.example.bank.balance.Balance;
import com.example.bank.balance.BalanceCurrency;
import com.example.bank.balance.BalanceService;
import com.example.bank.customer.Customer;
import com.example.bank.customer.CustomerRepository;
import com.example.bank.exception.ApplicationCustomException;
import com.example.bank.rabbitmq.MessagePublisher;
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
    private final MessagePublisher messagePublisher;

    public Account getAccount(Integer id) throws ApplicationCustomException {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ApplicationCustomException("Account not found!"));
    }

    public Account createAccount(AccountRequest request) throws ApplicationCustomException {
        List<Balance> balances = new ArrayList<>();
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ApplicationCustomException("Customer not found!"));
        Account account = Account.builder()
                .customer(customer)
                .country(request.getCountry())
                .build();

        accountRepository.saveAndFlush(account);

        messagePublisher.publishMessage(request);

        for (BalanceCurrency currency : request.getCurrencies()) {
            balances.add(balanceService.createBalance(account, currency));
        }

        account.setBalances(balances);

        return account;
    }
}
