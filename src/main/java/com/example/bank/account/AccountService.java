package com.example.bank.account;

import com.example.bank.customer.Customer;
import com.example.bank.customer.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor

public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public Optional<Account> getAccount(Integer id){
        return accountRepository.findById(id);
    }

    public void createAccount(AccountRequest request){
        System.out.println(request);

        Customer customer = customerRepository.findById(request.customerId()).get();

        if(customer.getId() == null){
            throw new IllegalStateException("Customer not found!");
        }

        Account account = Account.builder()
                .customer(customer)
                .country(request.country())
                .createdAt(LocalDateTime.now())
                .build();

        accountRepository.saveAndFlush(account);
    }
}
