package com.peigo.challenge.transfer.domain.model;

import com.peigo.challenge.accounts.domain.model.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class Transfer {
    Long transferNumber;
    Account rootAccount;
    Account destinationAccount;
}