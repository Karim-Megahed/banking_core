package com.example.bank.account;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    @GetMapping(path = "{customerId}")
    public Account getAccount(@PathVariable("customerId") Integer customerId){
        return accountService.getAccount(customerId);
    }

    @PostMapping
    public ResponseEntity<Object> createAccount(@RequestBody AccountRequest accountRequest){
        try{
            Account account = accountService.createAccount(accountRequest);

            return new ResponseEntity<>(account, HttpStatus.CREATED);
        }catch (IllegalStateException exception){
            return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
        }
    }
}
