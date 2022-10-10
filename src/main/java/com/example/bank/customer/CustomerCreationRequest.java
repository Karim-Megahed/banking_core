package com.example.bank.customer;

public record CustomerCreationRequest(
        String firstName,
        String lastName,
        String email) {
}
