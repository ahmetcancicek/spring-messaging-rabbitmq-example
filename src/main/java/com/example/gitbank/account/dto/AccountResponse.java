package com.example.gitbank.account.dto;

import com.example.gitbank.account.model.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
public class AccountResponse {
    private String id;
    private String customerId;
    private String name;
    private BigDecimal balance;
    private Currency currency;
}
