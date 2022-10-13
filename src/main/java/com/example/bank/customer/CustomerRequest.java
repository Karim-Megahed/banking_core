package com.example.bank.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    @NotBlank(message = "First name is invalid!")
    private String firstName;
    @NotBlank(message = "Last name is invalid!")
    private String lastName;
    @Email
    private String email;
}
