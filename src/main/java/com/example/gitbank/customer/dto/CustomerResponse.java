package com.example.gitbank.customer.dto;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {
    private String id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
}
