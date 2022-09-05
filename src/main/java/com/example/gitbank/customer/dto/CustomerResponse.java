package com.example.gitbank.customer.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {
    private String id;
    private String securityNo;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
}
