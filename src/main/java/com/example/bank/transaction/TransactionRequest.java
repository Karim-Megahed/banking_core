package com.example.bank.transaction;

import com.example.bank.balance.BalanceCurrency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    @NotNull(message = "Account id is invalid!")
    private Integer accountId;
    @NotBlank(message = "Description is invalid!")
    private String description;
    @NotNull(message = "Currency is invalid!")
    private BalanceCurrency currency;
    @NotNull(message = "Direction is invalid!")
    private TransactionDirection direction;
    @NotNull(message = "Amount is invalid!")
    private Float amount;
}
