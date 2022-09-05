package com.example.gitbank.customer.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    private String securityNo;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
}
