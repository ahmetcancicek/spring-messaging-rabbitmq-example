package com.example.gitbank.customer.event;


import com.example.gitbank.customer.model.Customer;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreatedEvent {
    private String id;
    private String securityNo;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;

    public static CustomerCreatedEvent from(Customer customer) {
        return CustomerCreatedEvent.builder()
                .id(customer.getId())
                .securityNo(customer.getSecurityNo())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .dateOfBirth(customer.getDateOfBirth())
                .build();
    }
}
