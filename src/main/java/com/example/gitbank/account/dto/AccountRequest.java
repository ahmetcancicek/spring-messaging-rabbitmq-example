package com.example.gitbank.account.dto;

import com.example.gitbank.account.model.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;


@Setter
@Getter
@Builder
@ToString
public class AccountRequest {

    @NotBlank
    private String customerId;

    @NotBlank
    private String securityNo;

    @NotBlank
    private String name;

    @Builder.Default
    private BigDecimal balance = BigDecimal.ZERO;

    @NotBlank
    private Currency currency;
}
