package com.example.bank.transaction;

import com.example.bank.exception.ApplicationCustomException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionCreationResponse> createTransaction(@RequestBody @Valid TransactionRequest transactionRequest) throws ApplicationCustomException {
        TransactionCreationResponse transaction = transactionService.createTransaction(transactionRequest);

        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }
}
