package com.example.gitbank.account.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DepositMoneyRequest {
    private String toId;
    private BigDecimal amount;
}
