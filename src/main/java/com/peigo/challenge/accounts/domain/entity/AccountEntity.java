package com.peigo.challenge.accounts.domain.entity;

import com.peigo.challenge.audit.AuditModel;
import com.peigo.challenge.customer.domain.entity.CustomerEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "account")
@ToString(exclude = {"customer"})
public class AccountEntity extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private CustomerEntity customer;

    @Column(unique = true)
    @NotNull
    @Size(min = 10, max = 10)
    private String accountNumber;

    private BigDecimal balance;
}
