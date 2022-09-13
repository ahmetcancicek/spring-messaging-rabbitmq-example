package com.example.gitbank.notification.messaging;

import com.example.gitbank.account.model.Currency;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private String id;
    private String name;
    private String customerId;
    private BigDecimal balance;
    private Currency currency;
}
