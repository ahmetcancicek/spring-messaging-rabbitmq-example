package com.example.gitbank.account.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class WithdrawMoneyRequest {
    private String fromId;
    private BigDecimal amount;
}
