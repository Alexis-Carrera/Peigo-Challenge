package com.peigo.challenge.customer.domain.repositories;

import com.peigo.challenge.customer.domain.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByNationalIdNumber(String nationalIdNumber);

}
