package com.example.bank.transaction;

import com.example.bank.account.Account;
import com.example.bank.balance.Balance;

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
@Table(name = "transactions")
public class Transaction {
    @Id
    @SequenceGenerator(
            name = "transaction_id_sequence",
            sequenceName = "transaction_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "transaction_id_sequence"
    )

    private Integer id;
    @Column(nullable = false)
    private Float amount;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionDirection direction;
    @Column(nullable = false)
    private String description;
    @ManyToOne
    @JoinColumn(
            name = "balance_id",
            nullable = false,
            referencedColumnName = "id"
    )
    private Balance balance;
    @ManyToOne
    @JoinColumn(
            name = "account_id",
            nullable = false,
            referencedColumnName = "id"
    )
    private Account account;
}
