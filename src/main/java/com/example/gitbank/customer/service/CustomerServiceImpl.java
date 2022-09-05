package com.example.gitbank.customer.service;

import com.example.gitbank.common.exception.ResourceNotFoundException;
import com.example.gitbank.customer.converter.CustomerConverter;
import com.example.gitbank.customer.dto.CustomerRequest;
import com.example.gitbank.customer.dto.CustomerResponse;
import com.example.gitbank.customer.model.Customer;
import com.example.gitbank.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerConverter customerConverter;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        log.info("Trying to create customer: [{}]", customerRequest.toString());
        if (existsBySecurityNo(customerRequest.getSecurityNo()))
            throw new ResourceNotFoundException("Customer", "Security No", customerRequest.getSecurityNo());
        Customer customer = customerConverter.toCustomer(customerRequest);
        customerRepository.save(customer);
        log.info("Customer saved to database: [{}]", customer);
        applicationEventPublisher.publishEvent(customer);
        return customerConverter.fromCustomer(customer);
    }

    public Optional<Customer> findById(String id) {
        return customerRepository.findById(id);
    }

    public Boolean existsBySecurityNo(String securityNo) {
        return customerRepository.existsBySecurityNo(securityNo);
    }

    public Optional<Customer> findBySecurityNo(String securityNo) {
        return customerRepository.findBySecurityNo(securityNo);
    }

    @Override
    public CustomerResponse getCustomerById(String id) {
        return customerRepository.findById(id).map(customerConverter::fromCustomer)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
    }

    @Override
    public CustomerResponse updateCustomer(String id, CustomerRequest customerRequest) {
        // TODO: Implement the method to update customer from db
        return null;
    }

    @Override
    public void deleteCustomer(String id) {
        // TODO: Implement the method to delete customer from db
    }
}
