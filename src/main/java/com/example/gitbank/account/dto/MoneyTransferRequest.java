package com.example.gitbank.account.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoneyTransferRequest {
    private String fromId;
    private String toId;
    private BigDecimal amount;
}
