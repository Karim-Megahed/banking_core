package com.example.bank.customer;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public void createCustomer(@RequestBody @Valid CustomerRequest customerCreationRequest){
        customerService.createCustomer(customerCreationRequest);
    }
}
