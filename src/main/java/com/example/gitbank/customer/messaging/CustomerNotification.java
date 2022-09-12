package com.example.gitbank.customer.messaging;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;


@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomerNotification {
    private String id;
    private String securityNo;
    private String firstName;
    private String lastName;
}
