package com.example.gitbank.customer.repository;

import com.example.gitbank.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    Optional<Customer> findBySecurityNo(String securityNo);

    @Override
    Optional<Customer> findById(String id);

    Boolean existsBySecurityNo(String securityNo);
}
