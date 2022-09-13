package com.example.gitbank.account.dto;

import com.example.gitbank.account.model.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;


@Setter
@Getter
@Builder
@ToString
public class AccountRequest {

    @NotBlank
    @Size(max = 255)
    private String customerId;

    @NotBlank
    @Size(max = 255)
    private String name;

    @Positive
    @Size(max = 9)
    @Builder.Default
    private BigDecimal balance = BigDecimal.ZERO;

    @NotBlank
    @Size(max = 3)
    private Currency currency;
}
