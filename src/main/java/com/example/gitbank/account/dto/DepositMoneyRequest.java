package com.example.gitbank.account.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DepositMoneyRequest {

    @NotBlank
    @Size(max = 255)
    private String toId;

    @Positive
    @Size(max = 9)
    private BigDecimal amount;
}
