package com.example.bank.account;

import com.example.bank.exception.ApplicationCustomException;
import com.example.bank.transaction.TransactionResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    @GetMapping(path = "{customerId}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable("customerId") Integer customerId) throws ApplicationCustomException {
        Account account = accountService.getAccount(customerId);
        AccountResponse response = new AccountResponse(account.getId(), account.getCustomer().getId(), account.getBalances());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody @Valid AccountRequest accountRequest) throws ApplicationCustomException {
        Account account = accountService.createAccount(accountRequest);
        AccountResponse response = new AccountResponse(account.getId(), account.getCustomer().getId(), account.getBalances());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path = "{accountId}/transactions")
    public ResponseEntity<Object> getAccountTransactions(@PathVariable("accountId") Integer accountId) throws ApplicationCustomException {
        List<TransactionResponse> transactions = accountService.getAccountTransactions(accountId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}