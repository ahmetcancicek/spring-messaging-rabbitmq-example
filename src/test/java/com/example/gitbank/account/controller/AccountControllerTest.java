package com.example.gitbank.account.controller;

import com.example.gitbank.account.dto.*;
import com.example.gitbank.account.model.Currency;
import com.example.gitbank.account.service.AccountServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AccountServiceImpl accountService;

    @MockBean
    private ApplicationEventPublisher applicationEventPublisher;

    @Test
    void givenIsValidAccount_whenCreateAccount_thenReturnAccount() throws Exception {
        // given
        AccountResponse accountResponse = AccountResponse.builder()
                .id(UUID.randomUUID().toString())
                .customerId(UUID.randomUUID().toString())
                .currency(Currency.USD)
                .name("My Debit Account")
                .balance(BigDecimal.TEN)
                .build();

        AccountRequest accountRequest = AccountRequest.builder()
                .customerId(UUID.randomUUID().toString())
                .currency(Currency.USD)
                .name("My Debit Account")
                .balance(BigDecimal.TEN)
                .build();

        given(accountService.createAccount(any(AccountRequest.class))).willReturn(accountResponse);

        // when
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(accountRequest));

        // then
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(accountResponse.getId()))
                .andExpect(jsonPath("$.data.name").value(accountResponse.getName()))
                .andExpect(jsonPath("$.data.balance").value(accountResponse.getBalance()))
                .andExpect(jsonPath("$.data.customerId").value(accountResponse.getCustomerId()))
                .andExpect(jsonPath("$.data.currency").value(accountResponse.getCurrency().toString()))
        ;
    }

    @Test
    void givenExistingAccount_whenGetAccount_thenReturnAccount() throws Exception {
        // given
        AccountResponse accountResponse = AccountResponse.builder()
                .id(UUID.randomUUID().toString())
                .customerId(UUID.randomUUID().toString())
                .currency(Currency.USD)
                .name("My Debit Account")
                .balance(BigDecimal.TEN)
                .build();

        given(accountService.getAccountById(accountResponse.getId())).willReturn(accountResponse);

        // when
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/api/v1/accounts/{id}", accountResponse.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(accountResponse.getId()))
                .andExpect(jsonPath("$.data.name").value(accountResponse.getName()))
                .andExpect(jsonPath("$.data.balance").value(accountResponse.getBalance()))
                .andExpect(jsonPath("$.data.customerId").value(accountResponse.getCustomerId()))
                .andExpect(jsonPath("$.data.currency").value(accountResponse.getCurrency().toString()))
        ;
    }

    @Test
    void givenExistingAccountAndSufficientBalance_whenWithdrawMoney_thenReturnAccountWithNewBalance() throws Exception {
        // given
        AccountResponse accountResponse = AccountResponse.builder()
                .id(UUID.randomUUID().toString())
                .customerId(UUID.randomUUID().toString())
                .currency(Currency.USD)
                .name("My Debit Account")
                .balance(BigDecimal.TEN)
                .build();

        WithdrawMoneyRequest withdrawMoneyRequest = WithdrawMoneyRequest.builder()
                .fromId(accountResponse.getId())
                .amount(BigDecimal.ONE)
                .build();

        given(accountService.withdrawMoney(any(WithdrawMoneyRequest.class))).willReturn(accountResponse);

        // when
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/v1/accounts/withdraw")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(withdrawMoneyRequest));

        // then
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(accountResponse.getId()))
                .andExpect(jsonPath("$.data.name").value(accountResponse.getName()))
                .andExpect(jsonPath("$.data.balance").value(accountResponse.getBalance()))
                .andExpect(jsonPath("$.data.customerId").value(accountResponse.getCustomerId()))
                .andExpect(jsonPath("$.data.currency").value(accountResponse.getCurrency().toString()));
    }

    @Test
    void givenExistingAccount_whenDepositMoney_thenReturnAccountWithNewBalance() throws Exception {
        // given
        AccountResponse accountResponse = AccountResponse.builder()
                .id(UUID.randomUUID().toString())
                .customerId(UUID.randomUUID().toString())
                .currency(Currency.USD)
                .name("My Debit Account")
                .balance(BigDecimal.TEN)
                .build();

        DepositMoneyRequest withdrawMoneyRequest = DepositMoneyRequest.builder()
                .toId(accountResponse.getId())
                .amount(BigDecimal.ONE)
                .build();

        given(accountService.depositMoney(any(DepositMoneyRequest.class))).willReturn(accountResponse);

        // when
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/v1/accounts/deposit")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(withdrawMoneyRequest));

        // then
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(accountResponse.getId()))
                .andExpect(jsonPath("$.data.name").value(accountResponse.getName()))
                .andExpect(jsonPath("$.data.balance").value(accountResponse.getBalance()))
                .andExpect(jsonPath("$.data.customerId").value(accountResponse.getCustomerId()))
                .andExpect(jsonPath("$.data.currency").value(accountResponse.getCurrency().toString()));
    }

    @Test
    void givenExistingAccount_whenTransferMoney_thenReturnTransactionResult() throws Exception {
        // given
        MoneyTransferResponse moneyTransferResponse = MoneyTransferResponse.builder()
                .fromCustomerId(UUID.randomUUID().toString())
                .fromCustomerId(UUID.randomUUID().toString())
                .toAccountId(UUID.randomUUID().toString())
                .toCustomerId(UUID.randomUUID().toString())
                .amount(BigDecimal.TEN)
                .build();

        MoneyTransferRequest moneyTransferRequest = MoneyTransferRequest.builder()
                .fromId(UUID.randomUUID().toString())
                .toId(UUID.randomUUID().toString())
                .amount(BigDecimal.TEN)
                .build();

        given(accountService.transferMoney(any(MoneyTransferRequest.class))).willReturn(moneyTransferResponse);

        // when
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/v1/accounts/transfer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(moneyTransferRequest));

        // then
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.fromAccountId").value(moneyTransferResponse.getFromAccountId()))
                .andExpect(jsonPath("$.data.fromCustomerId").value(moneyTransferResponse.getFromCustomerId()))
                .andExpect(jsonPath("$.data.toAccountId").value(moneyTransferResponse.getToAccountId()))
                .andExpect(jsonPath("$.data.toCustomerId").value(moneyTransferResponse.getToCustomerId()))
                .andExpect(jsonPath("$.data.amount").value(moneyTransferResponse.getAmount()));

    }
}