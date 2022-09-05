package com.example.gitbank.customer.converter;

import com.example.gitbank.customer.dto.CustomerRequest;
import com.example.gitbank.customer.dto.CustomerResponse;
import com.example.gitbank.customer.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverter {

    public Customer toCustomer(CustomerRequest customerRequest) {
        return Customer.builder()
                .securityNo(customerRequest.getSecurityNo())
                .firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .dateOfBirth(customerRequest.getDateOfBirth())
                .build();
    }

    public CustomerResponse fromCustomer(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .securityNo(customer.getSecurityNo())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .dateOfBirth(customer.getDateOfBirth())
                .build();
    }
}
