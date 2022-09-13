package com.example.gitbank.account.service;

import com.example.gitbank.account.dto.AccountRequest;
import com.example.gitbank.account.dto.AccountResponse;
import com.example.gitbank.account.dto.MoneyTransferRequest;
import com.example.gitbank.account.dto.MoneyTransferResponse;
import com.example.gitbank.account.mapper.AccountConverter;
import com.example.gitbank.account.model.Account;
import com.example.gitbank.account.model.Currency;
import com.example.gitbank.account.repository.AccountRepository;
import com.example.gitbank.common.exception.InsufficientErrorException;
import com.example.gitbank.common.exception.InsufficientMoneyTransferErrorException;
import com.example.gitbank.common.exception.NotEqualCurrentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    private AccountServiceImpl accountService;
    private AccountRepository accountRepository;
    private AccountConverter accountConverter;
    private AccountNotificationServiceImpl accountNotificationService;

    @BeforeEach
    void setUp() {
        accountRepository = Mockito.mock(AccountRepository.class);
        accountConverter = new AccountConverter();
        accountNotificationService = Mockito.mock(AccountNotificationServiceImpl.class);
        accountService = new AccountServiceImpl(accountRepository, accountConverter, accountNotificationService);
    }

    @Test
    void givenIsValid_whenCreateAccount_thenReturnAccount() {
        // given
        Account account = Account.builder()
                .id(UUID.randomUUID().toString())
                .customerId(UUID.randomUUID().toString())
                .currency(Currency.USD)
                .name("My Debit Account")
                .balance(BigDecimal.TEN)
                .build();

        AccountRequest accountRequest = AccountRequest.builder()
                .customerId(account.getCustomerId())
                .currency(account.getCurrency())
                .name("My Debit Account")
                .balance(account.getBalance())
                .build();

        given(accountRepository.save(any(Account.class))).willReturn(account);

        // when
        AccountResponse expectedAccountResponse = accountService.createAccount(accountRequest);

        // then
        verify(accountRepository, times(1)).save(any());
        assertEquals(account.getId(), expectedAccountResponse.getId());
        assertEquals(account.getName(), expectedAccountResponse.getName());
        assertEquals(account.getCurrency(), expectedAccountResponse.getCurrency());
        assertEquals(account.getBalance(), expectedAccountResponse.getBalance());

    }

    @Test
    void givenIsValid_whenUpdateAccount_thenReturnAccount() {
        // given
        Account account = Account.builder()
                .id(UUID.randomUUID().toString())
                .customerId(UUID.randomUUID().toString())
                .currency(Currency.USD)
                .name("My Debit Account")
                .balance(BigDecimal.TEN)
                .build();

        AccountRequest accountRequest = AccountRequest.builder()
                .customerId(account.toString())
                .currency(account.getCurrency())
                .name("My Debit Account")
                .balance(BigDecimal.TEN.add(BigDecimal.TEN))
                .build();

        given(accountRepository.findById(any(String.class))).willReturn(Optional.ofNullable(account));

        // when
        AccountResponse expectedAccountResponse = accountService.updateAccount(account.getId(), accountRequest);

        // then
        verify(accountRepository, times(1)).save(account);
        assertEquals(account.getName(), expectedAccountResponse.getName());
        assertEquals(account.getCurrency(), expectedAccountResponse.getCurrency());
        assertEquals(account.getBalance(), expectedAccountResponse.getBalance());
        assertEquals(account.getCustomerId(), expectedAccountResponse.getCustomerId());
    }

    @Test
    void givenExistingAccount_whenFindById_thenReturnAccount() {
        // given
        Account account = Account.builder()
                .id(UUID.randomUUID().toString())
                .customerId(UUID.randomUUID().toString())
                .currency(Currency.USD)
                .name("My Debit Account")
                .balance(BigDecimal.TEN)
                .build();

        given(accountRepository.findById(any(String.class))).willReturn(Optional.ofNullable(account));

        // when
        Optional<Account> expectedAccount = accountService.findById(account.getId());

        // then
        verify(accountRepository, times(1)).findById(any());
        assertTrue(expectedAccount.isPresent());
    }

    @Test
    void givenExistingAccountById_whenGetAccountById_thenReturnAccount() {
        // given
        Account account = Account.builder()
                .id(UUID.randomUUID().toString())
                .customerId(UUID.randomUUID().toString())
                .currency(Currency.USD)
                .name("My Debit Account")
                .balance(BigDecimal.TEN)
                .build();

        given(accountRepository.findById(any(String.class))).willReturn(Optional.ofNullable(account));

        // when
        AccountResponse expectedAccountResponse = accountService.getAccountById(account.getId());

        // then
        verify(accountRepository, times(1)).findById(any());
        assertEquals(account.getName(), expectedAccountResponse.getName());
        assertEquals(account.getCurrency(), expectedAccountResponse.getCurrency());
        assertEquals(account.getBalance(), expectedAccountResponse.getBalance());
        assertEquals(account.getCustomerId(), expectedAccountResponse.getCustomerId());
    }

    @Test
    void givenExistingAccountAndSufficientBalance_whenWithdrawMoney_thenReturnAccountWithNewBalance() {
        // given
        Account account = Account.builder()
                .id(UUID.randomUUID().toString())
                .customerId(UUID.randomUUID().toString())
                .currency(Currency.USD)
                .name("My Debit Account")
                .balance(BigDecimal.TEN)
                .build();

        given(accountRepository.findById(any(String.class))).willReturn(Optional.ofNullable(account));
        given(accountRepository.save(any(Account.class))).willReturn(account);

        // when
        AccountResponse expectedAccountResponse = accountService.withdrawMoney(account.getId(), BigDecimal.TEN);

        // then
        verify(accountRepository, times(1)).findById(any());
        verify(accountRepository, times(1)).save(any());
        assertEquals(account.getBalance(), BigDecimal.ZERO);
        assertEquals(account.getName(), expectedAccountResponse.getName());
        assertEquals(account.getCurrency(), expectedAccountResponse.getCurrency());
        assertEquals(account.getBalance(), expectedAccountResponse.getBalance());
        assertEquals(account.getCustomerId(), expectedAccountResponse.getCustomerId());
    }

    @Test
    void givenExistingAccountAndNotSufficientBalance_whenWithdrawMoney_thenThrowException() {
        // given
        Account account = Account.builder()
                .id(UUID.randomUUID().toString())
                .customerId(UUID.randomUUID().toString())
                .currency(Currency.USD)
                .name("My Debit Account")
                .balance(BigDecimal.TEN)
                .build();

        given(accountRepository.findById(any(String.class))).willReturn(Optional.ofNullable(account));

        // when
        Throwable throwable = catchThrowable(() -> {
            accountService.withdrawMoney(account.getId(), new BigDecimal(100));
        });

        assertThat(throwable).isInstanceOf(InsufficientErrorException.class);
    }

    @Test
    void givenExistingAccount_whenDepositMoney_thenReturnAccountWithNewBalance() {
        // given
        Account account = Account.builder()
                .id(UUID.randomUUID().toString())
                .customerId(UUID.randomUUID().toString())
                .currency(Currency.USD)
                .name("My Debit Account")
                .balance(BigDecimal.TEN)
                .build();

        given(accountRepository.findById(any(String.class))).willReturn(Optional.ofNullable(account));
        given(accountRepository.save(any(Account.class))).willReturn(account);

        // when
        AccountResponse expectedAccountResponse = accountService.depositMoney(account.getId(), new BigDecimal(100));

        // then
        verify(accountRepository, times(1)).save(any());
        verify(accountRepository, times(1)).findById(any());
        assertEquals(account.getBalance(), new BigDecimal(110));
        assertEquals(account.getName(), expectedAccountResponse.getName());
        assertEquals(account.getCurrency(), expectedAccountResponse.getCurrency());
        assertEquals(account.getBalance(), expectedAccountResponse.getBalance());
        assertEquals(account.getCustomerId(), expectedAccountResponse.getCustomerId());
    }

    @Test
    void givenExistingSourceAndTargetAccount_whenTransferMoney_thenReturnTransfer() {
        // given
        Account sourceAccount = Account.builder()
                .id(UUID.randomUUID().toString())
                .customerId(UUID.randomUUID().toString())
                .currency(Currency.USD)
                .name("My Debit Account")
                .balance(BigDecimal.TEN)
                .build();

        Account targetAccount = Account.builder()
                .id(UUID.randomUUID().toString())
                .customerId(UUID.randomUUID().toString())
                .currency(Currency.USD)
                .name("My Debit Account")
                .balance(BigDecimal.TEN)
                .build();

        given(accountRepository.findById(sourceAccount.getId())).willReturn(Optional.of(sourceAccount));
        given(accountRepository.findById(targetAccount.getId())).willReturn(Optional.of(targetAccount));

        MoneyTransferRequest moneyTransferRequest = MoneyTransferRequest.builder()
                .fromId(sourceAccount.getId())
                .toId(targetAccount.getId())
                .amount(BigDecimal.ONE)
                .build();

        // when
        MoneyTransferResponse moneyTransferResponse = accountService.transferMoney(moneyTransferRequest);

        // then
        assertEquals(sourceAccount.getId(), moneyTransferResponse.getFromAccountId());
        assertEquals(sourceAccount.getCustomerId(), moneyTransferResponse.getFromCustomerId());
        assertEquals(targetAccount.getId(), moneyTransferResponse.getToAccountId());
        assertEquals(targetAccount.getCustomerId(), moneyTransferResponse.getToCustomerId());
        assertEquals(moneyTransferRequest.getAmount(), moneyTransferResponse.getAmount());
    }

    @Test
    void givenAccountWithDifferentCurrency_whenTransferMoney_thenThrowException() {
        // given
        Account sourceAccount = Account.builder()
                .id(UUID.randomUUID().toString())
                .customerId(UUID.randomUUID().toString())
                .currency(Currency.USD)
                .name("My Debit Account")
                .balance(BigDecimal.TEN)
                .build();

        Account targetAccount = Account.builder()
                .id(UUID.randomUUID().toString())
                .customerId(UUID.randomUUID().toString())
                .currency(Currency.EUR)
                .name("My Debit Account")
                .balance(BigDecimal.TEN)
                .build();

        given(accountRepository.findById(sourceAccount.getId())).willReturn(Optional.of(sourceAccount));
        given(accountRepository.findById(targetAccount.getId())).willReturn(Optional.of(targetAccount));

        MoneyTransferRequest moneyTransferRequest = MoneyTransferRequest.builder()
                .fromId(sourceAccount.getId())
                .toId(targetAccount.getId())
                .amount(BigDecimal.ONE)
                .build();

        // when
        Throwable throwable = catchThrowable(() -> {
            accountService.transferMoney(moneyTransferRequest);
        });

        // then
        assertThat(throwable).isInstanceOf(NotEqualCurrentException.class);
    }

    @Test
    void givenExistingSourceAndTargetAccountWithInsufficientBalance_whenTransferMoney_thenReturnTransfer() {
        // given
        Account sourceAccount = Account.builder()
                .id(UUID.randomUUID().toString())
                .customerId(UUID.randomUUID().toString())
                .currency(Currency.USD)
                .name("My Debit Account")
                .balance(BigDecimal.ONE)
                .build();

        Account targetAccount = Account.builder()
                .id(UUID.randomUUID().toString())
                .customerId(UUID.randomUUID().toString())
                .currency(Currency.USD)
                .name("My Debit Account")
                .balance(BigDecimal.TEN)
                .build();

        given(accountRepository.findById(sourceAccount.getId())).willReturn(Optional.of(sourceAccount));
        given(accountRepository.findById(targetAccount.getId())).willReturn(Optional.of(targetAccount));

        MoneyTransferRequest moneyTransferRequest = MoneyTransferRequest.builder()
                .fromId(sourceAccount.getId())
                .toId(targetAccount.getId())
                .amount(BigDecimal.TEN)
                .build();

        // when
        Throwable throwable = catchThrowable(() -> {
            accountService.transferMoney(moneyTransferRequest);
        });

        // then
        assertThat(throwable).isInstanceOf(InsufficientMoneyTransferErrorException.class);
    }

    @Test
    void givenBalanceBiggerThanAmount_whenCalculateWithdrawMoney_thenReturnTrue() {
        // when
        boolean result = accountService.calculateWithdrawMoney(BigDecimal.TEN, BigDecimal.ONE);

        // then
        assertTrue(result);
    }

    @Test
    void givenBalanceEqualsToAmount_whenCalculateWithdrawMoney_thenReturnTrue() {
        // when
        boolean result = accountService.calculateWithdrawMoney(BigDecimal.TEN, BigDecimal.TEN);

        // then
        assertTrue(result);
    }

    @Test
    void givenBalanceLessThanAmount_whenCalculateWithdrawMoney_thenReturnFalse() {
        // when
        boolean result = accountService.calculateWithdrawMoney(BigDecimal.ONE, BigDecimal.TEN);

        // then
        assertFalse(result);
    }
}