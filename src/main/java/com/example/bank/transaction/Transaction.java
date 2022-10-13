package com.example.bank.transaction;

import com.example.bank.balance.Balance;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
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
    private Float amount;
    @Enumerated(EnumType.STRING)
    private TransactionDirection direction;
    private String description;
    @ManyToOne
    @JoinColumn(
            name = "balance_id",
//            nullable = false,
            referencedColumnName = "id"
    )
//    @JsonBackReference
    private Balance balance;
}
