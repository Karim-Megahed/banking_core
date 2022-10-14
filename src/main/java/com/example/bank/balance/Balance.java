package com.example.bank.balance;

import com.example.bank.account.Account;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
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
    private Float amount;
    @Enumerated(EnumType.STRING)
    private BalanceCurrency currency;
}
