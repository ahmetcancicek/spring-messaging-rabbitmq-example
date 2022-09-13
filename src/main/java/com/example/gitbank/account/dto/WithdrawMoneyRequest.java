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
@AllArgsConstructor
@RequiredArgsConstructor
public class WithdrawMoneyRequest {

    @NotBlank
    @Size(max = 255)
    private String fromId;

    @Positive
    @Size(max = 9)
    private BigDecimal amount;
}
