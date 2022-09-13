package com.example.gitbank.customer.service;

import com.example.gitbank.customer.dto.CustomerRequest;
import com.example.gitbank.customer.dto.CustomerResponse;
import com.example.gitbank.customer.model.Customer;

import java.util.Optional;

public interface CustomerService {
    CustomerResponse createCustomer(CustomerRequest customerRequest);

    Optional<Customer> findById(String id);

    Optional<Customer> findBySecurityNo(String securityNo);

    CustomerResponse getCustomerById(String id);

    Boolean existsBySecurityNoAndIdNot(String securityNo, String id);

    CustomerResponse updateCustomer(String id, CustomerRequest customerRequest);

    void deleteCustomer(String id);
}
