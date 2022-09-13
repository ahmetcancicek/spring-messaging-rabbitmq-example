package com.example.gitbank.customer.model;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;


@Setter
@Getter
@Builder
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", unique = false, nullable = false)
    private String id;

    @Column(unique = true, nullable = false)
    private String securityNo;

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
}
