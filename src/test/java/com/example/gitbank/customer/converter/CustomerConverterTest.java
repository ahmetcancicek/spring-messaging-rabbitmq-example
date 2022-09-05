package com.example.gitbank.customer.converter;

import com.example.gitbank.customer.dto.CustomerRequest;
import com.example.gitbank.customer.dto.CustomerResponse;
import com.example.gitbank.customer.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CustomerConverterTest {

    CustomerConverter customerConverter;

    @BeforeEach
    void setUp() {
        customerConverter = new CustomerConverter();
    }

    @Test
    void givenCustomerRequest_whenToCustomer_thenReturnCustomer() {
        CustomerRequest customerRequest = CustomerRequest.builder()
                .securityNo(UUID.randomUUID().toString())
                .firstName("George")
                .lastName("Chair")
                .dateOfBirth(LocalDate.of(1980, 11, 12))
                .build();

        Customer customer = customerConverter.toCustomer(customerRequest);

        assertEquals(customerRequest.getSecurityNo(), customer.getSecurityNo());
        assertEquals(customerRequest.getFirstName(), customer.getFirstName());
        assertEquals(customerRequest.getLastName(), customer.getLastName());
        assertEquals(customerRequest.getDateOfBirth(), customer.getDateOfBirth());
    }

    @Test
    void givenCustomer_whenFromCustomer_thenReturnCustomerResponse() {
        Customer customer = Customer.builder()
                .id(UUID.randomUUID().toString())
                .securityNo(UUID.randomUUID().toString())
                .firstName("George")
                .lastName("Chair")
                .dateOfBirth(LocalDate.of(1980, 11, 12))
                .build();

        CustomerResponse customerResponse = customerConverter.fromCustomer(customer);

        assertEquals(customer.getId(), customerResponse.getId());
        assertEquals(customer.getSecurityNo(), customerResponse.getSecurityNo());
        assertEquals(customer.getFirstName(), customerResponse.getFirstName());
        assertEquals(customer.getLastName(), customerResponse.getLastName());
        assertEquals(customer.getDateOfBirth(), customerResponse.getDateOfBirth());
    }
}