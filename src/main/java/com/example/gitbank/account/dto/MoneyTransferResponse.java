package com.example.gitbank.account.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoneyTransferResponse {
    private String fromAccountId;
    private String fromCustomerId;
    private String toAccountId;
    private String toCustomerId;
    private BigDecimal amount;
}
