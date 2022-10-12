package com.example.bank.balance;

import com.example.bank.account.Account;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "balances")
public class Balance {
    @Id
    @SequenceGenerator(
            name = "balance_id_sequence",
            sequenceName = "balance_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "balance_id_sequence"
    )

    private Integer id;
    @ManyToOne
    @JoinColumn(
            name = "account_id",
            nullable = false,
            referencedColumnName = "id"
    )
    @JsonBackReference
    private Account account;
    private Long amount;
    private String currency;

}
