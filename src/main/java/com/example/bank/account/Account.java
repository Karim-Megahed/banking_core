package com.example.bank.account;

import com.example.bank.balance.Balance;
import com.example.bank.customer.Customer;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @SequenceGenerator(
            name = "account_id_sequence",
            sequenceName = "account_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "account_id_sequence"
    )

    private Integer id;
    @OneToOne(targetEntity = Customer.class)
    @JoinColumn(
            name = "customer_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Customer customer;
    @Column(nullable = false)
    private String country;
    @OneToMany(
            mappedBy = "account",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private List<Balance> balances = new ArrayList<>();
}
