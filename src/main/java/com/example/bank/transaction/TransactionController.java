package com.example.bank.transaction;

import com.example.bank.exception.ApplicationCustomException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/accounts/{accountId}/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<Object> getAccountTransactions(@PathVariable("accountId") Integer accountId) throws ApplicationCustomException {
        List<TransactionResponse> transactions = transactionService.getAccountTransactions(accountId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TransactionCreationResponse> createTransaction(@PathVariable("accountId") Integer accountId, @RequestBody @Valid TransactionRequest transactionRequest) throws ApplicationCustomException {
        TransactionCreationResponse transaction = transactionService.createTransaction(accountId, transactionRequest);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }
}
