package com.example.bank.account;

import com.example.bank.balance.BalanceCurrency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
    @NotNull(message = "Customer id is invalid!")
    private Integer customerId;
    @NotBlank(message = "Country is invalid!")
    private String country;
    @NotEmpty(message = "Currencies are invalid!")
    private List<BalanceCurrency> currencies;
}