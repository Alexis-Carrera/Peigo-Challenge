package com.peigo.challenge.accounts.application.dto.response;

import com.peigo.challenge.accounts.domain.entity.AccountEntity;
import com.peigo.challenge.accounts.domain.model.Account;
import com.peigo.challenge.customer.domain.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class AccountList {
    Customer customer;

    List<Account> accounts;

}