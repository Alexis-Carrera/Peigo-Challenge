package com.peigo.challenge.customer.domain.entity;

import com.peigo.challenge.accounts.domain.entity.AccountEntity;
import com.peigo.challenge.audit.AuditModel;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "customer")
public class CustomerEntity extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @Column(unique = true)
    @NotNull
    @Size(min = 10, max = 13)
    private String nationalIdNumber;

    @OneToMany(mappedBy = "customer", orphanRemoval = true)
    private List<AccountEntity> listAccounts;

}
