package com.peigo.challenge.transfer.domain.entity;

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
@Table(name = "transfer")
public class TransferEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 10, max = 10)
    private String rootAccountNumber;

    @NotNull
    @Size(min = 10, max = 10)
    private String destinationAccountNumber;

    private BigDecimal balance;
}
