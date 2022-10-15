package com.example.bank.customer;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody @Valid CustomerRequest customerCreationRequest){
        Customer customer = customerService.createCustomer(customerCreationRequest);

        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }
}
