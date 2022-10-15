package com.example.bank.customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void itShouldSaveCustomer() {
        Customer customer = new Customer("karim", "megahed", "karim@gmail.com");

        customerRepository.save(customer);

        Optional<Customer> customerFromDB = customerRepository.findById(customer.getId());

        assertThat(customerFromDB)
                .isPresent()
                .hasValueSatisfying(c -> assertThat(c).isEqualTo(customer));
    }

    @Test
    void itShouldNotSaveCustomerWhenFirstNameIsNull() {
        Customer customer = new Customer( 1,null, "megahed", "karim@gmail.com");
        customerRepository.save(customer);

        Optional<Customer> customerFromDB = customerRepository.findById(customer.getId());

        assertThat(customerFromDB).isEmpty();

    }

    @Test
    void itShouldNotSaveCustomerWhenLastNameIsNull() {
        Customer customer = new Customer( 1,"karim", null, "karim@gmail.com");
        customerRepository.save(customer);

        Optional<Customer> customerFromDB = customerRepository.findById(customer.getId());

        assertThat(customerFromDB).isEmpty();
    }

    @Test
    void itShouldNotSaveCustomerWhenEmailIsNull() {
        Customer customer = new Customer( 1,"karim", "megahed", null);
        customerRepository.save(customer);

        Optional<Customer> customerFromDB = customerRepository.findById(customer.getId());

        assertThat(customerFromDB).isEmpty();
    }
}