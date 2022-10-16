package com.example.bank.customer;

import com.example.bank.rabbitmq.MessagePublisher;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final MessagePublisher messagePublisher;

    public Customer createCustomer(CustomerRequest request){
        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();

        customerRepository.saveAndFlush(customer);

        messagePublisher.publishMessage(request);

        return customer;
    }
}
