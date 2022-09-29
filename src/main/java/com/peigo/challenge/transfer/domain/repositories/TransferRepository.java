package com.peigo.challenge.transfer.domain.repositories;

import com.peigo.challenge.accounts.domain.entity.AccountEntity;
import com.peigo.challenge.customer.domain.entity.CustomerEntity;
import com.peigo.challenge.transfer.domain.entity.TransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransferRepository extends JpaRepository<TransferEntity, Long> {

}
