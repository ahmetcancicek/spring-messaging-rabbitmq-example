package com.example.gitbank.account.controller;

import com.example.gitbank.account.dto.AccountRequest;
import com.example.gitbank.account.dto.AccountResponse;
import com.example.gitbank.account.dto.DepositMoneyRequest;
import com.example.gitbank.account.dto.WithdrawMoneyRequest;
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
    void whenIsValidAccount_whenCreateAccount_thenReturnAccount() throws Exception {
        // given
        AccountResponse accountResponse = AccountResponse.builder()
                .id(UUID.randomUUID().toString())
                .customerId(UUID.randomUUID().toString())
                .currency(Currency.USD)
                .name("My Debit Account")
                .balance(BigDecimal.TEN)
                .build();

        given(accountService.createAccount(any())).willReturn(accountResponse);

        // when
        AccountRequest accountRequest = AccountRequest.builder()
                .customerId(UUID.randomUUID().toString())
                .currency(Currency.USD)
                .name("My Debit Account")
                .balance(BigDecimal.TEN)
                .build();

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
    void whenExistingAccount_whenGetAccount_thenReturnAccount() throws Exception {
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
    void whenExistingAccountAndSufficientBalance_whenWithdrawMoney_thenReturnAccountWithNewBalance() throws Exception {
        // given
        AccountResponse accountResponse = AccountResponse.builder()
                .id(UUID.randomUUID().toString())
                .customerId(UUID.randomUUID().toString())
                .currency(Currency.USD)
                .name("My Debit Account")
                .balance(BigDecimal.TEN)
                .build();

        given(accountService.withdrawMoney(any())).willReturn(accountResponse);

        // when
        WithdrawMoneyRequest withdrawMoneyRequest = WithdrawMoneyRequest.builder()
                .fromId(accountResponse.getId())
                .amount(BigDecimal.ONE)
                .build();

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
    void whenExistingAccount_whenDepositMonet_thenReturnAccountWithNewBalance() throws Exception {
        // given
        AccountResponse accountResponse = AccountResponse.builder()
                .id(UUID.randomUUID().toString())
                .customerId(UUID.randomUUID().toString())
                .currency(Currency.USD)
                .name("My Debit Account")
                .balance(BigDecimal.TEN)
                .build();

        given(accountService.depositMoney(any())).willReturn(accountResponse);

        // when
        DepositMoneyRequest withdrawMoneyRequest = DepositMoneyRequest.builder()
                .toId(accountResponse.getId())
                .amount(BigDecimal.ONE)
                .build();

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
}