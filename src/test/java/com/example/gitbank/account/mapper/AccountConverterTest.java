package com.example.gitbank.account.mapper;

import com.example.gitbank.account.dto.AccountRequest;
import com.example.gitbank.account.dto.AccountResponse;
import com.example.gitbank.account.model.Account;
import com.example.gitbank.account.model.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountConverterTest {

    private AccountConverter accountConverter;

    @BeforeEach
    void setUp() {
        accountConverter = new AccountConverter();
    }

    @Test
    void givenAccountRequest_whenToAccount_thenReturnAccount() {
        AccountRequest accountRequest = AccountRequest.builder()
                .securityNo(UUID.randomUUID().toString())
                .accountName("My Debit Account")
                .balance(BigDecimal.ONE)
                .currency(Currency.USD)
                .customerId(UUID.randomUUID().toString())
                .build();

        Account account = accountConverter.toAccount(accountRequest);

        assertEquals(accountRequest.getCustomerId(), account.getCustomerId());
        assertEquals(accountRequest.getAccountName(), account.getName());
        assertEquals(accountRequest.getBalance(), account.getBalance());
        assertEquals(accountRequest.getCurrency(), account.getCurrency());
    }

    @Test
    void givenAccount_whenFromAccount_thenReturnAccountResponse() {
        Account account = Account.builder()
                .name("My Debit Account")
                .balance(BigDecimal.ONE)
                .currency(Currency.USD)
                .customerId(UUID.randomUUID().toString())
                .build();

        AccountResponse accountResponse = accountConverter.fromAccount(account);

        assertEquals(account.getName(), accountResponse.getName());
        assertEquals(account.getId(), accountResponse.getId());
        assertEquals(account.getCustomerId(), accountResponse.getCustomerId());
        assertEquals(account.getBalance(), accountResponse.getBalance());
        assertEquals(account.getCurrency(), accountResponse.getCurrency());

    }

}