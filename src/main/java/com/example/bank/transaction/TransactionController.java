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

    @GetMapping(path = "{transactionId}")
    public Transaction getTransaction(@PathVariable("transactionId") Integer transactionId) throws ApplicationCustomException {
        return transactionService.getTransaction(transactionId);
    }

    @PostMapping
    public ResponseEntity<Object> createTransaction(@RequestBody @Valid TransactionRequest transactionRequest){
        try{
            Transaction transaction = transactionService.createTransaction(transactionRequest);

            return new ResponseEntity<>(transaction, HttpStatus.CREATED);
        } catch (ApplicationCustomException exception) {
            throw new RuntimeException(exception);
        }
    }
}
