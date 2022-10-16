package com.example.bank.account;

import com.example.bank.exception.ApplicationCustomException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    @GetMapping(path = "{accountId}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable("accountId") Integer accountId) throws ApplicationCustomException {
        Account account = accountService.getAccount(accountId);
        AccountResponse response = new AccountResponse(account.getId(), account.getCustomer().getId(), account.getBalances());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody @Valid AccountRequest accountRequest) throws ApplicationCustomException {
        Account account = accountService.createAccount(accountRequest);
        AccountResponse response = new AccountResponse(account.getId(), account.getCustomer().getId(), account.getBalances());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}