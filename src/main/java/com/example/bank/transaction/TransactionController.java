package com.example.bank.transaction;

import com.example.bank.exception.AccountNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping(path = "{transactionId}")
    public Transaction getTransaction(@PathVariable("transactionId") Integer transactionId){
        return transactionService.getTransaction(transactionId);
    }

    @PostMapping
    public ResponseEntity<Object> createTransaction(@RequestBody TransactionRequest transactionRequest){
        try{
            Transaction transaction = transactionService.createTransaction(transactionRequest);

            return new ResponseEntity<>(transaction, HttpStatus.CREATED);
        } catch (AccountNotFoundException exception) {
            throw new RuntimeException(exception);
        }
    }
}
