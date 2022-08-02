package com.example.gitbank.customer.service;

import com.example.gitbank.customer.converter.CustomerConverter;
import com.example.gitbank.customer.dto.CustomerRequest;
import com.example.gitbank.customer.dto.CustomerResponse;
import com.example.gitbank.customer.model.Customer;
import com.example.gitbank.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerProducer customerProducer;

    private final CustomerConverter customerConverter;

    public Optional<CustomerResponse> createCustomer(CustomerRequest customerRequest) {
        log.info("Trying to create customer: [" + customerRequest.toString() + "]");
        Customer customer = customerConverter.toCustomer(customerRequest);
        customerRepository.save(customer);
        log.info("Customer saved to database: [{}]" + customer.toString());
        customerProducer.createAccount(customer.getId());
        return Optional.of(customerConverter.fromCustomer(customer));
    }
}
