package com.example.gitbank.customer.converter;

import com.example.gitbank.customer.dto.CustomerRequest;
import com.example.gitbank.customer.dto.CustomerResponse;
import com.example.gitbank.customer.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverter {

    public Customer toCustomer(CustomerRequest customerRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .dateOfBirth(customerRequest.getDateOfBirth())
                .build();

        return customer;
    }

    public CustomerResponse fromCustomer(Customer customer) {
        CustomerResponse customerResponse = CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .dateOfBirth(customer.getDateOfBirth())
                .build();

        return customerResponse;
    }
}
