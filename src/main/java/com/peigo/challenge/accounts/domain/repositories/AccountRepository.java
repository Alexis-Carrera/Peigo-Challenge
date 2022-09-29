package com.peigo.challenge.accounts.domain.repositories;

import com.peigo.challenge.accounts.domain.entity.AccountEntity;
import com.peigo.challenge.customer.domain.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByAccountNumber(String accountNumber);

    Optional<List<AccountEntity>> findAllByCustomer(CustomerEntity customer);

}
